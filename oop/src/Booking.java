import java.util.Date;

public class Booking {

    private final int bookingID;
    private final int customerID;
    private final int tripID;
    private final Date bookingDate;
    private int numberOfPeople;
    private double totalPrice;
    private String paymentStatus;  // "Paid" - "Pending" - "Cancelled"

    // Constructor with validation
    public Booking(int bookingID, int customerID, int tripID, Date bookingDate,
                   int numberOfPeople, String paymentStatus) {

        if (bookingDate == null) {
            throw new IllegalArgumentException("Booking date cannot be null.");
        }

        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be at least 1.");
        }

        if (paymentStatus == null || paymentStatus.isBlank()) {
            throw new IllegalArgumentException("Payment status cannot be empty.");
        }

        this.bookingID = bookingID;
        this.customerID = customerID;
        this.tripID = tripID;
        this.bookingDate = bookingDate;
        this.numberOfPeople = numberOfPeople;
        this.paymentStatus = paymentStatus;

        this.totalPrice = 0;  // يتم حسابه لاحقًا
    }

    // Getters
    public int getBookingID() { return bookingID; }
    public int getCustomerID() { return customerID; }
    public int getTripID() { return tripID; }
    public Date getBookingDate() { return bookingDate; }
    public int getNumberOfPeople() { return numberOfPeople; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentStatus() { return paymentStatus; }

    // Setters with validation
    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be positive.");
        }
        this.numberOfPeople = numberOfPeople;
    }

    public void setPaymentStatus(String paymentStatus) {
        if (paymentStatus == null || paymentStatus.isBlank()) {
            throw new IllegalArgumentException("Payment status cannot be empty.");
        }
        this.paymentStatus = paymentStatus;
    }

    // Price calculation
    public double calculateTotalPrice(double pricePerPerson) {
        if (pricePerPerson <= 0) {
            throw new IllegalArgumentException("Price per person must be positive.");
        }

        totalPrice = numberOfPeople * pricePerPerson;
        return totalPrice;
    }

    // Payment
    public boolean processPayment(double amount) {
        if (amount >= totalPrice) {
            paymentStatus = "Paid";
            return true;
        } else {
            paymentStatus = "Pending";
            return false;
        }
    }

    // Display
    public void displayBookingDetails() {
        System.out.println(this);
    }

    // toString
    @Override
    public String toString() {
        return "Booking{" +
                "bookingID=" + bookingID +
                ", customerID=" + customerID +
                ", tripID=" + tripID +
                ", bookingDate=" + bookingDate +
                ", numberOfPeople=" + numberOfPeople +
                ", totalPrice=" + totalPrice +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}