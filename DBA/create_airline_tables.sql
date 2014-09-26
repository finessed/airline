\connect airline

CREATE TABLE PNRNum (
  PNR        CHAR(6)     PRIMARY KEY,
  Booking_ID BIGINT      NOT NULL,
  Claimed_At TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE Flight (
  Flight_ID           BIGINT      PRIMARY KEY,
  Flight_Code         VARCHAR(7) NOT NULL,
  Departure_Location  CHAR(3) NOT NULL,
  Scheduled_Departure TIMESTAMPTZ NOT NULL,
  Arrival_Location    CHAR(3) NOT NULL,
  Scheduled_Arrival   TIMESTAMPTZ NOT NULL,
  Created_At          TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (Flight_code, Departure_Location, Scheduled_Departure),
  CONSTRAINT DEPART_BEFORE_ARRIVE CHECK (Scheduled_Departure < Scheduled_Arrival )
);

GRANT USAGE ON SCHEMA public TO admin;
GRANT USAGE ON SCHEMA public TO clerk;

GRANT SELECT,INSERT ON ALL TABLES IN SCHEMA public TO admin;
GRANT SELECT,INSERT ON ALL TABLES IN SCHEMA public TO clerk;
