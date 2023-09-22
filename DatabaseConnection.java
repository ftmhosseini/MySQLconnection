package library;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	// JDBC URL, username, and password of your MySQL server
		private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library";
		private static final String USERNAME = "root";
		private static final String PASSWORD = "";

		// Singleton instance
		private static DatabaseConnection instance;

		// Database connection
		private Connection connection;

		// Private constructor to prevent instantiation
		private DatabaseConnection() {
			try {
			    // Load the MySQL JDBC driver class
			    Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
			    System.err.println("MySQL JDBC driver not found.");
			    e.printStackTrace();
			    return; // Exit or handle the error as appropriate.
			}
			try {
				// Create a database connection
				connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
				System.out.println("Database connected successfully!");
				TableCreation();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to connect to the database.");
			}
		}

		private boolean tableExists(String tableName) throws SQLException {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });
			return resultSet.next();
		}

		private void TableCreation() {
			try {
				// Create a database connection
				Statement statement = connection.createStatement();

				// Define the SQL CREATE TABLE statement
				if (!tableExists("student")) {
					String createTableSQL = "CREATE TABLE student " + "(std_id INTEGER NOT NULL , "
							+ "id INTEGER AUTO_INCREMENT NOT NULL ," + "age INTEGER , " + "name VARCHAR(250) NOT NULL , "
							+ "grade VARCHAR(250) NOT NULL, " + "PRIMARY KEY (id) ," + "UNIQUE (std_id))"
							+ "ENGINE = InnoDB; ";
					// Execute the CREATE TABLE statement
					statement.executeUpdate(createTableSQL);

					System.out.println("Table created successfully!");
				} else
					System.out.println("This table is existed!!");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to connect to the database.");
			}
		}

		// Method to get the singleton instance
		public static synchronized DatabaseConnection getInstance() {
			if (instance == null) {
				instance = new DatabaseConnection();
			}
			return instance;
		}

		// Method to get the database connection
		public Connection getConnection() {
			return connection;
		}
	}
