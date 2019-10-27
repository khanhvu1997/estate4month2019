use estate4month2019;

CREATE TABLE role(
id bigint NOT NULL PRIMARY KEY auto_increment,
name VARCHAR(255) NOT NULL,
code VARCHAR(255) NOT NULL,

createddate TIMESTAMP NULL,
modifieddate TIMESTAMP NULL,
createdby VARCHAR(255) NOT NULL,
modifiedby VARCHAR(255) NOT NULL
);


CREATE TABLE user(
id bigint NOT NULL PRIMARY KEY auto_increment,
username VARCHAR(150) NOT NULL,
password VARCHAR(150) NOT NULL,
fullname VARCHAR(150) NOT NULL,
status int NOT NULL,
createddate TIMESTAMP NULL,
modifieddate TIMESTAMP NULL,
createdby VARCHAR(255) NOT NULL,
modifiedby VARCHAR(255) NOT NULL
);

CREATE TABLE user_role(
id bigint NOT NULL PRIMARY KEY auto_increment,
roleid bigint NOT NULL,
userid bigint NOT NULL
);

ALTER TABLE user_role ADD CONSTRAINT fk_userrole_role FOREIGN KEY (roleid) REFERENCES role(id);
ALTER TABLE user_role ADD CONSTRAINT fk_userrole_user FOREIGN KEY (userid) REFERENCES user(id);

CREATE TABLE building(
id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NULL,	
numberofbasement INT NULL,
buildingarea INT NULL,
district VARCHAR(100) NULL,
buildingtype VARCHAR(100) NULL,	
ward VARCHAR(100) NULL,	
street VARCHAR(100) NULL,	
structure VARCHAR(100) NULL,
costrent INT NULL,
costdescription TEXT NULL,
servicecost VARCHAR(255) NOT NULL,
carcost VARCHAR(255) NOT NULL,
motorbikecost VARCHAR(255) NOT NULL,
overtimecost VARCHAR(255) NOT NULL,
electricitycost VARCHAR(255) NOT NULL,
deposit VARCHAR(255) NOT NULL,
payment VARCHAR(255) NOT NULL,
timecontract VARCHAR(255) NOT NULL,
timedecorator VARCHAR(255) NOT NULL,
managername VARCHAR(255) NOT NULL,
managerphine VARCHAR(255) NOT NULL,
type TEXT NULL,
createddate TIMESTAMP NULL,	
modifieddate TIMESTAMP NULL,	
createdby VARCHAR(255) NOT NULL,	
modifiedby VARCHAR(255) NOT NULL
);

CREATE TABLE district(
id bigint NOT NULL PRIMARY KEY auto_increment,
name VARCHAR(255) NOT NULL,
code VARCHAR(255) NOT NULL,
createddate TIMESTAMP NULL,
modifieddate TIMESTAMP NULL,
createdby VARCHAR(255) NOT NULL,
modifiedby VARCHAR(255) NOT NULL
);

CREATE TABLE assignmentbuilding(
id bigint NOT NULL PRIMARY KEY auto_increment,
buildingid bigint NOT NULL,
staffid bigint NOT NULL
);


ALTER TABLE assignmentbuilding ADD CONSTRAINT fk_assignmentbuilding_user FOREIGN KEY (staffid) REFERENCES user(id);
ALTER TABLE assignmentbuilding ADD CONSTRAINT fk_assignmentbuilding_building FOREIGN KEY (buildingid) REFERENCES building(id);

CREATE TABLE rentarea(
id bigint NOT NULL PRIMARY KEY auto_increment,
value VARCHAR(255) NOT NULL,
buildingid bigint NOT NULL,
createddate TIMESTAMP NULL,
modifieddate TIMESTAMP NULL,
createdby VARCHAR(255) NOT NULL,
modifiedby VARCHAR(255) NOT NULL
);

ALTER TABLE rentarea ADD CONSTRAINT fk_rentarea_building FOREIGN KEY (buildingid) REFERENCES building(id);
