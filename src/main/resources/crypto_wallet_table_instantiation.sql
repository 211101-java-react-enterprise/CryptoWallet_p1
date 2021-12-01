

-- drop tables
drop table coin;
drop table app_user;

-- create tables
create table app_user (
	user_uuid varchar primary key,
	username varchar unique not null,
	password varchar not null,
	first_name varchar,
	last_name varchar,
	dollars_invested numeric(10,2)
);


create table coin (
	user_uuid varchar,
	currency_pair varchar(10),
	amount numeric,
	
	constraint fk_user_uuid
	foreign key (user_uuid)
	references app_user (user_uuid)
	on delete cascade,
	
	constraint pk_composite_user_uuid_currency_pair
	primary key (user_uuid, currency_pair)
	
);

-- insert dummy values into app_user
insert into app_user (user_uuid, username, password, first_name, last_name, dollars_invested)
values ('5256d1e5-3393-4640-8b9b-44f607a2ec84', 'username0', 'p4ssword', 'First!', 'Last', 100.05);

insert into app_user (user_uuid, username, password, first_name, last_name, dollars_invested)
values ('ff582ebe-091e-467b-a216-01f71d0cf9cc', 'username1', 'p4ssword', 'First!', 'Last', 100.05);

-- insert dummy values into coin 
insert into coin (user_uuid, currency_pair, amount)
values ('5256d1e5-3393-4640-8b9b-44f607a2ec84', 'BTC-USD', 0.1);

insert into coin (user_uuid, currency_pair, amount)
values ('5256d1e5-3393-4640-8b9b-44f607a2ec84', 'ETH-USD', 10);

insert into coin (user_uuid, currency_pair, amount)
values ('5256d1e5-3393-4640-8b9b-44f607a2ec84', 'SHIB-USD', 1000);



insert into coin (user_uuid, currency_pair, amount)
values ('ff582ebe-091e-467b-a216-01f71d0cf9cc', 'BTC-USD', 1);

insert into coin (user_uuid, currency_pair, amount)
values ('ff582ebe-091e-467b-a216-01f71d0cf9cc', 'ETH-USD', 21);


--Example Join statement
select *
from app_user au 
join coin c 
on au.user_uuid = c.user_uuid 
where c.user_uuid = '5256d1e5-3393-4640-8b9b-44f607a2ec84';


-- Select all statements
select * from app_user;
select * from coin;










