create table t_stock(
    ticker varchar(255) not null,
    position_open_date date not null,
    primary key (ticker)
    );

create table t_quote (
    quote_date date not null,
    ticker varchar(255) not null,
    adj_close float,
    adj_high float ,
    adj_low float ,
    adj_open float,
    adj_volume float,
    close float not null,
    div_cash float,
    high float ,
    low float ,
    open float not null,
    split_factor float,
    volume float ,
    primary key (quote_date, ticker)
    );

create table t_transaction (
    id BIGINT AUTO_INCREMENT,
    amount float,
    price float,
    quantity float,
    ticker varchar(255),
    transaction_date date,
    transaction_type varchar(255) check (transaction_type in ('BUY','SELL','STK_SPLT','CIL','EXCHANGE','REINVESTMENT','DIVIDEND_CASH')),
    validation_status varchar(255) check (validation_status in ('PENDING','VALIDATED')),
    primary key (id)
    );

create table t_dividend (
    ticker varchar(255) not null,
    payment_date date not null,
    ex_dividend_date date not null,
    amount float not null,
    declaration_date date,
    record_date date,
    primary key (payment_date, ticker));
