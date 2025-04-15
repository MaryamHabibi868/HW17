drop table if exists users, cards, transactions;


create table users
(
    user_id    int primary key,
    first_name varchar(50),
    last_name  varchar(50),
    user_name  varchar(70) unique ,
    password   varchar(20)
);


create table cards
(
    card_id      int primary key,
    card_number  varchar(16) unique ,
    bank_name    varchar(30),
    balance      double precision,
    expired_date date,
    user_id      int references users (user_id)
);

create table transactions
(
    transaction_id          int primary key,
    source_card_number      varchar(16),
    destination_card_number varchar(16),
    amount                  double precision,
    transaction_type        varchar(100),
    transaction_status      varchar(50),
    transaction_date        date
);