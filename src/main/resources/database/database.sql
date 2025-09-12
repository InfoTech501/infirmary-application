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
drop table diagnosed_condition cascade constraints;
drop table medical_record cascade constraints;
drop table inventory cascade constraints;
drop table medicine cascade constraints;
drop table medicine_administered cascade constraints;
drop table login cascade constraints;
drop table employee cascade constraints;
drop table department cascade constraints;

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
  contact_number varchar2(15),
  primary key (id));

create table section (
  section_id number(10,0) generated as identity,
  adviser_id number(10,0) unique,
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
  person_id number(10,0) unique,
  section_section_id number(10,0),
  stud_guardian_id number(10,0),
  LRN number(12,0),
  primary key (id));

create table ailments (
  ailment_id number(10,0) generated as identity,
  description varchar2(255 char),
  primary key (ailment_id));

create table medical_history (
  med_history_id number(10,0) generated as identity,
  student_id number(10,0),
  condition_id number(10,0),
  last_checkup_date date,
  primary key (med_history_id));

create table diagnosed_condition (
  condition_id number(10,0) generated as identity,
  condition_name varchar2(64),
  primary key (condition_id));

create table medical_record (
  id number(10,0) generated as identity
    constraint MEDICAL_RECORD_NOT_NULL not null,
  student_id number(10,0),
  ailment_id number(10,0),
  nurse_in_charge_id number(10,0),
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
  inventory_id number(10,0) generated as identity
    constraint INVENTORY_NOT_NULL not null,
  medicine_id number(10,0),
  item_type varchar2(60),
  quantity number(10,0),
  expiration_date timestamp(6),
  primary key (inventory_id));

create table medicine (
  medicine_id number(10,0) generated as identity,
  item_name varchar2(50),
  description varchar2(255),
  is_available NUMBER(1,0)
    constraint IS_AVAILABLE_MEDICINE_NOT_NULL not null
    constraint IS_AVAILABLE_MEDICINE_CHECK check (is_available IN (0,1)),
  primary key (medicine_id));

create table medicine_administered (
  med_administered_id number(10,0) generated as identity,
  medicine_id number(10,0),
  med_record_id number(10,0),
  nurse_in_charge_id number(10,0),
  description varchar2(255),
  quantity number(10,0),
  date_administered timestamp(6),
  primary key (med_administered_id));

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
  id number(10,0) generated as identity,
  person_id number(10,0),
  department_id number(10,0),
  employee_number number(10,0),
  date_employed date,
  employment_status varchar(32),
  primary key (id));

create table department (
  id number(10,0) generated as identity,
  department_name varchar2(64),
  primary key (id));

alter table employee
    add constraint FK_EMPLOYEE_PERSON_ID
    foreign key (person_id) references person;

alter table employee
    add constraint FK_EMPLOYEE_DEPARTMENT_ID
    foreign key (department_id) references department;

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

alter table medical_history
    add constraint FK_MEDICAL_HISTORY_STUDENT_ID
    foreign key (student_id) references student;

alter table medical_history
    add constraint FK_MEDICAL_HISTORY_CONDITION_ID
    foreign key (condition_id) references diagnosed_condition;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_STUDENT_ID
    foreign key (student_id) references student;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_AILMENT_ID
    foreign key (ailment_id) references ailments;

