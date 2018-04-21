-- auto-generated definition
create table novel
(
  id              int auto_increment
    primary key,
  requester_type  varchar(255)                            not null,
  requester_extra varchar(255)                            not null,
  update_time     timestamp default '1971-01-01 00:00:00' not null,
  chapters_count  int default '0'                         not null,
  modify_time     timestamp default CURRENT_TIMESTAMP     not null
  on update CURRENT_TIMESTAMP
  comment '本行更新的时间，也就是有人刷出小说更新并传上来的时间，',
  constraint novel_requester_uindex
  unique (requester_type, requester_extra)
)
  engine = InnoDB;

