import java.util.ArrayList;
import java.util.Date;
    public class Trip {
        private int tripID;
        private String tripName;
        private Date endDate;
        private Date startDate;
        private double priceperperson;
        private int totalSeats;
        private int availableSeats;
        private int destinationID;
        private int transportID;
        private ArrayList<Booking>bookings;
        private Object b;

        public Trip(int tripID, String tripName, Date startDate, Date endDate, double pricePerPerson,
                    int totalSeats, int availableSeats, int destinationID, int transportID,
                    ArrayList<Booking> bookings) {
            this.tripID = tripID;
            this.tripName = tripName;
            this.startDate = startDate;
            this.endDate = endDate;
            this.priceperperson = pricePerPerson;
            this.totalSeats = totalSeats;
            this.availableSeats = availableSeats;
            this.destinationID = destinationID;
            this.transportID = transportID;
            this.bookings = bookings != null ? bookings : new ArrayList<>();
        }
        public int getTripID(){
            return tripID;
        }

        public String getTripName() {
            return tripName;
        }

        public double getPriceperperson() {
            return priceperperson;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }

        public void setAvailableSeats(int availableSeats) {
            this.availableSeats = availableSeats;
        }

        public void displayTripDetails(){

            System.out.println("Trip ID: "+tripID);
            System.out.println("Trip Name: "+tripName);
            System.out.println("end Date: "+endDate);
            System.out.println("start Date: "+startDate);
            System.out.println("price per person: "+priceperperson);
            System.out.println("Total seats: "+totalSeats);
            System.out.println("Available Seats: "+availableSeats);
            System.out.println("Destination ID: "+destinationID);
            System.out.println("Transport ID: "+transportID);
            System.out.println("bookings: "+bookings.size());
            for(Booking booking:bookings){

                System.out.println(booking.toString());
            }


        }

        public void addBooking(Booking b){
            int numPeople = b.getNumberOfPeople();
            if(checkAvailability(numPeople)){
                bookings.add(b);
                this.availableSeats -= numPeople;
                System.out.println("Booking added successfully for .");
            } else {
                System.out.println("Not enough available seats for this booking.");
            }
        }

        private boolean checkAvailability(int numPeople) {
            return numPeople <= availableSeats;
        }
        public void removeBooking(int bookingId) {
            Booking bookingToRemove = null;
            int numpeopleInBooking = 0;

            for(Booking booking : bookings) {
                if (booking.getBookingID() == bookingId) {
                    bookingToRemove = booking;
                    numpeopleInBooking = booking.getNumberOfPeople();
                    break;
                }
            }

            if (bookingToRemove != null) {
                bookings.remove(bookingToRemove);
                this.availableSeats += numpeopleInBooking;
            }
        }
    }

