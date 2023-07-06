import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    private String name;
    private int id;
    private String designation;
    private double salary;

    // Constructor
    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Override toString() method for printing employee details
    @Override
    public String toString() {
        return "Employee [name=" + name + ", id=" + id + ", designation=" + designation + ", salary=" + salary + "]";
    }
}

class FileManager {
    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public List<Employee> readEmployeesFromFile() {
        List<Employee> employees = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] employeeData = line.split(",");
                String name = employeeData[0];
                int id = Integer.parseInt(employeeData[1]);
                String designation = employeeData[2];
                double salary = Double.parseDouble(employeeData[3]);

                Employee employee = new Employee(name, id, designation, salary);
                employees.add(employee);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }

        return employees;
    }

    public void writeEmployeesToFile(List<Employee> employees) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Employee employee : employees) {
                writer.println(
                        employee.getName() + "," + employee.getId() + "," + employee.getDesignation() + "," + employee.getSalary());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
        }
    }
}

public class EmployeeManagementSystem {
    private List<Employee> employees;
    private FileManager fileManager;
    private Scanner scanner;

    public EmployeeManagementSystem() {
        this.fileManager = new FileManager("employees.txt");
        this.employees = fileManager.readEmployeesFromFile();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Employee Management System");
        System.out.println("---------------------------");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Search Employee");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;

                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    searchEmployee();
                    break;
                case 6:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addEmployee() {
        System.out.println("\nAdd Employee");
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter employee designation: ");
        String designation = scanner.nextLine();
        System.out.print("Enter employee salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Employee newEmployee = new Employee(name, id, designation, salary);
        employees.add(newEmployee);
        fileManager.writeEmployeesToFile(employees);

        System.out.println("Employee added successfully.");
    }

    private void viewEmployees() {
        System.out.println("\nView Employees");

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private void updateEmployee() {
        System.out.println("\nUpdate Employee");
        System.out.print("Enter the ID of the employee to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                System.out.print("Enter new employee name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new employee designation: ");
                String designation = scanner.nextLine();
                System.out.print("Enter new employee salary: ");
                double salary = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character

                employee.setName(name);
                employee.setDesignation(designation);
                employee.setSalary(salary);
                fileManager.writeEmployeesToFile(employees);

                System.out.println("Employee updated successfully.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee not found with ID: " + id);
        }
    }

    private void deleteEmployee() {
        System.out.println("\nDelete Employee");
        System.out.print("Enter the ID of the employee to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Employee employeeToRemove = null;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employeeToRemove = employee;
                break;
            }
        }

        if (employeeToRemove != null) {
            employees.remove(employeeToRemove);
            fileManager.writeEmployeesToFile(employees);
            System.out.println("Employee deleted successfully.");
        } else {
            System.out.println("Employee not found with ID: " + id);
        }
    }

    private void searchEmployee() {
        System.out.println("\nSearch Employee");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 1) {
            System.out.print("Enter the ID of the employee to search: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            boolean found = false;
            for (Employee employee : employees) {
                if (employee.getId() == id) {
                  
                    System.out.println("Employee found:");
                    System.out.println(employee);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee not found with ID: " + id);
            }
        } else if (choice == 2) {
            System.out.print("Enter the name of the employee to search: ");
            String name = scanner.nextLine();

            boolean found = false;
            for (Employee employee : employees) {
                if (employee.getName().equalsIgnoreCase(name)) {
                    System.out.println("Employee found:");
                    System.out.println(employee);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("Employee not found with name: " + name);
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    private void exit() {
        scanner.close();
        System.out.println("Exiting the Employee Management System. Goodbye!");
        System.exit(0);
    }

    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        system.start();
    }
}
