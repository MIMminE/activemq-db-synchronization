DROP TABLE IF EXISTS testtbl;
DROP TABLE IF EXISTS authlog;
create table auth_log_tbl
(
    cli_ip     varchar(32)         not null,
    result     varchar(32)         not null,
    sync_flag  bit null,
    fail_code  varchar(8) null,
    id_flag    bigint auto_increment primary key,
    time_stamp datetime(6) not null,
    hostname   varchar(32) not null,
    cli_mac    varchar(40) not null,
    nas_id     varchar(40) not null,
    user_name  varchar(64) not null,
    version    varchar(64) null,
    reason     varchar(200) null
);


create table system_log_tbl
(
    id_flag        bigint auto_increment primary key,
    name      varchar(32) not null,
    time_stamp datetime(6) not null,
    age       int         not null,
    sync_flag bit default false
)