alter table medical_record
    add constraint FK_MEDICAL_RECORD_NURSE_IN_CHARGE_ID
    foreign key (nurse_in_charge_id) references employee;

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
insert into person (first_name, middle_name, last_name, birthdate, age, gender, email, address, contact_number)
values ('Cale', 'Mavis', 'Brown', TO_TIMESTAMP('2003-05-04 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), '22', 'MALE', 'cale.mavis@gmail.com', 'Salaban', '09537856189');

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

-- INSERT DEPARTMENT DATA
insert into department (department_name)
values ('Senior High School Department');
insert into department (department_name)
values ('Clinic Department');

-- INSERT EMPLOYEE DATA
insert into employee (person_id, department_id, employee_number, date_employed, employment_status)
values (8, 1, '2020001245', to_date('2020-07-15', 'YYYY-MM-DD'), 'Active');
insert into employee (person_id, department_id, employee_number, date_employed, employment_status)
values (9, 1, '2021000456', to_date('2021-08-15', 'YYYY-MM-DD'), 'Inactive');
insert into employee (person_id, department_id, employee_number, date_employed, employment_status)
values (10, 1, '2024000789', to_date('2024-01-15', 'YYYY-MM-DD'), 'Active');
insert into employee (person_id, department_id, employee_number, date_employed, employment_status)
values (11, 2, '20250001', to_date('2025-01-11', 'YYYY-MM-DD'), 'Active');
insert into employee (person_id, department_id, employee_number, date_employed, employment_status)
values (12, 2, '20220088', to_date('2022-09-15', 'YYYY-MM-DD'), 'Inactive');

-- INSERT AILMENTS DATA
insert into ailments (description)
values ('Headache');
insert into ailments (description)
values ('Cold');
insert into ailments (description)
values ('Fever');
insert into ailments (description)
values ('Dry Cough');
insert into ailments (description)
values ('Stomachache');
insert into ailments (description)
values ('Skin Rash');
insert into ailments (description)
values ('Shortness of Breath');
insert into ailments (description)
values ('Dizziness');
insert into ailments (description)
values ('Sore Throat');
insert into ailments (description)
values ('Allergic Reaction');

--INSERT MEDICINE DATA
insert into medicine (item_name, description, is_available)
values ('Ibuprofen', 'can treat fever, mild to moderate pain, etc', 1);
insert into medicine (item_name, description, is_available)
values ('Cough Syrup', 'Adults with both dry and productive coughs', 1);
insert into medicine (item_name, description, is_available)
values ('Paracetamol', 'temporarily relieve mild-to-moderate pain and fever', 0);
insert into medicine (item_name, description, is_available)
values ('Antacid', 'a medicine used to treat heartburn and indigestion', 1);
insert into medicine (item_name, description, is_available)
values ('Antihistamine', 'treat allergic rhinitis, common cold, influenza, and other allergies', 1);
insert into medicine (item_name, description, is_available)
values ('Aspirin', 'Used to treat pain, fever, or inflammation', 0);
insert into medicine (item_name, description, is_available)
values ('Vitamin C', 'Boosts immunity and promotes overall health', 1);
insert into medicine (item_name, description, is_available)
values ('Hydrocortisone Cream', 'Treats skin irritation, eczema, and rashes', 0);
insert into medicine (item_name, description, is_available)
values ('Decongestant', 'Relieves nasal congestion caused by colds', 0);
insert into medicine (item_name, description, is_available)
values ('Antibiotics', 'Treats bacterial infections in the body', 1);

 --INSERT INVENTORY DATA
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('1', 'Medicine', 50, to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('2', 'Medicine', 30, to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('3', 'Medicine', 40, to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('4', 'Medicine', 25, to_timestamp('2028-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('5', 'Medicine', 20, to_timestamp('2026-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('6', 'Medicine', 60, to_timestamp('2027-06-15 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('7', 'Medicine', 10, to_timestamp('2026-10-10 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('8', 'Medicine', 15, to_timestamp('2025-12-31 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('9', 'Medicine', 50, to_timestamp('2026-03-20 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_id, item_type, quantity, expiration_date)
values ('10', 'Medicine', 4, to_timestamp('2027-08-25 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));

--INSERT DIAGNOSED CONDITION
insert into diagnosed_condition (condition_name)
values ('Asthma');
insert into diagnosed_condition (condition_name)
values ('Depression');
insert into diagnosed_condition (condition_name)
values ('Scoliosis');
insert into diagnosed_condition (condition_name)
values ('Panic Disorder');
insert into diagnosed_condition (condition_name)
values ('Diabetes');

--INSERT MEDICAL HISTORY DATA
insert into medical_history (student_id, condition_id, last_checkup_date)
values (1, 1, to_date('2025-07-15', 'YYYY-MM-DD'));
insert into medical_history (student_id, condition_id, last_checkup_date)
values (2, 1, to_date('2021-06-10', 'YYYY-MM-DD'));
insert into medical_history (student_id, condition_id, last_checkup_date)
values (3, 3, to_date('2020-08-08', 'YYYY-MM-DD'));
insert into medical_history (student_id, condition_id, last_checkup_date)
values (4, 4, to_date('2019-05-22', 'YYYY-MM-DD'));
insert into medical_history (student_id, condition_id, last_checkup_date)
values (5, 2, to_date('2023-08-19', 'YYYY-MM-DD'));

--INSERT MEDICAL RECORD DATA
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (1, 1, 4, 'Headache', '37.5°C', '130/85', 88, 18, to_timestamp('2000-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed pain reliever', 1);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (2, 2, 5, 'Stomachache', '37.2°C', '140/90', 92, 20, to_timestamp('2008-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Rest and hydration', 0);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (3, 3, 5, 'Fever', '38.5°C', '120/80', 85, 19, to_timestamp('2025-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antipyretic', 1);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (4, 4, 4, 'Dry cough', '37.0°C', '110/70', 78, 16, to_timestamp('2024-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed cough syrup', 0);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (5, 1, 4, 'Dizziness', '37.0°C', '115/75', 72, 15, to_timestamp('2020-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Recommended hydration', 1);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (3, 1, 5, 'Headache', '37.0°C', '125/85', 80, 17, to_timestamp('2023-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed pain reliver', 0);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (6, 6, 4, 'Skin rash', '36.9°C', '118/78', 75, 16, TO_TIMESTAMP('2022-04-20 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antihistamine cream', 1);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (1, 9, 4, 'Sore throat', '37.4°C', '122/82', 78, 17, TO_TIMESTAMP('2021-03-15 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Recommended salt water gargle', 1);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (1, 8, 5, 'Dizziness', '36.8°C', '121/81', 74, 16, TO_TIMESTAMP('2020-08-14 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Recommended hydration', 0);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (4, 10, 5, 'Allergic reaction', '36.7°C', '125/85', 80, 17, TO_TIMESTAMP('2023-09-30 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed antihistamine tablets', 1);
insert into medical_record (student_id, ailment_id, nurse_in_charge_id, symptoms, temperature_readings, blood_pressure, pulse_rate, respiratory_rate, visit_date, treatment, is_active)
values (7, 7, 4, 'Shortness of breath', '37.0°C', '135/90', 95, 22, TO_TIMESTAMP('2024-06-05 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'Prescribed inhaler', 0);

--INSERT MEDICINE ADMINISTERED
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (1, 1, 4, 'Ibuprofen 200mg administered', 1, to_timestamp('2000-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (2, 2, 5, 'Cough syrup 10ml administered', 2, to_timestamp('2008-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (3, 3, 5, 'Paracetamol 500mg administered', 1, to_timestamp('2025-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (4, 4, 4, 'Antacid 500mg administered', 2, to_timestamp('2024-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (5, 5, 4, 'Vitamin C 500mg administered', 1, to_timestamp('2020-01-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (6, 6, 5, 'Ibuprofen 200mg administered', 1, TO_TIMESTAMP('2023-02-15 09:30:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (7, 7, 4, 'Antihistamine cream administered', 2, TO_TIMESTAMP('2022-07-10 15:45:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (8, 8, 4, 'Ibuprofen 400mg administered', 1, TO_TIMESTAMP('2021-05-25 12:20:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (9, 9, 5, 'Decongestant 10ml administered', 1, TO_TIMESTAMP('2020-10-01 08:15:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into medicine_administered (medicine_id, med_record_id, nurse_in_charge_id, description, quantity, date_administered)
values (10, 10, 5, 'Antibiotics 500mg administered', 1, TO_TIMESTAMP('2023-03-20 11:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));

commit;