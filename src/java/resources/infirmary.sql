drop user infirmary cascade;

create user infirmary identified by Changeme0;

alter user infirmary quota unlimited on USERS;

grant create session to infirmary with admin option;

grant connect to infirmary;

alter session set current_schema = infirmary;

drop table student cascade constraints;
drop table inventory cascade constraints;
drop table employee cascade constraints;
drop table login cascade constraints;
drop table frequent_visit_report cascade constraints;
drop table patient cascade constraints;
drop table medication_trends cascade constraints;
drop table common_ailments_report cascade constraints;

create table student (
    id number(10,0) generated as identity
        constraint STUD_ID_NOT_NULL NOT NULL,
    student_number varchar2(20)
        constraint STUDENT_NUMER_NOT NOT NULL
        constraint STUDENT_NUMBER_UNIQUE unique,
    first_name varchar2(50 char),
    middle_name varchar2(35 char),
    last_name varchar(35 char),
    address varchar2(255 char) NOT NULL,
    age number(2,0),
    strand varchar2(255 char)
        constraint STRAND_CHECK check (STRAND IN ('GAS', 'ABM', 'STEM', 'TVL', 'HUMSS')),
    grade_level varchar2(50 char),
    section varchar2(50 char),
    LRN number(12,0)
        constraint LRN_NOT_NULL NOT NULL
        constraint LRN_UNIQUE UNIQUE,
    birthdate timestamp(6),
    gender varchar2(10 char),
    email varchar2(64 char) NOT NULL,
    contact_number varchar2(11 char) NOT NULL,
    guardian_contact_number varchar2(11 char) NOT NULL,
    constraint STUDENT_PK primary key (id));

create table login (
    id number(20,0) generated as identity
        constraint LOGIN_ID_NOT_NULL NOT NULL,
    username varchar2(255 char),
    password varchar2(255 char),
    join_date timestamp(6),
    last_login_date timestamp(6),
    role varchar2(255 char),
    authorities varchar2(255 char)NOT NULL,
    is_active number(1,0)
        constraint IS_ACTIVE_NOT_NULL NOT NULL
		constraint IS_ACTIVE_CHECK CHECK (IS_ACTIVE in (0,1)),
    is_locked number (1,0)
        constraint IS_LOCKED_NOT_NULL NOT NULL
		constraint IS_LOCKED_CHECK CHECK (IS_LOCKED in (0,1)),
    constraint LOGIN_PK primary key (id));

create table patient (
    id number(20,0) generated as identity
        constraint PATIENT_NOT_NULL NOT NULL,
    patient_id number(20,0)
        constraint PATIENT_ID_NOT_NULL NOT NULL
        constraint PATIENT_ID_UNIQUE unique,
    student_number varchar2(20) not null,
    first_name varchar2(50 char),
    middle_name varchar2(35 char),
    last_name varchar2(35 char),
    grade_level varchar2(50 char),
    symptoms varchar2(255 char),
    added_remarks varchar2(255 char),
    temperature_readings varchar(10),
    visit_date timestamp(6),
    time_in timestamp(6),
    time_out timestamp(6),
    medications_administered varchar2(60 char),
    nurse_in_charge varchar2(255 char),
    constraint PATIENT_PK primary key(id));

create table inventory (
    medicine_id number (20,0) generated as identity
        constraint INVENTORY_NOT_NULL NOT NULL,
    medicine_name varchar2(60 char),
    brand varchar2(60 char),
    dosage varchar(50),
    med_description varchar2(255 char),
    quantity_available number(10,0),
    quantity_used number(10,0),
    med_total_usage number(10,0),
    expiration_date timestamp(6),
    primary key(medicine_id));

create table medication_trends (
    id number(10,0)
        constraint MEDICATION_TREND_NOT_NULL NOT NULL,
    medicine_id number(20,0) not null,
    med_trend_date timestamp(6),
    primary key(id));

create table frequent_visit_report (
    id number(20,0) generated as identity
        constraint STUDENT_FREQUENT_ID_NOT_NULL NOT NULL,
    total_visit number(20,0),
    primary key (id));

create table common_ailments_report (
    id number(20,0)
        constraint COMMON_AILMENTS_NOT_NULL NOT NULL,
    patient_id number(20,0) not null,
    report_date timestamp(6),
    primary key (id));

alter table login
    add constraint FK_STUDENT_LOGIN
    foreign key (id) references Student;

alter table frequent_visit_report
    add constraint FK_PATIENT_FREQUENT_VISIT
    foreign key (id) references patient;

alter table medication_trends
    add constraint FK_INVENTORY_MEDICATION_TRENDS
    foreign key (medicine_id) references inventory;

alter table common_ailments_report
    add constraint FK_PATIENT_COMMON_AILMENTS
    foreign key (patient_id) references patient;

alter table patient
    add constraint FK_STUDENT_PATIENT
    foreign key (id) references student;

