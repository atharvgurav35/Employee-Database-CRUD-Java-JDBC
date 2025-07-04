package Elevatelab;

import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/Employee";
    static final String DB_USER = "postgres";
    static final String DB_PASSWORD = "manasi";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {

        	Class.forName("org.postgresql.Driver");

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                while (true) {
                    System.out.println("\n--- Employee Database ---");
                    System.out.println("1. Add Employee");
                    System.out.println("2. View Employees");
                    System.out.println("3. Update Employee");
                    System.out.println("4. Delete Employee");
                    System.out.println("5. Exit");
                    System.out.print("Choose: ");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            addEmployee(conn, sc);
                            break;
                        case 2:
                            viewEmployees(conn);
                            break;
                        case 3:
                            updateEmployee(conn, sc);
                            break;
                        case 4:
                            deleteEmployee(conn, sc);
                            break;
                        case 5:
                            System.out.println("Goodbye!");
                            return;
                        default:
                            System.out.println("Invalid choice.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addEmployee(Connection conn, Scanner sc) throws SQLException
    {
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        String query = "INSERT INTO Employee(name, email, salary) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setDouble(3, salary);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " Employee added.");
        }
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String query = "SELECT * FROM Employee";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("ID | Name | Email | Salary");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("salary"));
            }
        }
    }

    static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter New Name: ");
        String name = sc.nextLine();
        System.out.print("Enter New Email: ");
        String email = sc.nextLine();
        System.out.print("Enter New Salary: ");
        double salary = sc.nextDouble();

        String query = "UPDATE Employee SET name = ?, email = ?, salary = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setDouble(3, salary);
            stmt.setInt(4, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " Employee updated.");
       
        }
     }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException
    {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM Employee WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows + " Employee deleted.");
        }
    }
}
