# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table autonomous_community (
  code                      varchar(255) not null,
  name                      varchar(255),
  constraint pk_autonomous_community primary key (code))
;

create table indicator (
  name                      varchar(255) not null,
  constraint pk_indicator primary key (name))
;

create table observation (
  id                        bigint not null,
  obs_value                 bigint,
  indicator_name            varchar(255),
  zone_name                 varchar(255),
  date                      timestamp,
  constraint pk_observation primary key (id))
;

create table province (
  code                      varchar(255) not null,
  name                      varchar(255),
  autonomous_community_code varchar(255),
  constraint pk_province primary key (code))
;

create table zone (
  name                      varchar(255) not null,
  type                      integer,
  province_code             varchar(255),
  constraint ck_zone_type check (type in (0,1,2)),
  constraint pk_zone primary key (name))
;

create sequence autonomous_community_seq;

create sequence indicator_seq;

create sequence observation_seq;

create sequence province_seq;

create sequence zone_seq;

alter table observation add constraint fk_observation_indicator_1 foreign key (indicator_name) references indicator (name) on delete restrict on update restrict;
create index ix_observation_indicator_1 on observation (indicator_name);
alter table observation add constraint fk_observation_zone_2 foreign key (zone_name) references zone (name) on delete restrict on update restrict;
create index ix_observation_zone_2 on observation (zone_name);
alter table province add constraint fk_province_autonomousCommunit_3 foreign key (autonomous_community_code) references autonomous_community (code) on delete restrict on update restrict;
create index ix_province_autonomousCommunit_3 on province (autonomous_community_code);
alter table zone add constraint fk_zone_province_4 foreign key (province_code) references province (code) on delete restrict on update restrict;
create index ix_zone_province_4 on zone (province_code);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists autonomous_community;

drop table if exists indicator;

drop table if exists observation;

drop table if exists province;

drop table if exists zone;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists autonomous_community_seq;

drop sequence if exists indicator_seq;

drop sequence if exists observation_seq;

drop sequence if exists province_seq;

drop sequence if exists zone_seq;

