\connect airline

CREATE TABLE PNRNum (
  PNR        CHAR(6)     PRIMARY KEY,
  Booking_ID BIGINT      NOT NULL,
  Claimed_At TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Flight (
  flight_id           BIGINT      PRIMARY KEY,
  flight_date         DATE        NOT NULL,
  flight_code         VARCHAR(7)  NOT NULL,
  depart              CHAR(3)     NOT NULL,
  depart_at           TIMESTAMPTZ NOT NULL,
  destination         CHAR(3)     NOT NULL,
  arrive_at           TIMESTAMPTZ NOT NULL,

  UNIQUE (flight_date, depart, flight_code),
  CONSTRAINT DEPART_BEFORE_ARRIVE CHECK (depart_at < arrive_at),
  CONSTRAINT NO_LOOPS CHECK (depart <> destination)
);

CREATE INDEX ON Flight (destination);


--  

GRANT USAGE ON SCHEMA public TO admin;
GRANT USAGE ON SCHEMA public TO clerk;

GRANT SELECT,INSERT ON ALL TABLES IN SCHEMA public TO admin;
GRANT SELECT,INSERT ON ALL TABLES IN SCHEMA public TO clerk;

COPY Flight FROM '/mnt/bootstrap/flights.csv' CSV;