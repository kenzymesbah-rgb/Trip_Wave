CREATE DATABASE Tourism;
USE Tourism;

CREATE TABLE Customer (
    CusID INT PRIMARY KEY,
    FName VARCHAR(50),
    MName VARCHAR(50),
    LName VARCHAR(50),
    Email VARCHAR(100),
    City VARCHAR(50),
    State VARCHAR(50),
    Street VARCHAR(100),
    Nationality VARCHAR(50)
);

CREATE TABLE CustomerPhone (
    Phone VARCHAR(20),
    CusID INT,
    PRIMARY KEY (Phone),
    FOREIGN KEY (CusID) REFERENCES Customer(CusID)
        ON DELETE CASCADE
);

CREATE TABLE Employee (
    EmployeeID INT PRIMARY KEY,
    Email VARCHAR(100),
    Position VARCHAR(50),
    Salary DECIMAL(10,2),
    HireDate DATE,
    FName VARCHAR(50),
    MName VARCHAR(50),
    LName VARCHAR(50)
);


CREATE TABLE EmployeePhone (
    Phone VARCHAR(20),
    EmployeeID INT,
    PRIMARY KEY (Phone),
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
        ON DELETE CASCADE
);



CREATE TABLE Destination (
    DesID INT PRIMARY KEY,
    DesName VARCHAR(100),
    City VARCHAR(50),
    Country VARCHAR(50),
    Cost DECIMAL(10,2),
    Description TEXT
);


CREATE TABLE Transport (
    TransID INT PRIMARY KEY,
    TransType VARCHAR(50),
    SupplierName VARCHAR(100),
    PricePerSeat DECIMAL(10,2),
    AvailableSeat INT,
    ArrTime TIME,
    DepartTime TIME
);



CREATE TABLE Trip (
    TripID INT PRIMARY KEY,
    TripName VARCHAR(100),
    PricePerPerson DECIMAL(10,2),
    StartDate DATE,
    EndDate DATE,
    TotalSeats INT,
    AvailSeats INT,
    EmployeeID INT,
    TransID INT,
    DesID INT,

    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID),
    FOREIGN KEY (TransID) REFERENCES Transport(TransID),
    FOREIGN KEY (DesID) REFERENCES Destination(DesID)
);



CREATE TABLE Booking (
    BookingID INT PRIMARY KEY,
    PaymentMethod VARCHAR(50),
    BookDate DATE,
    NumOfPeople INT,
    CusID INT NOT NULL,
    PaymentStatus VARCHAR(50),
    TripID INT NOT NULL,

    FOREIGN KEY (CusID) REFERENCES Customer(CusID),
    FOREIGN KEY (TripID) REFERENCES Trip(TripID)
);



