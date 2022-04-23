package com.sqlsamples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SampleDbCreator {

    private Connection connection;

    public SampleDbCreator(Connection dbConnection) {
        this.connection = dbConnection;
    }

    public void create() throws SQLException, IOException {
        CreateDataBase();
        CreateTable();
    }

    private void CreateTable() throws IOException, SQLException {
        System.out.print("Creating sample table with data ... ");
        String sql = new StringBuilder()
                .append("USE SampleDB; ")
                .append("CREATE TABLE Employees ( ")
                .append(" Id INT IDENTITY(1,1) NOT NULL PRIMARY KEY, ")
                .append(" Name NVARCHAR(50), ")
                .append(" Location NVARCHAR(50) ")
                .append("); ")
                .append("INSERT INTO Employees (Name, Location) VALUES ")
                .append("(N'Jared', N'Australia'), ")
                .append("(N'Nikita', N'India'), ")
                .append("(N'Tom', N'Germany'); ")
                .toString();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Done.");
        }
    }

    private void CreateDataBase() throws SQLException {
        System.out.print("Dropping and creating database 'SampleDB' ... ");
        String sql = "DROP DATABASE IF EXISTS [SampleDB]; CREATE DATABASE [SampleDB]";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Done.");
        }
    }
}
