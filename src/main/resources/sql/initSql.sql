CREATE DATABASE stockanalysis CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
create user 'stockanalysis_inst'@'localhost' identified by 'stockanalysis';
grant all privileges on stockanalysis.* to 'stockanalysis_inst'@'localhost';
flush privileges;

use stockanalysis;

drop table if exists stock_data_result_sum;
create table stock_data_result_sum
(
    result_id     int auto_increment primary key,
    task_id       varchar(12),
    collect_date  DATE,
    accuracy_rate float(2)
) AUTO_INCREMENT = 100001 COMMENT = '任务扫描结果表';
create index idx_collect_date on stock_data_result_sum (task_id, collect_date);

drop table if exists stock_data_result_details;
create table stock_data_result_details
(
    details_id int auto_increment primary key,
    result_id  int,
    stock_code varchar(8),
    result     int(2),
    open       float(2),
    close      float(2),
    increase   float(2)
) AUTO_INCREMENT = 100001 COMMENT = '任务扫描结果详情表';


## User Role Table
drop table if exists t_user;
CREATE TABLE t_user(
                       user_id int(12) auto_increment primary key,
                       user_name varchar(255),
                       password varchar(255),
                       create_time timestamp default current_timestamp,
                       status smallint default 0
) AUTO_INCREMENT = 1000001 COMMENT = '用户表';
alter table t_user add constraint unique_username UNIQUE(user_name);
create index idx_user_name on t_user(user_name);

drop table if exists t_role;
CREATE TABLE t_role(
                       role_id int(12) auto_increment primary key,
                       role_name varchar(255),
                       role_desc varchar(255),
                       status smallint default 0
) AUTO_INCREMENT = 1000001 COMMENT = '角色表';
alter table t_role add constraint unique_rolename UNIQUE(role_name);

drop table if exists t_user_role;
CREATE TABLE t_user_role(
                            user_role_id int(12) auto_increment primary key,
                            user_id int(12),
                            role_id int(12)
) AUTO_INCREMENT = 1000001 COMMENT = '用户角色';
create index idx_user_id on t_user(user_id);