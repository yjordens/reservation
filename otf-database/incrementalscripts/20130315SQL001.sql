------------------
-- Yvan Jordens --
------------------

create sequence DBSCRIPT_SEQ;

CREATE TABLE DBSCRIPT
(
    OBJECTID integer DEFAULT nextval('DBSCRIPT_SEQ') NOT NULL ,
    SCRIPTNAAM VARCHAR(50) NOT NULL,
    DATUM_CREATIE DATE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    DUPLICAATSCRIPT VARCHAR(50),
    CONSTRAINT PK_DBSCRIPT PRIMARY KEY (OBJECTID)
);

CREATE UNIQUE INDEX UN01_DBSCRIPT ON DBSCRIPT(scriptnaam) tablespace pg_default;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130315SQL001');