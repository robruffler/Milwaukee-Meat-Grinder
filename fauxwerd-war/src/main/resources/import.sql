#this sql is executed for local builds only on startup
insert into role (role_id, name, optlock) values (1, 'ROLE_USER', 1);
insert into role (role_id, name, optlock) values (2, 'ROLE_ADMIN', 1);
insert into user (user_id, email, enabled, full_name, password, optlock) values (1, 'admin@fauxwerd.com', 1, 'Fauxwerd Admin', '1acd274f7e21f228e5362be168b4ceec4c20e1e2', 1);
insert into user_role (user_id, role_id) values (1, 1);
insert into user_role (user_id, role_id) values (1, 2);