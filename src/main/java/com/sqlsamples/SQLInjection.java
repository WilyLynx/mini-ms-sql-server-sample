package com.sqlsamples;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLInjection extends DemoBase {
    public static void main(String[] args) {
        SQLInjection demo = new SQLInjection();
        demo.execute();
    }

    @Override
    protected String getConnectionString() {
        return "jdbc:sqlserver://localhost\\MINI21:1434;databaseName=master;user=sa;password=minidb2021";
    }

    @Override
    protected void runDemoBody(Connection connection) throws SQLException {
        boolean northwindExists = this.checkNorthwindExists(connection);
        if(! northwindExists)
            System.out.println("Create Db Northwind first!");
        else
            while (northwindExists) {
                String sql = this.prepareQuery();
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                    int size = this.printResults(resultSet);
                    this.printNextHint(size);
                }
                northwindExists = this.checkNorthwindExists(connection);
            }
    }

    private String prepareQuery() {
        System.out.println("Reading data from table");
        System.out.print("Find employee by name e.g. Jared: ");
        String name = scanner.nextLine();
        String sql = "SELECT Id, Name, Location FROM Employees WHERE Name = \'" + name + "\'";
        System.out.println("I am printing SQL executed query for you: ");
        System.out.println(sql);
        return sql;
    }
    private int printResults(ResultSet resultSet) throws SQLException {
        int size = 0;
        while (resultSet.next()) {
            size += 1;
            System.out.println(
                    resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
        }
        return size;
    }

    private void printNextHint(int size) {
        switch (size) {
            case 0:
                System.out.println("Try: Jared");
                break;
            case 1:
                System.out.println("Great you are using this app very kindly.");
                System.out.println("How about some wired names like \"Jared\' OR \'1\'=\'1\" ?");
                break;
            case 3:
                System.out.println("That was unexpected O_O ");
                System.out.println("Let's find out something about other DBs:");
                System.out.println("\"\' UNION ALL SELECT 1 as Id, name, '' as location FROM sys.databases WHERE name != \'\"");
                break;
            default:
                System.out.println("Finally, unleash final boss:");
                System.out.println("\'; DROP DATABASE Northwind; SELECT \'");

        }
    }
    private boolean checkNorthwindExists(Connection connection) throws SQLException {
        String sql = "SELECT name from sys.databases WHERE name = 'Northwind'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while(resultSet.next()) {
                if(resultSet.getString(1).equals("Northwind")){
                    System.out.println("\nHere we go again!");
                    return true;
                }
            }
        }
        System.out.println("\nBye, bye Northwind !!!");
        return false;
    }
}