------------------
-- Yvan Jordens --
------------------


CREATE TABLE nvpair (
    id                      varchar(50) not null,
    VERSIE                  bigint DEFAULT 0 NOT NULL,
    creatieTijdstip         timestamp without time zone default CURRENT_TIMESTAMP NOT NULL,
    wijzigingsTijdstip      timestamp without time zone,
    creatieGebruiker_id     varchar(50),
    wijzigingsGebruiker_id  varchar(50),
    name                    VARCHAR(250) NOT NULL,
    textValue               VARCHAR(500) NULL,
    numericValue            bigint NULL,
    timeValue               timestamp without time zone NULL,
    constraint PK_nvpair primary key (id),
    constraint FK01_NVPAIR foreign key (creatieGebruiker_id) references GEBRUIKER,
    constraint FK02_NVPAIR foreign key (wijzigingsGebruiker_id) references GEBRUIKER
);

CREATE UNIQUE INDEX UN01_NVPAIR ON nvpair(name);
create index I_FK01_NVPAIR on nvpair(creatieGebruiker_id);
create index I_FK02_NVPAIR on nvpair(wijzigingsGebruiker_id);

GRANT SELECT, INSERT, UPDATE, DELETE ON nvpair TO yjordens_app;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130329SQL001');