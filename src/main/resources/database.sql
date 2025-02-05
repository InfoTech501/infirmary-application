drop user nursedesk cascade;

create user nursedesk identified by Changeme2024;

alter user nursedesk quota unlimited on USERS;

grant create session to nursedesk with admin option;

grant connect to nursedesk;

alter session set current_schema = nursedesk;

drop table patient cascade constraints;

create table patient (
    id number(20,0) generated as identity
        constraint PATIENT_NOT_NULL NOT NULL,
    patient_id number(20,0)
        constraint PATIENT_ID_NOT_NULL NOT NULL
        constraint PATIENT_ID_UNIQUE unique,
    student_number varchar2(20),
    first_name varchar2(50 char),
    middle_name varchar2(35 char),
    last_name varchar2(35 char),
    symptoms varchar2(255 char),
    added_remarks varchar2(255 char),
    temperature_readings varchar(10),
    visit_date timestamp(6),
    time_in timestamp(6),
    time_out timestamp(6),
    medications_administered varchar2(60 char),
    nurse_in_charge varchar2(255 char),
    constraint PATIENT_PK primary key(id));

   insert into patient (patient_id, first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
   values ('001', 'Kim Yashai', 'Legaspi', 'Panlilio', 'headache', 'consider further neurological evaluation', '36.5 C', TO_TIMESTAMP('2025-01-03 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-01-03 08:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-01-03 12:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
   insert into patient (patient_id, first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
   values ('002', 'John', 'Cruz', 'Doe', 'fever', 'none', '38.6 C', TO_TIMESTAMP('2025-02-01 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-02-01 08:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-02-01 11:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
   insert into patient (patient_id, first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
   values ('003', 'kale', 'Angcanan', 'Bayot', 'cough', 'none', '36.5 C', TO_TIMESTAMP('2025-02-08 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-02-08 07:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-02-08 11:23:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
   insert into patient (patient_id, first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
   values ('004', 'lei', 'Villanueva', 'Bayot', 'sore throat', 'none', '36.5 C', TO_TIMESTAMP('2025-01-03 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-01-03 06:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-01-03 10:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');
   insert into patient (patient_id, first_name, middle_name, last_name, symptoms, added_remarks, temperature_readings, visit_date, time_in, time_out, medications_administered, nurse_in_charge)
   values ('005', 'joe', 'bill', 'Bayot', 'sore throat', 'none', '36.5 C', TO_TIMESTAMP('2025-03-03 00:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), TO_TIMESTAMP('2025-03-03 06:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'),  TO_TIMESTAMP('2025-03-03 10:00:00.00', 'yyyy-mm-dd hh24:mi:ss.ff'), 'Acetaminophen (Tylenol)', 'Nurse K');