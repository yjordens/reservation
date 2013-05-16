------------------
-- Yvan Jordens --
------------------

create table WAGEN (
    id                      varchar(50) not null,
    VERSIE                  bigint DEFAULT 0 NOT NULL,
    creatietijdstiputc      timestamp without time zone default CURRENT_TIMESTAMP NOT NULL,
    wijzigingstijdstiputc   timestamp without time zone,
    creatieGebruiker_id     varchar(50),
    wijzigingsGebruiker_id  varchar(50),
    nummerplaat              varchar(50),
    merk                    varchar(250),
    merktype                varchar(250),
    brandstof               varchar(50),
    actief                  BOOLEAN DEFAULT false not null,
    constraint PK_WAGEN primary key (id),
    constraint FK01_WAGEN foreign key (creatieGebruiker_id) references GEBRUIKER,
    constraint FK02_WAGEN foreign key (wijzigingsGebruiker_id) references GEBRUIKER
);

CREATE UNIQUE INDEX UN01_WAGEN ON WAGEN(nummerplaat);
create index I_FK01_WAGEN on WAGEN(creatieGebruiker_id);
create index I_FK02_WAGEN on WAGEN(wijzigingsGebruiker_id);

GRANT SELECT, INSERT, UPDATE, DELETE ON WAGEN TO yjordens_essers;

create sequence WAGEN_SEQ;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130421SQL001');