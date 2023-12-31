show variables like 'server_id';

set global server_id = 2;

stop slave;
reset slave;

-- 130304 is from "show master status;"
change master to master_host='192.168.12.3',master_port=8808,master_user='test-slave',master_password='test-81710181',master_log_file='mysql-bin.000006',master_log_pos=130304;

'130304'

start slave;

stop slave;
reset slave;
start slave;

show slave status;
-- check "Slave_IO_Running" & "Slave_SQL_Running" if they're "Yes", means working

-- if it's not working, after reboot, try below
STOP SLAVE;
SET GLOBAL SQL_SLAVE_SKIP_COUNTER = 1; 
START SLAVE;
