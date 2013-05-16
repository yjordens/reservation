------------------
-- Yvan Jordens --
------------------

alter table GEBRUIKER add column ROLE varchar2(50 CHAR) NULL;
update GEBRUIKER set ROLE = 'ADMIN' where user_id = 'jordenyv';
alter table GEBRUIKER modify column ROLE NOT NULL;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130509SQL001');