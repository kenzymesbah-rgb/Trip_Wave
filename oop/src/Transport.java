public class Transport {

    private int transportID;
    private String transportType;
    private String companyName;
    private int seatsAvailable;
    private double pricePerSeat;
    private String departureTime;
    private String arrivalTime;

    // Constructor with validation
    public Transport(int transportID, String transportType, String companyName,
                     int seatsAvailable, double pricePerSeat,
                     String departureTime, String arrivalTime) {

        if (transportType == null || transportType.isBlank()) {
            throw new IllegalArgumentException("Transport type cannot be empty.");
        }

        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be empty.");
        }

        if (seatsAvailable < 0) {
            throw new IllegalArgumentException("Seats available cannot be negative.");
        }

        if (pricePerSeat <= 0) {
            throw new IllegalArgumentException("Price per seat must be positive.");
        }

        if (departureTime == null || departureTime.isBlank()) {
            throw new IllegalArgumentException("Departure time cannot be empty.");
        }

        if (arrivalTime == null || arrivalTime.isBlank()) {
            throw new IllegalArgumentException("Arrival time cannot be empty.");
        }

        this.transportID = transportID;
        this.transportType = transportType;
        this.companyName = companyName;
        this.seatsAvailable = seatsAvailable;
        this.pricePerSeat = pricePerSeat;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    // Getters
    public int getTransportID() {
        return transportID;
    }

    public String getTransportType() {
        return transportType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    // Setters with validation
    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }

    public void setTransportType(String transportType) {
        if (transportType == null || transportType.isBlank()) {
            throw new IllegalArgumentException("Transport type cannot be empty.");
        }
        this.transportType = transportType;
    }

    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.isBlank()) {
            throw new IllegalArgumentException("Company name cannot be empty.");
        }
        this.companyName = companyName;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        if (seatsAvailable < 0) {
            throw new IllegalArgumentException("Seats available cannot be negative.");
        }
        this.seatsAvailable = seatsAvailable;
    }

    public void setPricePerSeat(double pricePerSeat) {
        if (pricePerSeat <= 0) {
            throw new IllegalArgumentException("Price per seat must be positive.");
        }
        this.pricePerSeat = pricePerSeat;
    }

    public void setDepartureTime(String departureTime) {
        if (departureTime == null || departureTime.isBlank()) {
            throw new IllegalArgumentException("Departure time cannot be empty.");
        }
        this.departureTime = departureTime;
    }

    public void setArrivalTime(String arrivalTime) {
        if (arrivalTime == null || arrivalTime.isBlank()) {
            throw new IllegalArgumentException("Arrival time cannot be empty.");
        }
        this.arrivalTime = arrivalTime;
    }

    // Display method
    public void display() {
        System.out.println("Transport ID: " + transportID);
        System.out.println("Transport Type: " + transportType);
        System.out.println("Company Name: " + companyName);
        System.out.println("Seats Available: " + seatsAvailable);
        System.out.println("Price Per Seat: " + pricePerSeat);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
    }
}