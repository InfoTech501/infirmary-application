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


