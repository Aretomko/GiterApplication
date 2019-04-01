
create sequence hibernate_sequence start 1 increment 1;

create table language (id int8 not null, name varchar(255), number_of_ocurrences int4 not null, statistic_id int8, primary key (id));

create table statistic (id int8 not null, email varchar(255), login varchar(255), number_of_languages_used int4 not null, number_of_repositories int4 not null, user_id int8, primary key (id));

create table user_role (user_id int8 not null, roles varchar(255));

create table users_repository (id int8 not null, language varchar(255), name varchar(255), user_id int8, primary key (id));

create table usr (id int8 not null, activation_code varchar(255), active boolean not null, email varchar(255), password varchar(255), username varchar(255), primary key (id));

alter table if exists language add constraint FKlidsoh1qu7ubj52r5b407kp91 foreign key (statistic_id) references statistic;

alter table if exists statistic add constraint FK3fk4hj4hlc9ut33lb4pt47r4p foreign key (user_id) references usr;

alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;

alter table if exists users_repository add constraint FK6ovbqbmlmj6iyi1ecl2ixy12s foreign key (user_id) references statistic;