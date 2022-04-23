package com.sqlsamples.SimpleDemos;

public class LocalhostDb extends SimpleDemoBase {

	public static void main(String[] args) {
		LocalhostDb demo = new LocalhostDb();
		demo.execute();
	}

	@Override
	protected String getConnectionString() {
		return "jdbc:sqlserver://localhost\\MINI21:1434;databaseName=master;user=sa;password=minidb2021";
	}
}