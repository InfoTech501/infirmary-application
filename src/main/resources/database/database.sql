drop user infirmary cascade;

create user infirmary identified by Changeme0;

alter user infirmary quota unlimited on DATA;

alter user infirmary quota unlimited on USERS;

grant create session to infirmary with admin option;

grant connect to infirmary;

alter session set current_schema = infirmary;

drop table person cascade constraints;
drop table section cascade constraints;
drop table guardian_details cascade constraints;
drop table student cascade constraints;
drop table ailments cascade constraints;
drop table medical_history cascade constraints;
drop table medical_record cascade constraints;
drop table inventory cascade constraints;
drop table medicine cascade constraints;
drop table medicine_administered cascade constraints;
drop table login cascade constraints;
drop table employee cascade constraints;

create table person (
  id number(10,0) generated as identity
    constraint PERSON_ID_NOT_NULL not null,
  first_name varchar2(50 char),
  middle_name varchar2(35 char),
  last_name varchar2(35 char),
  age number(2,0),
  birthdate timestamp(6),
  gender varchar(10 char),
  email varchar(64 char),
  address varchar2(255 char),
  contact_number varchar2(11),
  primary key (id));

create table section (
  section_id number(20,0) generated as identity,
  adviser_id number(20,0) unique,
  strand varchar2(30 char),
  grade_level varchar(30 char),
  section varchar(30 char),
  primary key (section_id));

create table guardian_details (
  guardian_id number(10,0) generated as identity
    constraint GUARDIAN_NOT_NULL not null,
  guardian_name varchar2(255 char),
  guardian_address varchar2(255 char),
  guardian_contact_number varchar2(11 char),
  primary key (guardian_id));

create table student (
  id number(10,0) generated as identity,
  person_id number(20,0) unique,
  section_section_id number(20,0),
  stud_guardian_id number(20,0),
  LRN number(12,0),
  primary key (id));

create table ailments (
  ailment_id number(30) not null,
  description varchar2(255 char),
  primary key (ailment_id));

create table medical_history (
  med_history_id number(10,0) not null,
  description varchar2(255 char),
  primary key (med_history_id));

create table medical_record (
  id number(20,0) generated as identity
    constraint MEDICAL_RECORD_NOT_NULL not null,
  student_id number(20,0),
  ailment_id number(20,0),
  med_history_id number(20,0),
  nurse_in_charge_id number(20,0),
  symptoms varchar2(60),
  temperature_readings varchar2(10),
  visit_date timestamp(6),
  treatment varchar2(255),
  primary key (id));

create table inventory (
  inventory_id number(20,0) generated as identity
    constraint INVENTORY_NOT_NULL not null,
  medicine_id varchar2(50 char),
  item_type varchar2(60),
  quantity number(10,0),
  primary key (inventory_id));

create table medicine (
  medicine_id varchar2(50 char) not null,
  item_name varchar2(50),
  description varchar2(255),
  expiration_date timestamp(6),
  primary key (medicine_id));

create table medicine_administered (
  id number(20,0),
  medicine_id varchar2(50 char),
  med_record_id number(20,0),
  nurse_in_charge_id number(20,0),
  description varchar2(255),
  quantity number(10,0),
  date_administered timestamp(6),
  primary key (id));

create table login (
  id number(20,0) generated as identity
    constraint LOGIN_NOT_NULL not null,
  person_id number(20,0) unique,
  username varchar2(25 char)
    constraint LOGIN_USERNAME_NOT_NULL not null,
  password varchar2(255 char)
    constraint LOGIN_PASSWORD_NOT_NULL not null,
  join_date timestamp(6),
  last_login_date timestamp(6),
  role varchar2(255 char),
  authorities varchar2(255 char),
  is_active number(1,0),
  is_locked number(1,0),
  primary key (id));

create table employee (
  id number(20,0)
    constraint EMPLOYEE_NOT_NULL not null,
  employee_id varchar2(50 char)
    constraint EMPLOYEE_ID_NOT_NULL not null
    constraint EMPLOYEE_ID_UNIQUE unique,
  primary key (id));

alter table employee
    add constraint FK_EMPLOYEE_PERSON_ID
    foreign key (id) references person;

