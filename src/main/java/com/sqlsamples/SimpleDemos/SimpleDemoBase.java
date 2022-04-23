package com.sqlsamples.SimpleDemos;

import com.sqlsamples.DemoBase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class SimpleDemoBase extends DemoBase {

    @Override
    protected void runDemoBody(Connection connection) throws SQLException, IOException {
        this.readDemo(connection);
        this.insertDemo(connection);
        this.updateDemo(connection);
        this.deleteDemo(connection);
        this.readDemo(connection);
    }
}
