DROP TABLE IF EXISTS FLIGHT;
create table FLIGHT(
  ID int not null AUTO_INCREMENT,
  FLIGHT_NUMBER varchar(10) NOT NULL,
  ORIGIN varchar(10) NOT NULL,
  DESTINATION varchar(10) NOT NULL,
  DEPARTURE_TIME time,
  ARRIVAL_TIME time,
  FARE decimal(10,2) NOT NULL,
  PRIMARY KEY ( ID )
);