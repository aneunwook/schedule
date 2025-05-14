create table schedule(
     id bigint primary key auto_increment not null ,
     name varchar(255) not null ,
     password varchar(255) not null ,
     contents  varchar(255) not null ,
     created_at datetime not null ,
     updated_at datetime not null
);

create table author(
   id bigint primary key auto_increment not null,
   email varchar(255) not null unique ,
   name varchar(255) not null ,
   created_at DATETIME not null ,
   updated_at datetime not null
);

ALTER TABLE schedule ADD constraint foreign key (author_id) references author(id);


ALTER TABLE schedule ADD COLUMN author_id bigint;