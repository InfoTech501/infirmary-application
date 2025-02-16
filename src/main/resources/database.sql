drop user infirmary cascade;

create user infirmary identified by Changeme0;

alter user infirmary quota unlimited on DATA;

alter user infirmary quota unlimited on USERS;

grant create session to infirmary with admin option;

grant connect to infirmary;

alter session set current_schema = infirmary;


create table inventory (
  inventory_id number(20,0) generated as identity
    constraint INVENTORY_NOT_NULL not null,
  medicine_id varchar2(50 char),
  item_type varchar2(60),
  description varchar2(255),
  quantity_available number(10,0),
  primary key (inventory_id));


  insert into inventory (medicine_id, item_type, description, quantity_available)
  values ('IB', 'Medicine', 'Ibuprofen', 50);
  insert into inventory (medicine_id, item_type, description, quantity_available)
  values ('CS', 'Medicine', 'Cough syrup', 30);
  insert into inventory (medicine_id, item_type, description, quantity_available)
  values ('PT', 'Medicine', 'Paracetamol', 40);
  insert into inventory (medicine_id, item_type, description, quantity_available)
  values ('AC', 'Medicine', 'Antacid', 25);
  insert into inventory (medicine_id, item_type, description, quantity_available)
  values ('AH', 'Medicine', 'Antihistamine', 20);
