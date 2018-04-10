-- auto-generated definition
create table novel
(
  id              int auto_increment
    primary key,
  requester_type  varchar(255)                            not null,
  requester_extra varchar(255)                            not null,
  update_time     timestamp default '1971-01-01 00:00:00' not null,
  chapters_count  int default '0'                         not null,
  constraint novel_requester_uindex
  unique (requester_type, requester_extra)
)
  engine = InnoDB;

