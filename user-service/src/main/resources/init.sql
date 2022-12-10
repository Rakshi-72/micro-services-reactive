-- CREATE TABLE Users (
--     userId INTEGER PRIMARY KEY AUTO_INCREMENT,
--     name varchar(56) NOT NULL,
--     balance INTEGER NOT NULL
-- );

-- CREATE TABLE user_transction(

--     transactionId INTEGER AUTO_INCREMENT,
--     user_id Integer NOT NULL,
--     amount Integer NOT NULL,
--     transaction_date timesatmp,
--     foreign key(user_id) references users(userId)

-- );

create table users (
    id bigint auto_increment,
    name varchar(50),
    balance int,
    primary key (id)
);

create table user_transaction(
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    foreign key (user_id) references users (id) on delete cascade,
    primary key(id)
);