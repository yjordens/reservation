------------------
-- Yvan Jordens --
------------------

alter table GEBRUIKER add column ROLE varchar(50) NULL;
update GEBRUIKER set ROLE = 'ADMIN' where user_id = 'jordenyv';
alter table GEBRUIKER alter column ROLE SET NOT NULL;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130509SQL001');