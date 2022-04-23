package com.sqlsamples;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Locale;
import java.util.Scanner;

public class ManualTransactions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Manual transactions demo.");

        //Update the username and password below
        String connectionUrl = "jdbc:sqlserver://localhost\\MINI21:1434;databaseName=master;user=sa;password=minidb2021";

        try {
            // Load SQL Server JDBC driver and establish connection.
            System.out.print("Connecting to SQL Server ... ");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {
                System.out.println("Done.");

                SampleDbCreator dbCreator = new SampleDbCreator(connection);
                dbCreator.create();

                System.out.println("Auto commit: false");
                connection.setAutoCommit(false);

                // READ demo
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

                // INSERT demo
                System.out.print("Inserting a new row into table, press ENTER to continue...");
                System.in.read();
                sql = new StringBuilder().append("INSERT Employees (Name, Location) ").append("VALUES (?, ?);")
                        .toString();
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, "Jake");
                    statement.setString(2, "United States");
                    int rowsAffected = statement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) inserted");
                }


                // DELETE demo
                String userToDelete = "Jared";
                System.out.print("Deleting user '" + userToDelete + "', press ENTER to continue...");
                System.in.read();
                sql = "DELETE FROM Employees WHERE Name = ?;";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, userToDelete);
                    int rowsAffected = statement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) deleted");
                }

                System.out.println("Accept changes? [Y/N]");
                if(scanner.nextLine().toUpperCase(Locale.ROOT).equals("Y")){
                    System.out.println("Applying changes. Commit transaction.");
                    connection.commit();
                } else {
                    System.out.println("Changes rejected.");
                    connection.rollback();
                }

                // READ demo
                System.out.print("Reading data from table, press ENTER to continue...");
                System.in.read();
                sql = "SELECT Id, Name, Location FROM Employees;";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        System.out.println(
                                resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
                    }
                }


                System.out.println("All done.");
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }
    }
}