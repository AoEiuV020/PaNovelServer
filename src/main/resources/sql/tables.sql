create table novel
(
  id                  int auto_increment
    primary key,
  site                varchar(255)                        not null,
  author              varchar(255)                        not null,
  name                varchar(255)                        not null,
  detail              varchar(255)                        not null,
  chapters_count      int default '0'                     not null,
  receive_update_time timestamp default CURRENT_TIMESTAMP not null
  comment '拿到上一个更新的时间, 也就是上次刷出更新的[checkUpdateTime],',
  check_update_time   timestamp default CURRENT_TIMESTAMP not null
  comment '检查更新时间, 也就是这个时间之前的更新是已知的，不论有无更新，',
  constraint novel_site_author_name_uindex
  unique (site, author, name)
)
  engine = InnoDB
  charset = utf8;

