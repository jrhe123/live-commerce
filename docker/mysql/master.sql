create user 'test-slave'@'%' identified with mysql_native_password by 'test-81710181';

grant replication slave on *.* to 'test-slave'@'%';

flush privileges;

show variables like 'server_id';

set global server_id = 1;

-- check position
show master status;

show binlog events;

reset master;

show variables like 'server_id';
