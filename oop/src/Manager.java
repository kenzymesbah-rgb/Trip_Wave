import java.util.ArrayList;

public class Manager {
        private ArrayList<Customer> customers = new ArrayList<>();
        private ArrayList<Employee> employees = new ArrayList<>();

        // إدارة أي Manageable من نوع Customer
        public void addCustomer(Customer c) {
            customers.add(c);
            System.out.println("Customer added: " + c.getFullName());
        }

        public void removeCustomer(Customer c) {
            customers.remove(c);
            System.out.println("Customer removed: " + c.getFullName());
        }

        public void displayAllCustomers() {
            for (Customer c : customers) c.display(); // display من Manageable
        }

        // نفس الكلام للموظفين
        public void addEmployee(Employee e) {
            employees.add(e);
            System.out.println("Employee added: " + e.getFullName());
        }

        public void removeEmployee(Employee e) {
            employees.remove(e);
            System.out.println("Employee removed: " + e.getFullName());
        }

        public void displayAllEmployees() {
            for (Employee e : employees) e.display(); // display من Manageable
        }
    }

