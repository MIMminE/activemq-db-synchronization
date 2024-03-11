INSERT INTO authlog (cli_ip, result, sync_flag, fail_code, time_stamp, hostname, cli_mac, nas_id, user_name, version,
                     reason)
VALUES (1, 15, false, ' result', '2024-03-04 10:13:17.000000', 'test host', ' hostname', ' cli_mac', ' nas_id',
        ' user_name', ' version');
INSERT INTO authlog (cli_ip, result, sync_flag, fail_code, time_stamp, hostname, cli_mac, nas_id, user_name, version,
                     reason)
VALUES (1, 15, false, ' result', '2024-03-04 10:13:18.000000', 'test host', ' hostname', ' cli_mac', ' nas_id',
        ' user_name', ' version');
INSERT INTO authlog (cli_ip, result, sync_flag, fail_code, time_stamp, hostname, cli_mac, nas_id, user_name, version,
                     reason)
VALUES (2, 3, false, '2', '2024-03-04 10:51:19.000000', 'test host', '3', '2', '3', '2', '3');
INSERT INTO authlog (cli_ip, result, sync_flag, fail_code, time_stamp, hostname, cli_mac, nas_id, user_name, version,
                     reason)
VALUES (1, 1, false, '1',  '2024-03-05 09:39:49.000000', 'test host', 'gfg', '.gf', 'fdgdf', 'fdg', 'fd');
INSERT INTO authlog (cli_ip, result, sync_flag, fail_code, time_stamp, hostname, cli_mac, nas_id, user_name, version,
                     reason)
VALUES (1, 1, false, '1',  '2024-03-05 09:39:42.000000', 'test hos', 'gfg', '.gf', 'fdgdf', 'fdg', 'fd');
INSERT INTO authlog (cli_ip, result, sync_flag, fail_code, time_stamp, hostname, cli_mac, nas_id, user_name, version,
                     reason)
VALUES (1, 1, false, '1', '2024-03-05 09:39:46.000000', 'test host', 'gfg', '.gf', 'fdgdf', 'fdg', 'fd');

INSERT INTO testtbl (name, age, time_stamp)
values ('test1', 10, '2024-03-04 10:13:15.000000');
INSERT INTO testtbl (name, age, time_stamp)
values ('test2', 20, '2024-03-04 10:13:18.000000');
INSERT INTO testtbl (name, age, time_stamp)
values ('test3', 30, '2024-03-04 10:13:17.000000');
INSERT INTO testtbl (name, age, time_stamp)
values ('test4', 40, '2024-03-04 10:13:11.000000');
INSERT INTO testtbl (name, age, time_stamp)
values ('test5', 50, '2024-03-04 10:13:12.000000');
