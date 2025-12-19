CREATE DATABASE Tourism;
GO

USE Tourism;
GO

CREATE TABLE Customer (
    CusID               INT             PRIMARY KEY IDENTITY(1,1),
    FullName        VARCHAR(100)        NOT NULL,
    Email           VARCHAR(100)        NOT NULL,
    Password        VARCHAR(50)         NOT NULL,
    City            VARCHAR(50)         NOT NULL,
    State           VARCHAR(50)         NOT NULL,
    Phone            VARCHAR(20)        NOT NULL,
    Nationality     VARCHAR(50)         NOT NULL
);
GO

CREATE TABLE Employee (
    EmployeeID      INT                PRIMARY KEY IDENTITY(1,1),
    FullName        VARCHAR(100)            NOT NULL,
    Email           VARCHAR(100)            NOT NULL,
    Password        VARCHAR(50)             NOT NULL,
    Position        VARCHAR(50)             NULL,
    Salary          DECIMAL(18,2)           NULL,
    Phone           VARCHAR(20)             NOT NULL,
    HireDate        DATE                    NULL
);
GO

CREATE TABLE Destination (
    DesID          INT                 PRIMARY KEY IDENTITY(1,1),
    DesName        VARCHAR(100)           NOT NULL,
    City           VARCHAR(50)            NOT NULL,
    Country        VARCHAR(50)            NOT NULL,        
    Cost           DECIMAL(18,2)          NULL,
    Description    VARCHAR(MAX)
);
GO

CREATE TABLE Transport (
    TransID           INT                  PRIMARY KEY IDENTITY(1,1),
    TransType         VARCHAR(50)                NOT NULL,
    SupplierName      VARCHAR(100)  ,
    PricePerSeat      DECIMAL(18,2) ,
    AvailableSeat       INT  ,
    ArrTime             TIME ,
    DepartTime          TIME
);
GO

CREATE TABLE Trip (
    TripID             INT               PRIMARY KEY IDENTITY(1,1),
    TripName           VARCHAR(100)          NOT NULL,
    PricePerPerson     DECIMAL(18,2)         NOT NULL,
    StartDate          DATE                  NOT NULL,
    EndDate            DATE                  NOT NULL,
    TotalSeats         INT  ,
    AvailSeats         INT  ,
    EmployeeID         INT                   NOT NULL,
    TransID            INT                   NOT NULL,
    DesID              INT                   NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
    FOREIGN KEY (TransID) REFERENCES Transport(TransID),
    FOREIGN KEY (DesID) REFERENCES Destination(DesID)
);
GO

CREATE TABLE Booking (
    BookingID            INT                       PRIMARY KEY IDENTITY(1,1),
    Price                DECIMAL(18,2)                   NOT NULL,
    PaymentMethod        VARCHAR(50)                     NOT NULL,
    BookDate             DATE                            NOT NULL,
    NumOfPeople          INT                             NOT NULL,
    CusID                INT                             NOT NULL,
    PaymentStatus        VARCHAR(50)                     NULL,
    TripID               INT                             NOT NULL,
    FOREIGN KEY (CusID) REFERENCES Customer(CusID),
    FOREIGN KEY (TripID) REFERENCES Trip(TripID)
);
GO


select *from customer;
UPDATE customer
set Email = 'ahmed.ali@gmail.com'
WHERE CusID = 2;

UPDATE customer
set Nationality = 'Egyption'
WHERE CusID = 1;

/* ===============================
   USE DATABASE
================================ */
USE Tourism;
GO

/* ===============================
   INSERT EMPLOYEE
================================ */
INSERT INTO Employee
(FullName, Email, Password, Position, Salary, Phone, HireDate)
VALUES
('Sara Ahmed Hassan', 'sara.hassan@tourism.com', 'admin123', 'Manager', 10000, '01000000000', '2024-01-01');
GO

/* ===============================
   INSERT TRANSPORT
================================ */
INSERT INTO Transport
(TransType, SupplierName, PricePerSeat, AvailableSeat, ArrTime, DepartTime)
VALUES
('Plane', 'Egypt Air', 300, 200, '18:00', '09:00');
GO

/* ===============================
   INSERT DESTINATIONS
================================ */
INSERT INTO Destination
(DesName, City, Country, Cost, Description)
VALUES
('Paris Trip', 'Paris', 'France', 500, 'Visit Eiffel Tower and city tour'),
('Rome Trip', 'Rome', 'Italy', 400, 'Historical places and museums'),
('Moscow Trip', 'Moscow', 'Russia', 700, 'Red Square and Kremlin'),
('Madrid Trip', 'Madrid', 'Spain', 450, 'City tour and attractions'),
('Berlin Trip', 'Berlin', 'Germany', 350, 'Modern city and history');
GO

/* ===============================
   INSERT TRIPS
================================ */
INSERT INTO Trip
(TripName, PricePerPerson, StartDate, EndDate, TotalSeats, AvailSeats, EmployeeID, TransID, DesID)
VALUES
('Paris Trip',   150,  '2025-02-01', '2025-02-06', 50, 50, 1, 1, 1),
('Rome Trip',    200,  '2025-03-01', '2025-03-08', 60, 60, 1, 1, 2),
('Moscow Trip', 1000,  '2025-04-01', '2025-04-05', 40, 40, 1, 1, 3),
('Madrid Trip',  450,  '2025-05-01', '2025-05-08', 55, 55, 1, 1, 4),
('Berlin Trip',  350,  '2025-06-01', '2025-06-05', 45, 45, 1, 1, 5);
GO

/* ===============================
   CHECK DATA
================================ */
SELECT 
    T.TripID,
    T.TripName,
    D.City,
    D.Country,
    T.PricePerPerson,
    T.StartDate,
    T.EndDate
FROM Trip T
JOIN Destination D ON T.DesID = D.DesID;
GO
select * from Booking;


USE Tourism;
GO

/* ===============================
   DELETE BOOKINGS FIRST
================================ */
DELETE FROM Booking
WHERE CusID IN (1, 2);
GO

/* ===============================
   DELETE CUSTOMERS
================================ */
DELETE FROM Customer
WHERE CusID IN (1, 2);
GO

/* ===============================
   RESET IDENTITY (BOOKING)
================================ */
DBCC CHECKIDENT ('Booking', RESEED, 0);
GO

/* ===============================
   RESET IDENTITY (CUSTOMER)
================================ */
DBCC CHECKIDENT ('Customer', RESEED, 0);
GO

/* ===============================
   CHECK RESULT
================================ */
SELECT * FROM Booking;
SELECT * FROM Customer;
GO

select *from Trip;
