# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table autonomous_community (
  code                      varchar(255) not null,
  name                      varchar(255),
  constraint pk_autonomous_community primary key (code))
;

create table city (
  name                      varchar(255) not null,
  province_code             varchar(255),
  constraint pk_city primary key (name))
;

create table indicator (
  name                      varchar(255) not null,
  constraint pk_indicator primary key (name))
;

create table observation (
  id                        bigint not null,
  obs_value                 double,
  indicator_name            varchar(255),
  city_name                 varchar(255),
  date                      timestamp,
  constraint pk_observation primary key (id))
;

create table province (
  code                      varchar(255) not null,
  name                      varchar(255),
  autonomous_community_code varchar(255),
  constraint pk_province primary key (code))
;

create sequence autonomous_community_seq;

create sequence city_seq;

create sequence indicator_seq;

create sequence observation_seq;

create sequence province_seq;

alter table city add constraint fk_city_province_1 foreign key (province_code) references province (code) on delete restrict on update restrict;
create index ix_city_province_1 on city (province_code);
alter table observation add constraint fk_observation_indicator_2 foreign key (indicator_name) references indicator (name) on delete restrict on update restrict;
create index ix_observation_indicator_2 on observation (indicator_name);
alter table observation add constraint fk_observation_city_3 foreign key (city_name) references city (name) on delete restrict on update restrict;
create index ix_observation_city_3 on observation (city_name);
alter table province add constraint fk_province_autonomousCommunit_4 foreign key (autonomous_community_code) references autonomous_community (code) on delete restrict on update restrict;
create index ix_province_autonomousCommunit_4 on province (autonomous_community_code);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists autonomous_community;

drop table if exists city;

drop table if exists indicator;

drop table if exists observation;

drop table if exists province;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists autonomous_community_seq;

drop sequence if exists city_seq;

drop sequence if exists indicator_seq;

drop sequence if exists observation_seq;

drop sequence if exists province_seq;

