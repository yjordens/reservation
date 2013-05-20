------------------
-- Yvan Jordens --
------------------

alter table RESERVATIE add column wagenontvangst_id varchar(50) NULL;
alter table RESERVATIE add CONSTRAINT FK05_RESERVATIE foreign key (wagenontvangst_id) references WAGEN_ONTVANGST(ID)
create index I_FK05_RESERVATIE on RESERVATIE(wagenontvangst_id);
alter table WAGEN_ONTVANGST drop column reservatie_id;
alter table WAGEN_ONTVANGST drop column wagen_id;
alter table WAGEn add column kilometerStand  bigint DEFAULT 0 NOT NULL;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130518SQL001');