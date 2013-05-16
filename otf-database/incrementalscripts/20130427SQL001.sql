------------------
-- Yvan Jordens --
------------------

create table WAGEN_ONTVANGST (
    id                      varchar(50) not null,
    VERSIE                  bigint DEFAULT 0 NOT NULL,
    creatietijdstiputc      timestamp without time zone default CURRENT_TIMESTAMP NOT NULL,
    wijzigingstijdstiputc   timestamp without time zone,
    creatieGebruiker_id     varchar(50),
    wijzigingsGebruiker_id  varchar(50),
    reservatie_id           varchar(50),
    wagen_id                varchar(50),
    reserveerder_id         varchar(250),
    ontvangsttijdstiputc    timestamp without time zone,
    opmerking               varchar(500),
    constraint PK_WAGEN_ONTVANGST primary key (id) using index tablespace pg_default,
    constraint FK01_WAGEN_ONTVANGST foreign key (creatieGebruiker_id) references GEBRUIKER(ID),
    constraint FK02_WAGEN_ONTVANGST foreign key (wijzigingsGebruiker_id) references GEBRUIKER(ID) ,
    constraint FK03_WAGEN_ONTVANGST foreign key (reserveerder_id) references GEBRUIKER(ID),
    constraint FK04_WAGEN_ONTVANGST foreign key (wagen_id) references WAGEN(ID)
);

CREATE UNIQUE INDEX UN01_WAGEN_ONTVANGST ON WAGEN_ONTVANGST(reservatie_id);
create index I_FK01_WAGEN_ONTVANGST on WAGEN_ONTVANGST(creatieGebruiker_id) tablespace pg_default;
create index I_FK02_WAGEN_ONTVANGST on WAGEN_ONTVANGST(wijzigingsGebruiker_id) tablespace pg_default;
create index I_FK03_WAGEN_ONTVANGST on WAGEN_ONTVANGST(reserveerder_id) tablespace pg_default;
create index I_FK04_WAGEN_ONTVANGST on WAGEN_ONTVANGST(wagen_id) tablespace pg_default;

create sequence WAGEN_ONTVANGST_SEQ;

create table RESERVATIE (
    id                      varchar(50) not null,
    VERSIE                  bigint DEFAULT 0 NOT NULL,
    creatietijdstiputc      timestamp without time zone default CURRENT_TIMESTAMP NOT NULL,
    wijzigingstijdstiputc   timestamp without time zone,
    creatieGebruiker_id     varchar(50),
    wijzigingsGebruiker_id  varchar(50),
    annulatieTijdstipUtc    timestamp without time zone default CURRENT_TIMESTAMP NULL,
    annulatieGebruikerId    varchar(50) NULL,
    reservatie_nummer       bigint NOT NULL,
    wagen_id                varchar(50) NOT NULL,
    datum_begin             timestamp without time zone NOT NULL,
    datum_tot               timestamp without time zone NOT NULL,
    doel                    varchar(500) NOT NULL,
    constraint PK_RESERVATIE primary key (id) using index tablespace pg_default,
    constraint FK01_RESERVATIE foreign key (creatieGebruiker_id) references GEBRUIKER(ID),
    constraint FK02_RESERVATIE foreign key (wijzigingsGebruiker_id) references GEBRUIKER(ID) ,
    constraint FK03_RESERVATIE foreign key (wagen_id) references WAGEN(ID)
    constraint FK04_RESERVATIE foreign key (annulatieGebruikerId) references GEBRUIKER(ID)
);

CREATE UNIQUE INDEX UN01_RESERVATIE ON RESERVATIE(reservatie_nummer);
create index I_FK01_RESERVATIE on RESERVATIE(creatieGebruiker_id) tablespace pg_default;
create index I_FK02_RESERVATIE on RESERVATIE(wijzigingsGebruiker_id) tablespace pg_default;
create index I_FK03_RESERVATIE on RESERVATIE(wagen_id) tablespace pg_default;
create index I_FK04_RESERVATIE on RESERVATIE(annulatieGebruikerId) tablespace pg_default;

create sequence RESERVATIE_SEQ;

-----------------------------------
-- scriptid                      --
-----------------------------------
insert into dbscript  (scriptnaam) values ('20130427SQL001');