alter table section
    add constraint FK_SECTION_ADVISER_ID
    foreign key(adviser_id) references person;

alter table student
    add constraint FK_STUDENT_SECTION_SECTION_ID
    foreign key (section_section_id) references section;

alter table student
    add constraint FK_STUDENT_PERSON_ID
    foreign key (person_id) references person;

alter table student
    add constraint FK_STUDENT_STUD_GUARDIAN_ID
    foreign key (stud_guardian_id) references guardian_details;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_STUDENT_ID
    foreign key (student_id) references student;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_AILMENT_ID
    foreign key (ailment_id) references ailments;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_MED_HISTORY_ID
    foreign key (med_history_id) references medical_history;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_NURSE_IN_CHARGE_ID
    foreign key (nurse_in_charge_id) references person;

alter table inventory
    add constraint FK_INVENTORY_MEDICINE_ID
    foreign key (medicine_id) references medicine;

alter table medicine_administered
    add constraint FK_MEDICINE_ADMINISTERED_MEDICINE_ID
    foreign key (medicine_id) references medicine;

alter table medicine_administered
    add constraint FK_MEDICINE_ADMINISTERED_MED_RECORD_ID
    foreign key (med_record_id) references medical_record;

alter table medicine_administered
    add constraint FK_MEDICINE_ADMINISTERED_NURSE_IN_CHARGE_ID
    foreign key (nurse_in_charge_id) references employee;

alter table login
    add constraint FK_LOGIN_PERSON_ID
    foreign key (person_id) references person;


--INSERT PERSON DATA
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('John', null, 'Doe', to_timestamp('1982-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '18', 'MALE', 'johndoe@gmail.com', 'Silang, Cavite', '09173167078');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Maynard', 'Kent', 'Harlan', to_timestamp('2000-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '19', 'MALE', 'harlan.maynard@gmail.com', 'Kentucky, USA', null);
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Joan', 'Agapito', 'dela Cruz', to_timestamp('1995-02-04 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '17', 'FEMALE', 'joan.delacruz@gmail.com', 'Makati City', '09151132244');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Angelo', 'Mallari', 'dela Cruz', to_timestamp('1997-04-12 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '18', 'MALE', 'angelo.delacruz@gmail.com', 'Makati City', '09151132245');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Joshua', 'Martinez', 'Villanueva', to_timestamp('2005-12-21 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '20', 'MALE', 'joshua.villanueva@gmail.com', 'Imus, Cavite', '09175532245');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Maria', 'Antonio', 'Agustin', to_timestamp('2015-09-17 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '19', 'FEMALE', 'maria.agustin@gmail.com', 'Calamba, Laguna', '09175532333');

