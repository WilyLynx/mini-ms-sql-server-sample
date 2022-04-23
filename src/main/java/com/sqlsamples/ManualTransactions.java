package com.sqlsamples;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class ManualTransactions extends DemoBase {
    public static void main(String[] args) {
        ManualTransactions demo = new ManualTransactions();
        demo.execute();
    }

    @Override
    protected String getConnectionString() {
        return "jdbc:sqlserver://localhost\\MINI21:1434;databaseName=master;user=sa;password=minidb2021";
    }

    @Override
    protected void runDemoBody(Connection connection) throws SQLException, IOException {
        System.out.println("Auto commit: false");
        connection.setAutoCommit(false);

        this.readDemo(connection);
        this.insertDemo(connection);
        this.deleteDemo(connection);
        this.readDemo(connection);
        this.endTransaction(connection);
        this.readDemo(connection);
    }
    private void endTransaction(Connection connection) throws SQLException {
        System.out.println("Accept changes? [Y/N]");
        if(scanner.nextLine().toUpperCase(Locale.ROOT).equals("Y")){
            System.out.println("Applying changes. Commit transaction.");
            connection.commit();
        } else {
            System.out.println("Changes rejected.");
            connection.rollback();
        }
    }
}