package com.sqlsamples.SimpleDemos;

public class RemoteDb extends SimpleDemoBase {

	public static void main(String[] args) {
		RemoteDb demo = new RemoteDb();
		demo.execute();
	}

	@Override
	protected String getConnectionString() {
		return "jdbc:sqlserver://192.168.137.180\\WIN-7KGAL3F2ICE:1433;databaseName=master;user=sa;password=SAstudent1";
	}
}