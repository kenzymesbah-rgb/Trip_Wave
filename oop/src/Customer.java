import java.util.ArrayList;

public class Customer extends Person implements Manageable {

    private final int customerID;
    private String nationality;
    private String passportNumber;
    private ArrayList<Booking> bookings = new ArrayList<>();

    public Customer(String fullName, String email,String password_hash, String phoneNumber,int id, String nationality, String passportNumber) {
        super(fullName,email,password_hash,phoneNumber);
        this.customerID = id;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Override
    public void showInfo() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("fullName: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Nationality: " + nationality);
        System.out.println("Passport: " + passportNumber);
    }

    @Override
    public void add() {
        System.out.println("Use Manager class to add customers.");
    }

    @Override
    public void update() {
        System.out.println("Use Manager class to update customers.");
    }

    @Override
    public void delete() {
        System.out.println("Use Manager class to delete customers.");
    }
    @Override
    public void display() {
        this.showInfo();
    }


    public void addBooking(Booking b) {
        bookings.add(b);
        System.out.println("Booking added for customer " + getFullName());
    }
}