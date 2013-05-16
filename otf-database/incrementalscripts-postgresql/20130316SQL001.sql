------------------
-- Yvan Jordens --
------------------

alter table GEBRUIKER add column picture bytea null;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130316SQL001');