------------------
-- Yvan Jordens --
------------------

create table GEBRUIKER (
    id                      varchar(50) not null,
    VERSIE                  bigint DEFAULT 0 NOT NULL,
    creatietijdstiputc      timestamp without time zone default CURRENT_TIMESTAMP NOT NULL,
    wijzigingstijdstiputc   timestamp without time zone,
    creatieGebruiker_id     varchar(50),
    wijzigingsGebruiker_id  varchar(50),
    inszNummer              varchar(11),
    naam                    varchar(250),
    voornaam                varchar(250),
    initialen               varchar(3),
    telefoonnummer          varchar(50),
    email                   varchar(100),
    user_id                 varchar(50) not null,
    wachtwoord              varchar(41) not null,
    actief                  BOOLEAN DEFAULT false not null,
    constraint PK_GEBRUIKER primary key (id),
    constraint FK01_GEBRUIKER foreign key (creatieGebruiker_id) references GEBRUIKER,
    constraint FK02_GEBRUIKER foreign key (wijzigingsGebruiker_id) references GEBRUIKER
);

GRANT SELECT, INSERT, UPDATE, DELETE ON gebruiker TO yjordens_app;

CREATE UNIQUE INDEX UN01_GEBRUIKER ON GEBRUIKER(user_id);
create index I_FK01_GEBRUIKER on GEBRUIKER(creatieGebruiker_id);
create index I_FK02_GEBRUIKER on GEBRUIKER(wijzigingsGebruiker_id);

create sequence GEBRUIKER_SEQ;
GRANT SELECT, INSERT, UPDATE, DELETE ON GEBRUIKER_SEQ TO yjordens_essers;

insert into gebruiker(id, naam, voornaam, user_id, wachtwoord, actief) values (nextval('GEBRUIKER_SEQ'),'Jordens','Yvan','jordenyv','4d7547a1d2787c72f0e985d8b5194295e4aa6141',true);

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130315SQL002');