--INSERT LOGIN DATA
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('admin', '$2a$10$JKotLEwO8PMsnr3.ufngYusceu4T7UHBSkFgeyrv/q0.WPiGm9DxS', 1, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE USER', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('admin', '$2a$10$JKotLEwO8PMsnr3.ufngYusceu4T7UHBSkFgeyrv/q0.WPiGm9DxS', 2, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_ADMIN', 'user:read,user:create,user:update,user:delete', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('staff','$2a$10$JKotLEwO8PMsnr3.ufngYusceu4T7UHBSkFgeyrv/q0.WPiGm9DxS', 3, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_STAFF', 'user:read,user:create,user:update', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('user1','$2a$10$JKotLEwO8PMsnr3.ufngYusceu4T7UHBSkFgeyrv/q0.WPiGm9DxS', 4, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);
insert into login (username, password, person_id, join_date, last_login_date, role, authorities, is_active, is_locked)
values ('user2','$2a$10$JKotLEwO8PMsnr3.ufngYusceu4T7UHBSkFgeyrv/q0.WPiGm9DxS', 5, to_timestamp('2024-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), to_timestamp('2024-10-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'ROLE_USER', 'user:read,user:create,user:update', 1, 0);

-- INSERT GUARDIAN DETAILS DATA
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('chris bayot', '23 Silang, Cavite', '09263154827');
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('jorai villanueva', 'Loma, Amadeo', '09521846325');
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('dave bayot', 'Kaybagal North', null);
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('lei marinduque', 'Trece', '09524781639');
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('don ambion', 'Amadeo', null);

-- INSERT SECTION DATA
insert into section (adviser_id, strand, grade_level, section)
values (1, 'HUMSS', 'Grade 11', 'gumamela');
insert into section (adviser_id, strand, grade_level, section)
values (2, 'STEM', 'Grade 12', 'santan');
insert into section (adviser_id, strand, grade_level, section)
values (3, 'TVL', 'Grade 11', 'rosal');
insert into section (adviser_id, strand, grade_level, section)
values (4, 'GAS', 'Grade 11', 'jasmine');
insert into section (adviser_id, strand, grade_level, section)
values (5, 'ABM', 'Grade 12', 'camia');

--INSERT STUDENT DATA
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('1', '01', '001', '093152648294');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('2', '02', '002', '018245136248');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('3', '03', '003', '041257182639');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('4', '04', '004', '012846539215');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('5', '05', '005', '082456138297');

-- INSERT EMPLOYEE DATA
insert into employee (id, employee_id)
values (1, 'EMP-0001');
insert into employee (id, employee_id)
values (2, 'EMP-0002');
insert into employee (id, employee_id)
values (3, 'EMP-0003');
insert into employee (id, employee_id)
values (4, 'EMP-0004');
insert into employee (id, employee_id)
values (5, 'EMP-0005');

-- INSERT AILMENTS DATA
insert into ailments (ailment_id, description)
values (1, 'Headache');
insert into ailments (ailment_id, description)
values (2, 'Cold');
insert into ailments (ailment_id, description)
values (3, 'Fever');
insert into ailments (ailment_id, description)
values (4, 'Dry Cough');
insert into ailments (ailment_id, description)
values (5, 'Stomachache');

--INSERT MEDICINE DATA
insert into medicine (medicine_id, item_name, description, expiration_date)
values ('IB', 'Ibuprofen', 'can treat fever, mild to moderate pain, etc', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine (medicine_id, item_name, description, expiration_date)
values ('CS', 'Cough Syrup', 'Adults with both dry and productive coughs', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine (medicine_id, item_name, description, expiration_date)
values ('PT', 'Paracetamol', 'temporarily relieve mild-to-moderate pain and fever', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine (medicine_id, item_name, description, expiration_date)
values ('AC', 'Antacid', 'a medicine used to treat heartburn and indigestion', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine (medicine_id, item_name, description, expiration_date)
values ('AH', 'Antihistamine', 'treat allergic rhinitis, common cold, influenza, and other allergies', to_timestamp('2026-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));

 --INSERT INVENTORY DATA
insert into inventory (medicine_id, item_type, quantity)
values ('IB', 'Medicine', 50);
insert into inventory (medicine_id, item_type, quantity)
values ('CS', 'Medicine', 30);
insert into inventory (medicine_id, item_type, quantity)
values ('PT', 'Medicine', 40);
insert into inventory (medicine_id, item_type, quantity)
values ('AC', 'Medicine', 25);
insert into inventory (medicine_id, item_type, quantity)
values ('AH', 'Medicine', 20);

--INSERT MEDICAL RECORD DATA
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, visit_date, treatment)
values (1, 1, null, 1, 'Severe headache', '37.5°C', to_timestamp('2000-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed pain reliever');
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, visit_date, treatment)
values (2, 2, null, 2, 'Runny nose and cough', '37.2°C', to_timestamp('2008-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Rest and hydration');
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, visit_date, treatment)
values (3, 3, null, 3, 'High fever', '38.5°C', to_timestamp('2025-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antipyretic');
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, visit_date, treatment)
values (4, 4, null, 4, 'Dry cough', '37.0°C', to_timestamp('2024-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed cough syrup');

--INSERT MEDICINE ADMINISTERED
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (1, 'IB', 1, '1', 'Ibuprofen 200mg administered', 1, to_timestamp('2000-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (2, 'CS', 2, '2', 'Cough syrup 10ml administered', 2, to_timestamp('2008-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (3, 'PT', 3, '1', 'Paracetamol 500mg administered', 1, to_timestamp('2025-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (4, 'AC', 4, '3', 'Antacid 500mg administered', 2, to_timestamp('2024-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));

commit;
