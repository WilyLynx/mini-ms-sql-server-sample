package com.sqlsamples;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public abstract class DemoBase {
    protected Scanner scanner;
    public DemoBase() {
        this.scanner = new Scanner(System.in);
    }

    protected abstract String getConnectionString();
    protected abstract void runDemoBody(Connection connection) throws SQLException, IOException;

    public void execute() {
        System.out.println(this.getClass().getSimpleName() + " demo");
        String connectionUrl = this.getConnectionString();

        try {
            // Load SQL Server JDBC driver and establish connection.
            System.out.print("Connecting to SQL Server ... ");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Done.");

                this.createDataBase(connection);
                this.createTable(connection);

                this.runDemoBody(connection);

                System.out.println("All done.");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }

    private void createTable(Connection connection) throws SQLException {
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

    private void createDataBase(Connection connection) throws SQLException {
        System.out.print("Dropping and creating database 'SampleDB' ... ");
        String sql = "DROP DATABASE IF EXISTS [SampleDB]; CREATE DATABASE [SampleDB]";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Done.");
        }
    }

    protected void insertDemo(Connection connection) throws SQLException, IOException {
        System.out.print("Inserting a new row into table, press ENTER to continue...");
        System.in.read();
        String sql = new StringBuilder().append("INSERT Employees (Name, Location) ").append("VALUES (?, ?);")
                .toString();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "Jake");
            statement.setString(2, "United States");
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted");
        }
    }
    protected void updateDemo(Connection connection) throws SQLException, IOException {
        String userToUpdate = "Nikita";
        System.out.print("Updating 'Location' for user '" + userToUpdate + "', press ENTER to continue...");
        System.in.read();
        String sql = "UPDATE Employees SET Location = N'United States' WHERE Name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userToUpdate);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated");
        }
    }
    protected void deleteDemo(Connection connection) throws SQLException, IOException {
        String userToDelete = "Jared";
        System.out.print("Deleting user '" + userToDelete + "', press ENTER to continue...");
        System.in.read();
        String sql = "DELETE FROM Employees WHERE Name = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userToDelete);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) deleted");
        }
    }
    protected void readDemo(Connection connection) throws SQLException, IOException {
        System.out.print("Reading data from table, press ENTER to continue...");
        System.in.read();
        String sql = "SELECT Id, Name, Location FROM Employees;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
            }
        }
    }

}
