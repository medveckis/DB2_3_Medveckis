drop type SKOLASBIEDRS force;
create or replace type SKOLASBIEDRS as object (
  SKOLASBIEDRS_ID number,
  VARDS           varchar2(50),
  UZVARDS         varchar2(50),
  E_PAST          varchar2(120),
  TELEFONA_NUMURS varchar2(50),
  DZIVES_VIETA    REF ADRESE, 
  MEMBER FUNCTION E_PAST_METHOD RETURN STRING
);

CREATE OR REPLACE TYPE BODY SKOLASBIEDRS AS
MEMBER FUNCTION E_PAST_METHOD RETURN STRING IS
BEGIN
  RETURN SELF.VARDS || '.' || SELF.UZVARDS || '@inbox.lv';
END E_PAST_METHOD;
END;

drop type SKOLNIEKS force;
create or replace type SKOLNIEKS as object (
  SKOLNIEKS_ID number,
  SKOLNIEKS    SKOLASBIEDRS,
  KLASE_ID     number
);

drop table SKOLNIEKI;
create table SKOLNIEKI of SKOLNIEKS;

insert into SKOLNIEKI values(1, SKOLASBIEDRS(1,'Maksims', 'Medveckis', 'm.m@g.com', '111', null), 1);
insert into SKOLNIEKI values(2, SKOLASBIEDRS(1,'Georgijs', 'Petorvs', 'm.m@g.com', '222', null), 1);
insert into SKOLNIEKI values(3, SKOLASBIEDRS(1,'Denis', 'Sutugins', 'd.s@g.com', '333', null), 1);
insert into SKOLNIEKI values(1, SKOLASBIEDRS(1,'Maksims', 'Medveckis', 'm.m@g.com', '111', null), null);

select S.skolnieks_id, DEREF(S.skolnieks.dzives_vieta) from skolnieki S;
select * from adreses;
select s.skolnieks.e_past_method() as e_past from skolnieki s;

declare
ats REF ADRESE;
begin
select REF(A) into ats from ADRESES A where A.ADRESE_ID = 1;
update SKOLNIEKI S set S.SKOLNIEKS.DZIVES_VIETA = ats where S.SKOLNIEKS_ID = 1;

select REF(A) into ats from ADRESES A where A.ADRESE_ID = 2;
update SKOLNIEKI S set S.SKOLNIEKS.DZIVES_VIETA = ats where S.SKOLNIEKS_ID = 2;

select REF(A) into ats from ADRESES A where A.ADRESE_ID = 3;
update SKOLNIEKI S set S.SKOLNIEKS.DZIVES_VIETA = ats where S.SKOLNIEKS_ID = 3;
end;

delete from skolnieki s where s.skolnieks_id = 78;

create of replace trigger test_trigger
after delete
on skolnieki for each row
declare
a_id number;
pragma autonomous_transaction;
begin
select DEREF(skolnieks.dzives_vieta).adrese_id into a_id from dual;
delete from adreses as a where a_id = a.adrese_id;
end;





CREATE TABLE medical_bills
(
    BILL_ID number(10) primary key,
    BILL_NUMBER varchar2(20),
    PARTY_NAME varchar2(50),
    BILL_DATE date,
    CREATED_BY varchar2(20),
    CREATED_DATE date
);

CREATE TABLE medical_bills_history
(
    BILL_ID number(10),
    BILL_NUMBER varchar2(20),
    PARTY_NAME varchar2(50),
    BILL_DATE date,
    DELETED_BY varchar2(20),
    DELETED_DATE date
);

CREATE OR REPLACE TRIGGER trg_after_delete_skolnieks
AFTER DELETE
  ON skolnieki
  FOR EACH ROW
DECLARE
username varchar2(10);

BEGIN

  -- current login user, in this example, system
  SELECT user INTO username FROM dual;
	
  DELETE FROM adreses a WHERE a.adrese_id = deref(:old.skolnieks.dzives_vieta).adrese_id;
  
END;