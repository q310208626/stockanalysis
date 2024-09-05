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