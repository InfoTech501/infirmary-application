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
  blood_pressure varchar2(7),
  pulse_rate number(3,0),
  respiratory_rate NUMBER(2,0),
  visit_date timestamp(6),
  treatment varchar2(255),
  is_active NUMBER(1,0)
    constraint IS_ACTIVE_NOT_NULL not null
    constraint IS_ACTIVE_CHECK check (is_active IN (0,1)),
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
  is_available NUMBER(1,0)
    constraint IS_AVAILABLE_MEDICINE_NOT_NULL not null
    constraint IS_AVAILABLE_MEDICINE_CHECK check (is_available IN (0,1)),
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
values ('Maynard', 'Kent', 'Harlan', to_timestamp('2000-01-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '19', 'MALE', 'harlan.maynard@gmail.com', 'Salaban', null);
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Joan', 'Agapito', 'dela Cruz', to_timestamp('2001-02-04 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '17', 'FEMALE', 'joan.delacruz@gmail.com', 'Trece', '09151132244');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Angelo', 'Mallari', 'dela Cruz', to_timestamp('2000-04-12 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '18', 'MALE', 'angelo.delacruz@gmail.com', 'Amadeo, Cavite', '09151132245');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Joshua', 'Martinez', 'Villanueva', to_timestamp('2005-12-21 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '20', 'MALE', 'joshua.villanueva@gmail.com', 'Imus, Cavite', '09175532245');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Maria', 'Antonio', 'Agustin', to_timestamp('2003-09-17 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '19', 'FEMALE', 'maria.agustin@gmail.com', 'Dasma', '09175532333');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('RC', null, 'Bayot', to_timestamp('2003-10-17 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '21', 'FEMALE', 'rcbayot@gmail.com', 'Salaban', '09482156321');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Alice', 'Marie', 'Johnson', TO_TIMESTAMP('1995-05-10 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '28', 'FEMALE', 'alice.johnson@gmail.com', 'Mahogany', '09178234567');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Leo', 'Antonio', 'Santos', TO_TIMESTAMP('1998-08-22 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '27', 'MALE', 'leo.santos@gmail.com', 'Silang, Cavite', '09215467890');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Emily', null, 'Smith', TO_TIMESTAMP('1989-03-15 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '34', 'FEMALE', 'emily.smith@gmail.com', 'Silang, Cavite', '09326789012');
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Mark', 'David', 'Brown', TO_TIMESTAMP('2002-07-04 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '23', 'MALE', 'mark.brown@gmail.com', 'Salaban', '09437890123');

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
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('Linda Cruz', 'Bagong Silang, Caloocan', '09351234876');
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('Marco Reyes', 'Poblacion, Batangas', '09471234567');
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('Susan Lim', 'Fort Bonifacio, Taguig', null);
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('Greg Santos', 'Balagtas, Bulacan', '09283455678');
insert into guardian_details (guardian_name, guardian_address, guardian_contact_number)
values ('Anna Flores', 'New Manila, Quezon City', null);

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
values ('1', '01', '001', '109152648294');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('2', '02', '002', '108245136248');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('3', '03', '003', '101257182639');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('4', '04', '004', '102846539215');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('5', '05', '005', '103456138297');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('6', '01', '005', '105461532145');
insert into student(person_id, section_section_id, stud_guardian_id, LRN)
values ('7', '03', '004', '106846539215');

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
insert into ailments (ailment_id, description)
values (6, 'Skin Rash');
insert into ailments (ailment_id, description)
values (7, 'Shortness of Breath');
insert into ailments (ailment_id, description)
values (8, 'Dizziness');
insert into ailments (ailment_id, description)
values (9, 'Sore Throat');
insert into ailments (ailment_id, description)
values (10, 'Allergic Reaction');

--INSERT MEDICINE DATA
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('IB', 'Ibuprofen', 'can treat fever, mild to moderate pain, etc', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 1);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('CS', 'Cough Syrup', 'Adults with both dry and productive coughs', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 1);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('PT', 'Paracetamol', 'temporarily relieve mild-to-moderate pain and fever', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 0);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('AC', 'Antacid', 'a medicine used to treat heartburn and indigestion', to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 1);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('AH', 'Antihistamine', 'treat allergic rhinitis, common cold, influenza, and other allergies', to_timestamp('2026-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 1);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('AS', 'Aspirin', 'Used to treat pain, fever, or inflammation', TO_TIMESTAMP('2027-06-15 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 0);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('VM', 'Vitamin C', 'Boosts immunity and promotes overall health', TO_TIMESTAMP('2026-10-10 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 1);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('HD', 'Hydrocortisone Cream', 'Treats skin irritation, eczema, and rashes', TO_TIMESTAMP('2025-12-31 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 0);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('DN', 'Decongestant', 'Relieves nasal congestion caused by colds', TO_TIMESTAMP('2026-03-20 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 0);
insert into medicine (medicine_id, item_name, description, expiration_date, is_available)
values ('AN', 'Antibiotics', 'Treats bacterial infections in the body', TO_TIMESTAMP('2027-08-25 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 1);

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
insert into inventory (medicine_id, item_type, quantity)
values ('AS', 'Medicine', 60);
insert into inventory (medicine_id, item_type, quantity)
values ('VM', 'Medicine', 10);
insert into inventory (medicine_id, item_type, quantity)
values ('HD', 'Medicine', 15);
insert into inventory (medicine_id, item_type, quantity)
values ('DN', 'Medicine', 50);
insert into inventory (medicine_id, item_type, quantity)
values ('AN', 'Medicine', 4);

--INSERT MEDICAL RECORD DATA
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (1, 1, null, 1, 'Headache', '37.5°C', '130/85', 88, 18, to_timestamp('2000-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed pain reliever', 1);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (2, 2, null, 2, 'Stomachache', '37.2°C', '140/90', 92, 20, to_timestamp('2008-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Rest and hydration', 0);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (3, 3, null, 3, 'Fever', '38.5°C', '120/80', 85, 19, to_timestamp('2025-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antipyretic', 1);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (4, 4, null, 4, 'Dry cough', '37.0°C', '110/70', 78, 16, to_timestamp('2024-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed cough syrup', 0);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (5, 1, null, 3, 'Dizziness', '37.0°C', '115/75', 72, 15, to_timestamp('2020-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Recommended hydration', 1);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (3, 1, null, 4, 'Headache', '37.0°C', '125/85', 80, 17, to_timestamp('2023-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed pain reliver', 0);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (6, 6, null, 2, 'Skin rash', '36.9°C', '118/78', 75, 16, TO_TIMESTAMP('2022-04-20 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antihistamine cream', 1);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (1, 9, null, 3, 'Sore throat', '37.4°C', '122/82', 78, 17, TO_TIMESTAMP('2021-03-15 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Recommended salt water gargle', 1);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (1, 8, null, 1, 'Dizziness', '36.8°C', '121/81', 74, 16, TO_TIMESTAMP('2020-08-14 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Recommended hydration', 0);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (4, 10, null, 4, 'Allergic reaction', '36.7°C', '125/85', 80, 17, TO_TIMESTAMP('2023-09-30 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antihistamine tablets', 1);
insert into medical_record (student_id, ailment_id, med_history_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (7, 7, null, 2, 'Shortness of breath', '37.0°C', '135/90', 95, 22, TO_TIMESTAMP('2024-06-05 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed inhaler', 0);

--INSERT MEDICINE ADMINISTERED
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (1, 'IB', 1, '1', 'Ibuprofen 200mg administered', 1, to_timestamp('2000-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (2, 'CS', 2, '2', 'Cough syrup 10ml administered', 2, to_timestamp('2008-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (3, 'PT', 3, '1', 'Paracetamol 500mg administered', 1, to_timestamp('2025-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (4, 'AC', 4, '3', 'Antacid 500mg administered', 2, to_timestamp('2024-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (5, 'VM', 5, '1', 'Vitamin C 500mg administered', 1, to_timestamp('2020-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (6, 'IB', 6, '2', 'Ibuprofen 200mg administered', 1, TO_TIMESTAMP('2023-02-15 09:30:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (7, 'AH', 7, '1', 'Antihistamine cream administered', 2, TO_TIMESTAMP('2022-07-10 15:45:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (8, 'IB', 8, '4', 'Ibuprofen 400mg administered', 1, TO_TIMESTAMP('2021-05-25 12:20:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (9, 'DN', 9, '3', 'Decongestant 10ml administered', 1, TO_TIMESTAMP('2020-10-01 08:15:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (id, medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (10, 'AN', 10, '2', 'Antibiotics 500mg administered', 1, TO_TIMESTAMP('2023-03-20 11:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));

commit;
