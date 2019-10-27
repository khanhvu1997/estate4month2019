use estate4month2019;

insert into role(code,name) values('MANAGER','Quản lý');
insert into role(code,name) values('STAFF','Nhân viên');

insert into user(username,password,fullnam,status,roled) values('nguyenvana','123456','Nguyễn Văn A',1,1);
insert into user(username,password,fullnam,status,roled) values('nguyenvanb','123456','Nguyễn Văn B',1,2);
insert into user(username,password,fullnam,status,roled) values('nguyenvanc','123456','Nguyễn Văn C',1,2);
insert into user(username,password,fullnam,status,roled) values('nguyenvand','123456','Nguyễn Văn D',1,2);

insert into district(name,code) values('QUAN 1', 'Quận 1');
insert into district(name,code) values('QUAN 2', 'Quận 2');
insert into district(name,code) values('QUAN 3', 'Quận 3');
insert into district(name,code) values('QUAN 4', 'Quận 4');

insert into building(createdby,buildingarea,costrent,) 
values();
