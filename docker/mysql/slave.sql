show variables like 'server_id';

set global server_id = 2;

stop slave;
reset slave;

-- 157 is from "show master status;"
change master to master_host='192.168.12.7',master_port=8808,master_user='test-slave',master_password='test-81710181',master_log_file='mysql-bin.000001',master_log_pos=157;

start slave;

stop slave;
reset slave;
start slave;

show slave status;
-- check "Slave_IO_Running" & "Slave_SQL_Running" if they're "Yes", means working