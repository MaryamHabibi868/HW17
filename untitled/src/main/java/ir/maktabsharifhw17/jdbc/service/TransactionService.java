package ir.maktabsharifhw17.jdbc.service;


import ir.maktabsharifhw17.jdbc.config.ApplicationContext;
import ir.maktabsharifhw17.jdbc.domains.*;
import ir.maktabsharifhw17.jdbc.repository.CardRepository;
import ir.maktabsharifhw17.jdbc.repository.TransactionRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransactionService {

    private final CardRepository cardRepository = ApplicationContext.getInstance().getCardRepository();
    private final TransactionRepository transactionRepository = ApplicationContext.getInstance().getTransactionRepository();

    public Transaction performCardToCard(String sourceCardNumber, String destinationCardNumber, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CARD_TO_CARD);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        Optional<Card> sourceOpt = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destOpt = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceOpt.isEmpty() || destOpt.isEmpty()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        Card sourceCard = sourceOpt.get();
        Card destinationCard = destOpt.get();

        transaction.setSourceCard(sourceCard);
        transaction.setDestinationCard(destinationCard);

        if (amount > 15_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        double fee = 0;
        if (!sourceCard.getBankName().equals(destinationCard.getBankName())) {
            if (amount <= 10_000_000) {
                fee = 720;
            } else {
                int extraMillions = (int) Math.ceil((amount - 10_000_000) / 1_000_000);
                fee = 1000 + (extraMillions * 100);
            }
        }

        if (sourceCard.getBalance() < amount + fee) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        sourceCard.setBalance(sourceCard.getBalance() - (amount + fee));
        destinationCard.setBalance(destinationCard.getBalance() + amount);

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.create(transaction);

        return transaction;
    }

    public Transaction performPaya(String sourceCardNumber, String destinationCardNumber, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.PAYA_INDIVIDUAL);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        Optional<Card> sourceOpt = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destOpt = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceOpt.isEmpty() || destOpt.isEmpty()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        Card sourceCard = sourceOpt.get();
        Card destinationCard = destOpt.get();

        transaction.setSourceCard(sourceCard);
        transaction.setDestinationCard(destinationCard);

        if (amount > 50_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        double fee = amount * 0.001;

        if (sourceCard.getBalance() < amount + fee) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        sourceCard.setBalance(sourceCard.getBalance() - (amount + fee));
        destinationCard.setBalance(destinationCard.getBalance() + amount);

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.create(transaction);

        return transaction;
    }

    /*public List<Transaction> performBatchPaya(String sourceCardNumber, List<PayaRequest> requests) {
        List<Transaction> transactionList = new ArrayList<>();

        Optional<Card> sourceOpt = cardRepository.findByCardNumber(sourceCardNumber);
        if (sourceOpt.isEmpty()) {
            for (PayaRequest request : requests) {
                Transaction transaction = new Transaction();
                transaction.setTransactionType(TransactionType.PAYA);
                transaction.setAmount(request.getAmount());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setDescription("کارت مبدا یافت نشد");
                transactionRepository.create(transaction);
                transactionList.add(transaction);
            }
            return transactionList;
        }

        Card sourceCard = sourceOpt.get();

        double totalAmount = requests.stream().mapToDouble(PayaRequest::getAmount).sum();
        int count = requests.size();
        double batchFee = count <= 10 ? 12_000 : 12_000 + ((count - 10) * 1_200);

        if (sourceCard.getBalance() < totalAmount + batchFee) {
            for (PayaRequest request : requests) {
                Transaction transaction = new Transaction();
                transaction.setTransactionType(TransactionType.PAYA);
                transaction.setAmount(request.getAmount());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setSourceCard(sourceCard);
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setDescription("موجودی کارت برای کل مبلغ و کارمزد دسته‌ای کافی نیست");
                transactionRepository.create(transaction);
                transactionList.add(transaction);
            }
            return transactionList;
        }

        sourceCard.setBalance(sourceCard.getBalance() - batchFee);
        cardRepository.update(sourceCard);

        for (PayaRequest request : requests) {
            Transaction transaction = new Transaction();
            transaction.setTransactionType(TransactionType.PAYA);
            transaction.setAmount(request.getAmount());
            transaction.setTransactionDate(LocalDateTime.now());
            transaction.setSourceCard(sourceCard);

            if (request.getAmount() > 50_000_000) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setDescription("مبلغ بیشتر از سقف مجاز پایا است");
            } else if (sourceCard.getBalance() < request.getAmount() + (request.getAmount() * 0.001)) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transaction.setDescription("موجودی کافی برای این تراکنش وجود ندارد");
            } else {
                Optional<Card> destOpt = cardRepository.findByCardNumber(request.getDestinationCardNumber());
                if (destOpt.isPresent()) {
                    Card destCard = destOpt.get();
                    double fee = request.getAmount() * 0.001;
                    sourceCard.setBalance(sourceCard.getBalance() - (request.getAmount() + fee));
                    destCard.setBalance(destCard.getBalance() + request.getAmount());

                    cardRepository.update(sourceCard);
                    cardRepository.update(destCard);

                    transaction.setDestinationCard(destCard);
                    transaction.setTransactionStatus(TransactionStatus.SUCCESS);
                } else {
                    transaction.setTransactionStatus(TransactionStatus.FAILED);
                    transaction.setDescription("کارت مقصد یافت نشد");
                }
            }

            transactionRepository.create(transaction);
            transactionList.add(transaction);
        }

        return transactionList;
    }
*/
    public Transaction performSatna(String sourceCardNumber, String destinationCardNumber, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.SATNA);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        Optional<Card> sourceOpt = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destOpt = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceOpt.isEmpty() || destOpt.isEmpty()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        Card sourceCard = sourceOpt.get();
        Card destinationCard = destOpt.get();

        transaction.setSourceCard(sourceCard);
        transaction.setDestinationCard(destinationCard);

        if (amount <= 50_000_000 || amount > 200_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        double fee = amount * 0.002;

        if (sourceCard.getBalance() < amount + fee) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        sourceCard.setBalance(sourceCard.getBalance() - (amount + fee));
        destinationCard.setBalance(destinationCard.getBalance() + amount);

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.create(transaction);

        return transaction;
    }
}
