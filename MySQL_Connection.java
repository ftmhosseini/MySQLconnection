package library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MySQL_Connection {
	public static void registeration(Statement statement) throws SQLException {
		Scanner obj = new Scanner(System.in);
		System.out.println("Please enter student id:");
		int id = obj.nextInt();
		if (search(statement, id)) {
			System.out.println("This student id is existed!!");
		} else {
			String s = "INSERT INTO student (std_id, name, age, grade) VALUES (" + id;
			System.out.println("Please enter name:");
			String name = obj.next();
			s = s + ",'" + name + "'";
			System.out.println("Please enter age:");
			id = obj.nextInt();
			s = s + "," + id;
			System.out.println("Please enter grade (0: Diploma, 1: Bachelor, 2: Master, 3: PHD)");
			id = obj.nextInt();
			while (id > 3 || id < 0) {
				System.out.println("please enter the correct grade number");
				id = obj.nextInt();
			}
			switch (id) {
			case 0:
				s = s + ",'Diploma' )";
				break;
			case 1:
				s = s + ",'Bachelor' )";
				break;
			case 2:
				s = s + ",'Master' )";
				break;
			case 3:
				s = s + ",'PHD' )";
				break;
			}
			System.out.println(s);
			statement.executeUpdate(s);
			System.out.println("Insert was successfully");
//			obj.close();
		}
	}

	public static String edit() {
		Scanner obj = new Scanner(System.in);
		String s = "";
		System.out.println("Please enter name:");
		String name = obj.next();
		s = "name = '" + name + "'";
		System.out.println("Please enter age:");
		int id = obj.nextInt();
		s = s + ", age =" + id;
		System.out.println("Please enter grade (0: Diploma, 1: Bachelor, 2: Master, 3: PHD)");
		id = obj.nextInt();
		while (id > 3 || id < 0) {
			System.out.println("please enter the correct grade number");
			id = obj.nextInt();
		}
		switch (id) {
		case 0:
			s = s + ", grade = 'Diploma' ";
			break;
		case 1:
			s = s + ", grade = 'Bachelor' ";
			break;
		case 2:
			s = s + ", grade = 'Master' ";
			break;
		case 3:
			s = s + ", grade = 'PHD' ";
			break;
		}
		obj.close();
		System.out.println(s);
		return s;
	}

	public static void print_query(Statement statement, String s) throws SQLException {
		ResultSet result = statement.executeQuery(s);
		while (result.next()) {
			System.out.format("id = %s,\t student id = %s,\t name = %s, \t age = %s,\t grade = %s\n",
					result.getString("id"), result.getString("std_id"), result.getString("name"),
					result.getString("age"), result.getString("grade"));
			/*
			 * System.out.println(result.getString("id"));
			 * System.out.println(result.getString("std_id"));
			 * System.out.println(result.getString("name"));
			 * System.out.println(result.getString("age"));
			 * System.out.println(result.getString("grade"));
			 */}
	}

	public static boolean search(Statement statement, int id) throws SQLException {

		String s = "SELECT * FROM student WHERE std_id =" + id;
		System.out.println(s);
		ResultSet result = statement.executeQuery(s);
		while (result.next()) {
			return true;
		}
		System.out.println("the student info not found");

		return false;
	}

	public static void main(String[] args) throws SQLException {
		// Database connection parameters
		DatabaseConnection dbConnection = DatabaseConnection.getInstance();

		// Get the database connection
		Connection connection = dbConnection.getConnection();
		Scanner obj = new Scanner(System.in);
		Scanner scanner = new Scanner(System.in);
		int id;

		Statement statement = connection.createStatement();

		outer: while (true) {
			System.out.println("please select your operation:\n" + "1 : Register a student data\n"
					+ "2 : Search a student with student id\n" + "3 : Edit a student information\n"
					+ "4 : View all student information\n" + "5 : Remove a student info\n" + "0 : Exit\n");

			switch (scanner.nextInt()) {
			case 1:
				registeration(statement);
				break;
			case 2:

				System.out.println("please enter a student id that you search about");
				id = obj.nextInt();
				if (search(statement, id)) {

					System.out.println("the student data is");
					String s = "SELECT * FROM student WHERE std_id =" + id;
					print_query(statement, s);
				}
				break;
			case 3:
				System.out.println("please enter a student id that you search about");
				id = obj.nextInt();
				if (search(statement, id)) {
					String str = edit();
					String s = "UPDATE student SET " + str + " WHERE std_id = " + id;
					statement.executeUpdate(s);
					System.out.println("UPDATE was successfully");
				}
				break;
			case 4:
				String s = "SELECT * FROM student";
				print_query(statement, s);
				break;
			case 5:
				System.out.println("please enter a student id that you search about");
				id = obj.nextInt();
				if (search(statement, id)) {
					statement.executeUpdate("DELETE FROM student WHERE std_id = " + id);

					System.out.println("delete was successfully");
				}
				break;
			case 0:
				obj.close();
				scanner.close();
				connection.close();
				break outer;
			default:
				System.out.println("Please enter number in range of 0-5");
			}
		}
	}
}
