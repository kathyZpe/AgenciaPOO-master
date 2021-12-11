CREATE TABLE IF NOT EXISTS services(
    id INTEGER AUTO_INCREMENT PRIMARY KEY UNIQUE,
    service_type INTEGER NOT NULL,
    delivered BOOLEAN NOT NULL,
    arrival DATE NOT NULL,
    quit DATE NOT NULL,
    user_name NVARCHAR(12) NOT NULL,
    surname NVARCHAR(12) NOT NULL,
    model NVARCHAR(20) NOT NULL,
    registration NVARCHAR(20) NOT NULL,
    phone NVARCHAR(30) NOT NULL,
    email NVARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS agencies(
    id INTEGER AUTO_INCREMENT PRIMARY KEY UNIQUE,
    agency_name NVARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS parts (
    id INTEGER AUTO_INCREMENT PRIMARY KEY UNIQUE,
    part_name NVARCHAR(30) NOT NULL,
    unities INTEGER(10) NOT NULL,
    price DOUBLE(6,2) NOT NULL,
    agency_id INTEGER NOT NULL,
    FOREIGN KEY (agency_id) REFERENCES agencies(id)
);
