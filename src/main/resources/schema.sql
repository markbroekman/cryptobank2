CREATE SCHEMA IF NOT EXISTS `cryptobank`;
USE `cryptobank` ;

GRANT ALL ON SCHEMA `cryptobank` TO cryptoUser;


create table if not exists admin
(
    username varchar(45) not null
        primary key,
    password varchar(512) not null,
    salt varchar(16) not null
);


create table if not exists asset
(
    abbreviation varchar(5) not null,
    nameCrypto varchar(45) not null,
    description mediumtext null,
    constraint abbreviation_UNIQUE
        unique (abbreviation),
    constraint nameCrypto_UNIQUE
        unique (nameCrypto)
);

alter table asset
    add primary key (abbreviation);

create table if not exists bankaccount
(
    IBAN varchar(45) not null,
    balance double not null,
    constraint IBAN_UNIQUE2
        unique (IBAN)
);

alter table bankaccount
    add primary key (IBAN);

create table if not exists crypto_currency_rate
(
    abbreviation varchar(5) not null,
    datetime datetime not null,
    value decimal(15,2) not null,
    primary key (abbreviation, datetime),
    constraint fk_crypto_currency_rate_asset1
        foreign key (abbreviation) references asset (abbreviation)
);

create table if not exists customer
(
    username varchar(45) not null
        primary key,
    password varchar(512) not null,
    salt varchar(16) not null,
    firstname varchar(45) not null,
    lastname varchar(45) not null,
    dateofbirth date not null,
    socialsecuritynumber bigint not null,
    street varchar(45) not null,
    zipcode varchar(45) not null,
    housenumber int not null,
    addition varchar(5) null,
    IBAN varchar(45) not null,
    city varchar(45) not null,
    email varchar(512) not null,
    constraint IBAN_UNIQUE
        unique (IBAN),
    constraint email
        unique (email),
    constraint fk_Customer_bankAccount1
        foreign key (IBAN) references bankaccount (IBAN)
);

create index fk_Customer_bankAccount1_idx
    on customer (IBAN);

create table if not exists `order`
(
    orderId int auto_increment
        primary key,
    abbreviation varchar(5) not null,
    assetAmount decimal(15,2) not null,
    desiredPrice double not null,
    iban varchar(45) not null,
    datetimecreated datetime not null,
    orderType int not null,
    constraint fk_order_asset
        foreign key (abbreviation) references asset (abbreviation)
            on update cascade,
    constraint fk_order_bankaccount
        foreign key (iban) references bankaccount (IBAN)
            on update cascade
);

create table if not exists ownedasset
(
    IBAN varchar(45) not null,
    abbreviation varchar(5) not null,
    aantalEenheden double not null,
    primary key (IBAN, abbreviation),
    constraint fk_bankAccount_has_Asset_Asset1
        foreign key (abbreviation) references asset (abbreviation),
    constraint fk_bankAccount_has_Asset_bankAccount1
        foreign key (IBAN) references bankaccount (IBAN)
);

create index fk_bankAccount_has_Asset_Asset1_idx
    on ownedasset (abbreviation);

create index fk_bankAccount_has_Asset_bankAccount1_idx
    on ownedasset (IBAN);

create table if not exists transaction
(
    transactionId int auto_increment
        primary key,
    abbreviation varchar(5) not null,
    assetAmount decimal(15,2) not null,
    assetPrice double not null,
    ibanBuyer varchar(45) not null,
    ibanSeller varchar(45) not null,
    transactioncost double not null,
    datetimeprocessed datetime not null,
    constraint fk_transaction_asset
        foreign key (abbreviation) references asset (abbreviation)
            on update cascade,
    constraint fk_transaction_bankaccount_buyer
        foreign key (ibanBuyer) references bankaccount (IBAN)
            on update cascade,
    constraint fk_transaction_bankaccount_seller
        foreign key (ibanSeller) references bankaccount (IBAN)
            on update cascade
);
