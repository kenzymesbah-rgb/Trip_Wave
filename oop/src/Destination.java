public class Destination {

    private int destinationId;
    private String destinationName;
    private String country;
    private String city;
    private String description;
    private double averageCost;

    // Constructor with validation
    public Destination(int destinationId, String destinationName, String country,
                       String city, String description, double averageCost) {

        if (destinationName == null || destinationName.isBlank()) {
            throw new IllegalArgumentException("Destination name cannot be empty.");
        }

        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }

        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty.");
        }

        if (description == null || description.length() < 5) {
            throw new IllegalArgumentException("Description must be at least 5 characters.");
        }

        if (averageCost <= 0) {
            throw new IllegalArgumentException("Average cost must be positive.");
        }

        this.destinationId = destinationId;
        this.destinationName = destinationName;
        this.country = country;
        this.city = city;
        this.description = description;
        this.averageCost = averageCost;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        if (destinationName == null || destinationName.isBlank()) {
            throw new IllegalArgumentException("Destination name cannot be empty.");
        }
        this.destinationName = destinationName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be empty.");
        }
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be empty.");
        }
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.length() < 5) {
            throw new IllegalArgumentException("Description must be at least 5 characters.");
        }
        this.description = description;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        if (averageCost <= 0) {
            throw new IllegalArgumentException("Average cost must be positive.");
        }
        this.averageCost = averageCost;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "destinationId=" + destinationId +
                ", destinationName='" + destinationName + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", averageCost=" + averageCost +
                '}';
    }

    public void displayInfo() {
        System.out.println("Destination ID: " + destinationId);
        System.out.println("Name: " + destinationName);
        System.out.println("Country: " + country);
        System.out.println("City: " + city);
        System.out.println("Description: " + description);
        System.out.println("Average Cost: " + averageCost);
    }
}