insert into student (student_number, first_name, middle_name, last_name, LRN, age, birthdate, gender, strand, grade_level, section, address, email, contact_number, guardian_contact_number)
values ('TCSNHS-0011', 'Kim Yashai', 'Legaspi', 'Panlilio', '112345678901', '21', to_timestamp('2003-11-17 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'FEMALE', 'GAS', 'Grade 11', 'St.Fatima', 'Loma, Amadeo', 'kypl@gmail.com', '09123456781', '09824657139');
insert into student (student_number, first_name, middle_name, last_name, LRN, age, birthdate, gender, strand, grade_level, section, address, email, contact_number, guardian_contact_number)
values ('TCSNHS-0001', 'Alexandra Marie Isabela', null, 'Collins', '138254968527', '21', to_timestamp('2005-12-02 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'FEMALE', 'STEM', 'Grade 12', 'St.Fatima', 'Silang, Cavite', 'alexmi@gmail.com', '09694285173', '09152489365');
insert into student (student_number, first_name, middle_name, last_name, LRN, age, birthdate, gender, strand, grade_level, section, address, email, contact_number, guardian_contact_number)
values ('TCSNHS-0002' , 'John', 'Miller', 'Collins', '192465783152', '20', to_timestamp('2001-01-19 20:00:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'MALE', 'ABM', 'Grade 11', 'St.Faustina', 'Tagaytay', 'johnmiller@gmail.com', '09954281365', '09974821563');
insert into student (student_number, first_name, middle_name, last_name, LRN, age, birthdate, gender, strand, grade_level, section, address, email, contact_number, guardian_contact_number)
values ('TCSNHS-0003', 'Nathaniel Pritz', null, 'Cruz', '152486315723', '19', to_timestamp('2002-05-16 16:23:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'MALE', 'HUMSS', 'Grade 11', 'St.Hannibal', 'Lalaan, Silang, Cavite', 'nathanielcruz@gmail.com', '09958721632', '09842517365');
insert into student (student_number, first_name, middle_name, last_name, LRN, age, birthdate, gender, strand, grade_level, section, address, email, contact_number, guardian_contact_number)
values ('TCSNHS-0004', 'Steve', 'Bayot', 'Villanueva', '138246248951', '20', to_timestamp('2001-10-20 20:23:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'), 'MALE', 'TVL', 'Grade 12', 'St.Francis Xavier', 'Amadeo, Cavite', 'stevevillanueva@gmail.com', '09852163247', '09854627159');


insert into inventory (medicine_name, brand, dosage, med_description, quantity_available, quantity_used, med_total_usage, expiration_date)
values ('paracetamol', 'tylenol', '500mg', 'Pain and fever (high temperature) in adults and children', '50', '10', '700', to_timestamp('2025-01-12 21:10:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_name, brand, dosage, med_description, quantity_available, quantity_used, med_total_usage, expiration_date)
values ('loperamide', 'imodium', '2mg', 'Anti-diarrheal', '75', '25', '100', to_timestamp('2024-11-30 00:10:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_name, brand, dosage, med_description, quantity_available, quantity_used, med_total_usage, expiration_date)
values ('ibuprofen', 'advil', '200mg', 'Pain reliever, anti-inflammatory', '80', '30', '110', to_timestamp('2024-12-05 21:10:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_name, brand, dosage, med_description, quantity_available, quantity_used, med_total_usage, expiration_date)
values ('Amoxicillin', 'amoxil', '250mg', 'antibiotic', '50', '20', '70', to_timestamp('2025-09-01 21:10:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));
insert into inventory (medicine_name, brand, dosage, med_description, quantity_available, quantity_used, med_total_usage, expiration_date)
values ('cetirizine', 'zyrtec', '10mg', 'antihistamine', '120', '60', '180', to_timestamp('2025-03-22 21:10:00.00', 'yyyy-mm-dd hh24:mi:ss:ff'));



insert into patient (patient_id, student_number, first_name, middle_name, last_name, grade_level, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
values ('006', 'TCSNHS-0011', 'Kim Yashai', 'Legaspi', 'Panlilio', 'grade 11', 'headache', 'consider further neurological evaluation', '36.5 C', TO_TIMESTAMP('2025-01-03 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-01-03 08:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-01-03 12:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
insert into patient (patient_id, student_number, first_name, middle_name, last_name, grade_level, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
values ('007', 'TCSNHS-0022', 'John', 'Cruz', 'Doe', 'fever', 'grade 11', 'none', '38.6 C', TO_TIMESTAMP('2025-02-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-02-01 08:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-02-01 11:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
insert into patient (patient_id, student_number, first_name, middle_name, last_name, grade_level, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
values ('008', 'TCSNHS-0033', 'kale', 'Angcanan', 'Bayot', 'grade 12', 'cough', 'none', '36.5 C', TO_TIMESTAMP('2025-02-08 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-02-08 07:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-02-08 11:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
insert into patient (patient_id, student_number, first_name, middle_name, last_name, grade_level, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
values ('009', 'TCSNHS-0044', 'lei', 'Villanueva', 'Bayot', 'grade 12', 'sore throat', 'none', '36.5 C', TO_TIMESTAMP('2025-01-03 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-01-03 06:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-01-03 10:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
insert into patient (patient_id, student_number, first_name, middle_name, last_name, grade_level, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
values ('010', 'TCSNHS-0055', 'joe', 'bill', 'Bayot', 'grade 11', 'sore throat', 'none', '36.5 C', TO_TIMESTAMP('2025-03-03 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-03-03 06:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-03-03 10:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
