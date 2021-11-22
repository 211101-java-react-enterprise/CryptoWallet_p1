

-- drop tables
drop table coin_amount;
drop table wallet;
drop table app_user;

-- create tables
create table app_user (
	user_uuid varchar primary key,
	username varchar unique not null,
	password varchar not null
);

create table wallet (
	wallet_uuid varchar primary key,
	user_uuid varchar,
	dollars_invested numeric(10,2),
	
	constraint fk_user_uuid
	foreign key (user_uuid)
	references app_user (user_uuid)
);

create table coin_amount (
	coin_amount_uuid varchar primary key,
	wallet_uuid varchar,
	currency_pair varchar(10),
	amount numeric(15,5),
	
	constraint fk_wallet_uuid
	foreign key (wallet_uuid)
	references wallet (wallet_uuid)
);


