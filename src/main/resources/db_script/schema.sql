
CREATE TABLE rettighet (
  rettighet_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  navn         VARCHAR(255) NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE emner (
  emnekode VARCHAR(255) NOT NULL PRIMARY KEY,
  emnenavn VARCHAR(255)
)
  ENGINE = InnoDB;

CREATE TABLE koe (
  koe_id INT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  aapen  TINYINT NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE brukere (
  mail         VARCHAR(255) NOT NULL PRIMARY KEY,
  rettighet_id INT          NOT NULL,
  fornavn      VARCHAR(255) NOT NULL,
  etternavn    VARCHAR(255) NOT NULL,
  passord      VARCHAR(32)  NOT NULL,
  aktiv        TINYINT      NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE emner_brukere (
  emnekode  VARCHAR(255) NOT NULL,
  mail      VARCHAR(255) NOT NULL,
  foreleser TINYINT      NOT NULL,
  CONSTRAINT emner_brukere_pk PRIMARY KEY (emnekode, mail)
)
  ENGINE = InnoDB;

CREATE TABLE plassering (
  plassering_navn VARCHAR(255) NOT NULL PRIMARY KEY,
  ant_bord        INT
)
  ENGINE = InnoDB;

CREATE TABLE koe_gruppe (
  koe_id          INT          NOT NULL,
  gruppe_id       INT          NOT NULL,
  plassering_navn VARCHAR(255) NOT NULL,
  bordnummer      INT          NOT NULL,
  koe_plass       INT          NOT NULL,
  info            VARCHAR(255),
  faar_hjelp      VARCHAR(255),
  tidspunkt       TIMESTAMP,
  CONSTRAINT koe_gruppe_pk PRIMARY KEY (koe_id, gruppe_id)
)
  ENGINE = InnoDB;

CREATE TABLE gruppe (
  koe_id INT             NOT NULL,
  gruppe_id INT,
  mail      VARCHAR(255) NOT NULL,
  leder     INT          NOT NULL DEFAULT 0,
  CONSTRAINT gruppe_pk PRIMARY KEY (koe_id, gruppe_id, mail)
)
  ENGINE = InnoDB;

CREATE TABLE gruppe_oving (
  gruppe_id INT           NOT NULL,
  koe_id    INT           NOT NULL,
  oving_id  INT           NOT NULL,
  CONSTRAINT gruppe_oving_pk PRIMARY KEY (gruppe_id, koe_id, oving_id)
)
  ENGINE = InnoDB;

CREATE TABLE delemne (
  delemne_nr   INT          NOT NULL,
  emnekode     VARCHAR(255) NOT NULL,
  koe_id       INT          NOT NULL,
  delemnenavn  VARCHAR(255) NOT NULL,
  semester     VARCHAR(1)   NOT NULL,
  ant_ovinger  INT,
  ovingsregler VARCHAR(225),
  CONSTRAINT pk_delemne PRIMARY KEY (delemne_nr, emnekode)
)
  ENGINE = InnoDB;

CREATE TABLE delemne_brukere (
  mail       VARCHAR(255) NOT NULL,
  emnekode   VARCHAR(255) NOT NULL,
  delemne_nr INT          NOT NULL,
  CONSTRAINT pk_delemne_brukere PRIMARY KEY (mail, emnekode, delemne_nr)
)
  ENGINE = InnoDB;

CREATE TABLE oving (
  oving_id   INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
  oving_nr   INT          NOT NULL,
  emnekode   VARCHAR(255) NOT NULL,
  delemne_nr INT          NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE oving_brukere (
  oving_id     INT          NOT NULL,
  mail         VARCHAR(255) NOT NULL,
  godkjent_av  VARCHAR(255),
  godkjent_tid TIMESTAMP,
  CONSTRAINT pk_oving_brukere PRIMARY KEY (oving_id, mail)
)
  ENGINE = InnoDB;




ALTER TABLE brukere ADD CONSTRAINT brukere_fk1 FOREIGN KEY (rettighet_id) REFERENCES rettighet (rettighet_id);

ALTER TABLE delemne ADD CONSTRAINT delemne_fk1 FOREIGN KEY (emnekode) REFERENCES emner (emnekode)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE delemne ADD CONSTRAINT delemne_fk2 FOREIGN KEY (koe_id) REFERENCES koe (koe_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE oving ADD CONSTRAINT oving_fk1 FOREIGN KEY (delemne_nr, emnekode) REFERENCES delemne (delemne_nr, emnekode)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE emner_brukere ADD CONSTRAINT emner_brukere_fk1 FOREIGN KEY (emnekode) REFERENCES emner (emnekode)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE emner_brukere ADD CONSTRAINT emner_brukere_fk2 FOREIGN KEY (mail) REFERENCES brukere (mail)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE oving_brukere ADD CONSTRAINT oving_brukere_fk1 FOREIGN KEY (oving_id) REFERENCES oving (oving_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE oving_brukere ADD CONSTRAINT oving_brukere_fk2 FOREIGN KEY (mail) REFERENCES brukere (mail)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE delemne_brukere ADD CONSTRAINT delemne_brukere_fk1 FOREIGN KEY (mail) REFERENCES brukere (mail)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE delemne_brukere ADD CONSTRAINT delemne_brukere_fk2 FOREIGN KEY (delemne_nr, emnekode) REFERENCES delemne (delemne_nr, emnekode)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE koe_gruppe ADD CONSTRAINT koe_gruppe_fk1 FOREIGN KEY (koe_id) REFERENCES koe (koe_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE koe_gruppe ADD CONSTRAINT koe_gruppe_fk2 FOREIGN KEY (plassering_navn) REFERENCES plassering (plassering_navn)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE koe_gruppe ADD CONSTRAINT koe_gruppe_fk3 FOREIGN KEY (faar_hjelp) REFERENCES brukere (mail)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE gruppe_oving ADD CONSTRAINT gruppe_oving_fk1 FOREIGN KEY (koe_id, gruppe_id) REFERENCES koe_gruppe (koe_id, gruppe_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE gruppe_oving ADD CONSTRAINT gruppe_oving_fk2 FOREIGN KEY (oving_id) REFERENCES oving (oving_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE gruppe ADD CONSTRAINT gruppe_fk1 FOREIGN KEY (koe_id, gruppe_id) REFERENCES koe_gruppe (koe_id, gruppe_id)
  ON DELETE CASCADE
  ON UPDATE CASCADE;
ALTER TABLE gruppe ADD CONSTRAINT gruppe_fk2 FOREIGN KEY (mail) REFERENCES brukere (mail)
  ON DELETE CASCADE
  ON UPDATE CASCADE;