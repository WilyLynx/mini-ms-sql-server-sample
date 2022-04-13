package com.sqlsamples;

import java.io.Console;
import java.sql.*;
import java.util.Scanner;

public class SQL_injection {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Connect to SQL Server and demo Create, Read, Update and Delete operations.");

        //Update the username and password below
		String connectionUrl = "jdbc:sqlserver://localhost\\MINI21:1434;databaseName=master;user=sa;password=minidb2021";

		try {
			// Load SQL Server JDBC driver and establish connection.
			System.out.print("Connecting to SQL Server ... ");
			try (Connection connection = DriverManager.getConnection(connectionUrl)) {
				System.out.println("Done.");

				// Create a sample database
				System.out.print("Dropping and creating database 'SampleDB' ... ");
				String sql = "DROP DATABASE IF EXISTS [SampleDB]; CREATE DATABASE [SampleDB]";
				try (Statement statement = connection.createStatement()) {
					statement.executeUpdate(sql);
					System.out.println("Done.");
				}

				// Create a Table and insert some sample data
				System.out.print("Creating sample table with data.");
				sql = new StringBuilder()
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

				boolean northwindExists = true;
				while(northwindExists) {
					System.out.println("Reading data from table");
					System.out.print("Find employee by name e.g. Jared: ");
					String name = scanner.nextLine();
					sql = "SELECT Id, Name, Location FROM Employees WHERE Name = \'" + name + "\'";
					System.out.println("I am printing SQL executed query for you: ");
					System.out.println(sql);
					try (Statement statement = connection.createStatement();
						 ResultSet resultSet = statement.executeQuery(sql)) {
						int size = 0;
						while (resultSet.next()) {
							size += 1;
							System.out.println(
									resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
						}
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
					if(name.contains("DROP DATABASE Northwind")) {
						northwindExists = false;
						System.out.println("\nBye, bye Northwind !!!");
					} else
						System.out.println("\nHere we go again!");
				}
			}
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
	}
}