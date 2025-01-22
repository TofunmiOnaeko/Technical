-- create-tables.sql

-- Check if the database exists, if not, create it
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'TADatabase')
BEGIN
    CREATE DATABASE TADatabase;
END
GO

USE TADatabase;
GO

-- Create the Content table if it does not exist
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'Content' AND xtype = 'U')
BEGIN
    CREATE TABLE Content (
        uuid VARCHAR(36) PRIMARY KEY,
        user_id CHAR(6),
        full_name VARCHAR(50),
        likes VARCHAR(100),
        transport VARCHAR(100),
        avg_speed FLOAT,
        top_speed FLOAT
    );
END
GO

-- Create the Interaction table if it does not exist
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'Interaction' AND xtype = 'U')
BEGIN
    CREATE TABLE Interaction (
        request_id INT IDENTITY(1,1) PRIMARY KEY,
        request_uri VARCHAR(100),
        request_time_stamp DATETIME2,
        http_response_code VARCHAR(10),
        country_code VARCHAR(10),
        time_lapsed FLOAT
    );
END
GO
