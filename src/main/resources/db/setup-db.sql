create database ileiwedb;

create user 'ileiwe_user'@'localhost' identified by 'ileiwe123';
grant all privileges on ileiwedb.* to 'ileiwe_user'@'localhost';
flush privileges;