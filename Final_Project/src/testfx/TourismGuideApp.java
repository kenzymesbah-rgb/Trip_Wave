package testfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;

public class TourismGuideApp extends Application {

    private Stage stage;
    private String loggedInUserEmail = null; // نستخدم الايميل للتعرف على المستخدم

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            // تأكد من مسار الأيقونة
            Image icon = new Image(getClass().getResourceAsStream("/testfx/images/mmm.jpg"));
            stage.getIcons().add(icon);
        } catch (Exception e) {}

        stage.setTitle("Trip Wave");
        stage.setScene(new Scene(homePage(), 900, 600));
        stage.show();
    }

    private HBox createNavBar() {
        HBox navBar = new HBox(15);
        navBar.setPadding(new Insets(10));
        navBar.setStyle("-fx-background-color:#add8e2; -fx-background-radius: 6;");

        Button homeBtn = new Button("Home");
        Button TripBtn = new Button("Trips");
        Button bookingBtn = new Button("Bookings");
        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register");

        for (Button btn : List.of(homeBtn, TripBtn, bookingBtn, loginBtn, registerBtn)) {
            btn.setStyle("-fx-background-color:#ffffff; -fx-text-fill:#0d47a1; -fx-font-weight:bold; -fx-padding:6 12 6 12; -fx-background-radius:6;");
        }
        Region space = new Region();
        HBox.setHgrow(space, Priority.ALWAYS);

        homeBtn.setOnAction(e -> stage.setScene(new Scene(homePage(), 900, 600)));
        TripBtn.setOnAction(e -> stage.setScene(new Scene(tripsPage(), 900, 600)));
        bookingBtn.setOnAction(e -> stage.setScene(new Scene(bookingsPage(), 900, 600)));
        registerBtn.setOnAction(e -> stage.setScene(new Scene(signUpPage(), 700, 700)));
        loginBtn.setOnAction(e -> stage.setScene(new Scene(loginPage(), 550, 650)));

        if (loggedInUserEmail == null) {
            navBar.getChildren().addAll(homeBtn, TripBtn, space, loginBtn, registerBtn);
        } else {
            Button userBtn = new Button(loggedInUserEmail);
            userBtn.setStyle("-fx-background-color:#ffffff; -fx-text-fill:#000000; -fx-font-weight:bold; -fx-padding:6 12 6 12; -fx-background-radius:6;");
            userBtn.setOnAction(e -> stage.setScene(new Scene(userPage(), 900, 600)));
            navBar.getChildren().addAll(homeBtn, TripBtn, bookingBtn, space, userBtn);
        }

        return navBar;
    }

    /* =============== USER PAGE ===========*/
    private Parent userPage() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff); -fx-font-family: 'Arial';");

        Label title = new Label("Your Profile");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");

        // 1. Fetch Current Data
        Customer currentData = Customer.getCustomerByEmail(loggedInUserEmail);

        // Safety check to prevent crash if data is null
        String nameVal = (currentData != null) ? currentData.getFullName() : "";
        String phoneVal = (currentData != null) ? currentData.getPhoneNumber() : "";
        String cityVal = (currentData != null) ? currentData.getCity() : "";
        String natVal = (currentData != null) ? currentData.getNationality() : "";

        // 2. Setup Display Fields
        VBox form = new VBox(15);
        form.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        TextField nameField = new TextField(nameVal);
        TextField phoneField = new TextField(phoneVal);
        TextField cityField = new TextField(cityVal);
        TextField natField = new TextField(natVal);

        // Initially Disabled (View Mode)
        nameField.setEditable(false); phoneField.setEditable(false);
        cityField.setEditable(false); natField.setEditable(false);

        String transparentStyle = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #333333; -fx-font-size: 14px;";
        nameField.setStyle(transparentStyle + "-fx-font-weight: bold;");
        phoneField.setStyle(transparentStyle);
        cityField.setStyle(transparentStyle);
        natField.setStyle(transparentStyle);

        form.getChildren().addAll(
                new Label("Full Name:"), nameField,
                new Label("Phone:"), phoneField,
                new Label("City:"), cityField,
                new Label("Nationality:"), natField
        );

        Label statusMsg = new Label();

        // 3. Buttons Area
        HBox buttons = new HBox(15);

        Button back = new Button("Back");
        back.setStyle("-fx-background-color:#0d47a1; -fx-text-fill:white; -fx-font-weight:bold;");
        back.setOnAction(e -> stage.setScene(new Scene(homePage(), 900, 600)));

        Button editBtn = new Button("Edit Profile");
        Button saveBtn = new Button("Save Changes");
        saveBtn.setDisable(true);

        editBtn.setStyle("-fx-background-color:#f9a825; -fx-text-fill:white; -fx-font-weight:bold;");
        saveBtn.setStyle("-fx-background-color:#2e7d32; -fx-text-fill:white; -fx-font-weight:bold;");

        // Edit Logic
        editBtn.setOnAction(e -> {
            nameField.setEditable(true); nameField.setStyle("-fx-border-color: #90caf9; -fx-background-color: white;");
            phoneField.setEditable(true); phoneField.setStyle("-fx-border-color: #90caf9; -fx-background-color: white;");
            cityField.setEditable(true); cityField.setStyle("-fx-border-color: #90caf9; -fx-background-color: white;");
            natField.setEditable(true); natField.setStyle("-fx-border-color: #90caf9; -fx-background-color: white;");
            saveBtn.setDisable(false);
            editBtn.setDisable(true);
        });

        // Save Logic (Database)
        saveBtn.setOnAction(e -> {
            boolean success = Customer.updateCustomer(loggedInUserEmail, nameField.getText(), phoneField.getText(), cityField.getText(), natField.getText());
            if(success) {
                statusMsg.setText("Profile Updated Successfully!");
                statusMsg.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                // Reset to View Mode
                nameField.setEditable(false); nameField.setStyle(transparentStyle + "-fx-font-weight: bold;");
                phoneField.setEditable(false); phoneField.setStyle(transparentStyle);
                cityField.setEditable(false); cityField.setStyle(transparentStyle);
                natField.setEditable(false); natField.setStyle(transparentStyle);
                saveBtn.setDisable(true);
                editBtn.setDisable(false);
            } else {
                statusMsg.setText("Update Failed. Try again.");
                statusMsg.setStyle("-fx-text-fill: red;");
            }
        });

        Button logout = new Button("Logout");
        logout.setStyle("-fx-background-color:#c62828; -fx-text-fill:white; -fx-font-weight:bold;");
        logout.setOnAction(e -> {
            loggedInUserEmail = null;
            stage.setScene(new Scene(homePage(), 900, 600));
        });

        buttons.getChildren().addAll(back, editBtn, saveBtn, logout);
        layout.getChildren().addAll(title, form, statusMsg, buttons);
        return layout;
    }


    /* ================= LOGIN PAGE ================= */
    private Parent loginPage() {
        VBox layout = new VBox(18);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setPrefWidth(450);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff); -fx-font-family: 'Arial';");

        try {
            Image loginnnImg = new Image(getClass().getResourceAsStream("/testfx/images/loginnn.jpg"));
            ImageView loginView = new ImageView(loginnnImg);
            loginView.setFitWidth(150);
            loginView.setPreserveRatio(true);
            loginView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.3, 0, 2);");
            layout.getChildren().add(loginView);
        } catch(Exception ex) {}

        Label title = new Label("Login");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");

        Label welcome = new Label("Welcome back, please sign in to continue");
        welcome.setStyle("-fx-font-size:16px; -fx-text-fill:#333333;");

        TextField username = new TextField();
        username.setPromptText("Email");
        username.setPrefWidth(300);
        username.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setPrefWidth(300);
        password.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        Label result = new Label();
        result.setStyle("-fx-font-size:14px; -fx-text-fill:#1b5e20;");

        Button loginBtn = new Button("Sign In");
        loginBtn.setPrefWidth(150);
        loginBtn.setStyle("-fx-background-color:#1b5e20; -fx-text-fill:white; -fx-font-weight:bold; -fx-padding:8 16 8 16; -fx-background-radius:6;");

        loginBtn.setOnAction(e -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                result.setText("Please enter both email and password");
                result.setStyle("-fx-text-fill:red;");
            } else {
                if(Customer.checkLogin(username.getText(), password.getText())) {
                    loggedInUserEmail = username.getText();
                    stage.setScene(new Scene(homePage(), 900, 600));
                } else {
                    result.setText("Invalid credentials. Try again.");
                    result.setStyle("-fx-text-fill:red;");
                }
            }
        });

        Hyperlink registerLink = new Hyperlink("If you don't have an account, click here");
        registerLink.setStyle("-fx-text-fill:#0d47a1; -fx-font-size:13px;");
        registerLink.setOnAction(e -> stage.setScene(new Scene(signUpPage(), 900, 600)));

        Button back = new Button("Back");
        back.setPrefWidth(150);
        back.setStyle("-fx-background-color:#0d47a1; -fx-text-fill:white; -fx-font-weight:bold; -fx-padding:8 16 8 16; -fx-background-radius:6;");
        back.setOnAction(e -> stage.setScene(new Scene(homePage(), 900, 600)));

        layout.getChildren().addAll(title, welcome, username, password, loginBtn, result, registerLink, back);
        Platform.runLater(() -> stage.sizeToScene());
        return layout;
    }

    /* ================= SIGN UP PAGE ================= */
    private Parent signUpPage() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff); -fx-font-family: 'Arial';");

        try {
            Image registerImg = new Image(getClass().getResourceAsStream("/testfx/images/register.jpg"));
            ImageView registerView = new ImageView(registerImg);
            registerView.setFitWidth(200);
            registerView.setPreserveRatio(true);
            registerView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.3, 0, 2);");
            layout.getChildren().add(registerView);
        } catch(Exception ex) {}

        Label title = new Label("Register");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");

        Label info = new Label("Please enter your information to sign up");
        info.setStyle("-fx-font-size:16px; -fx-text-fill:#333333;");

        double fieldWidth = 200;

        TextField fullName = new TextField(); fullName.setPromptText("Full Name"); fullName.setPrefWidth(fieldWidth);
        TextField username = new TextField(); username.setPromptText("Email"); username.setPrefWidth(fieldWidth);
        TextField phone = new TextField(); phone.setPromptText("Phone Number"); phone.setPrefWidth(fieldWidth);
        PasswordField password = new PasswordField(); password.setPromptText("Password"); password.setPrefWidth(fieldWidth);
        TextField city = new TextField(); city.setPromptText("City"); city.setPrefWidth(fieldWidth);
        TextField nationality = new TextField(); nationality.setPromptText("Nationality"); nationality.setPrefWidth(fieldWidth);

        for(TextField tf : Arrays.asList(fullName, username, phone, password, city, nationality)) {
            tf.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");
        }

        Label result = new Label();
        result.setStyle("-fx-font-size:14px; -fx-text-fill:#1b5e20;");

        Button signUp = new Button("Sign Up");
        signUp.setPrefWidth(150);
        signUp.setStyle("-fx-background-color:#1b5e20; -fx-text-fill:white; -fx-font-weight:bold; -fx-padding:8 16 8 16; -fx-background-radius:6;");

        signUp.setOnAction(e -> {
            if (fullName.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty()) {
                result.setText("Please fill all required fields");
                result.setStyle("-fx-text-fill:red;");
            } else {
                Customer c = new Customer(fullName.getText(), username.getText(), password.getText(), phone.getText(), city.getText(), nationality.getText());
                if(c.saveToDatabase()) {
                    result.setText("Account created successfully ✔ Please Login.");
                    result.setStyle("-fx-text-fill:#1b5e20; -fx-font-weight:bold;");
                } else {
                    result.setText("Email already exists!");
                    result.setStyle("-fx-text-fill:red;");
                }
            }
        });

        Button back = new Button("Back");
        back.setPrefWidth(150);
        back.setStyle("-fx-background-color:#0d47a1; -fx-text-fill:white; -fx-font-weight:bold; -fx-padding:8 16 8 16; -fx-background-radius:6;");
        back.setOnAction(e -> stage.setScene(new Scene(homePage(), 900, 600)));

        layout.getChildren().addAll(title, info, fullName, username, phone, password, city, nationality, signUp, result, back);
        Platform.runLater(() -> stage.sizeToScene());
        return layout;
    }

    /* ================= HOME PAGE ================= */
    private BorderPane homePage() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setStyle("-fx-font-family: 'Arial';");

        try {
            Image homeImg = new Image(getClass().getResourceAsStream("/testfx/images/mmm.jpg"));
            BackgroundImage backgroundImage = new BackgroundImage(
                    homeImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 2.0, true, true, false, false)
            );
            root.setBackground(new Background(backgroundImage));
        } catch(Exception e) {}

        Label title = new Label("Welcome to Trip Wave");
        title.setStyle("-fx-font-size: 42px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 6, 0.3, 0, 2);");

        Label subtitle = new Label("Choose your destination and explore the world with us!");
        subtitle.setStyle("-fx-font-size: 22px; -fx-text-fill: #e3f2fd; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 4, 0.3, 0, 1);");

        VBox center = new VBox(25, title, subtitle);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(50));

        root.setTop(createNavBar());
        root.setCenter(center);
        return root;
    }


    /* ================= BOOKINGS PAGE ================= */
    private VBox bookingsPage() {
        HBox navBar = createNavBar();
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white;");

        Label title = new Label("Your Booked Trips");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");
        content.getChildren().add(title);

        // Fetch bookings from DB
        List<Booking> myBookings = Booking.getBookingsForUser(loggedInUserEmail);

        if (myBookings.isEmpty()) {
            content.getChildren().add(new Label("You haven't booked any trips yet."));
        } else {
            for (Booking b : myBookings) {
                VBox card = new VBox(8);
                card.setPadding(new Insets(12));
                card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);");

                Label details = new Label(
                        "Trip: " + b.getTripName() + "\n" +
                                "Destination: " + b.getDestination() + "\n" +
                                "Guests: " + b.getGuests() + "\n" +
                                "Total Price: $" + b.getTotalPrice()
                );
                details.setStyle("-fx-font-size:15px; -fx-text-fill:#333333;");

                Button cancelBtn = new Button("Cancel Booking");
                cancelBtn.setStyle("-fx-background-color:#e53935; -fx-text-fill:white; -fx-font-weight:bold;");
                cancelBtn.setOnAction(e -> {
                    if(Booking.cancelBooking(b.getBookingID())) {
                        stage.setScene(new Scene(bookingsPage(), 900, 600)); // Refresh page
                    }
                });

                card.getChildren().addAll(details, cancelBtn);
                content.getChildren().add(card);
            }
        }

        Button back = new Button("Back");
        back.setStyle("-fx-background-color:#0d47a1; -fx-text-fill:white; -fx-font-weight:bold; -fx-padding:8 16;");
        back.setOnAction(e -> stage.setScene(new Scene(homePage(), 900, 600)));
        content.getChildren().add(back);

        ScrollPane scroll = new ScrollPane(content);
        scroll.setFitToWidth(true);
        VBox root = new VBox();
        root.getChildren().addAll(navBar, scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return root;
    }

    /* ================= TRIPS PAGE ================= */
    private VBox tripsPage() {
        HBox navBar = createNavBar();
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setStyle("-fx-background-color: white; -fx-font-family: 'Arial';");

        Label title = new Label("Available Trips");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");
        content.getChildren().add(title);

        List<Trip> dbTrips = Trip.getAllTripsFromDB();

        if(dbTrips.isEmpty()) {
            Label lbl = new Label("No trips found in database. Please run the SQL INSERT script.");
            content.getChildren().add(lbl);
        } else {
            for (Trip t : dbTrips) {
                content.getChildren().add(tripCard(t));
            }
        }

        Button back = new Button("Back");
        back.setStyle("-fx-background-color:#0d47a1; -fx-text-fill:white; -fx-font-weight:bold;");
        back.setOnAction(e -> stage.setScene(new Scene(homePage(), 900, 600)));
        content.getChildren().add(back);

        ScrollPane scrollableContent = new ScrollPane(content);
        scrollableContent.setFitToWidth(true);
        scrollableContent.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        VBox root = new VBox();
        root.getChildren().addAll(navBar, scrollableContent);
        VBox.setVgrow(scrollableContent, Priority.ALWAYS);
        return root;
    }

    private HBox tripCard(Trip trip) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 8; -fx-background-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2);");

        VBox info = new VBox(8);
        info.setAlignment(Pos.CENTER_LEFT);
        Label name = new Label("Trip: " + trip.getTripName());
        Label dest = new Label("Destination: " + trip.getDestName());
        Label price = new Label("Price: $" + trip.getPrice());
        Label transport = new Label("Transport: " + trip.getTransType());

        for (Label lbl : List.of(name, dest, price, transport)) lbl.setStyle("-fx-font-size:14px; -fx-text-fill:#333333;");

        info.getChildren().addAll(name, dest, price, transport);

        Button book = new Button("Book now");
        book.setStyle("-fx-background-color:#1b5e20; -fx-text-fill:white; -fx-font-weight:bold;");
        book.setOnAction(e -> {
            if (loggedInUserEmail == null) stage.setScene(new Scene(loginPage(), 550, 650));
            else stage.setScene(new Scene(bookingPage(trip), 600, 500));
        });

        VBox actions = new VBox(book);
        actions.setAlignment(Pos.CENTER);
        card.getChildren().addAll(info, actions);
        return card;
    }

    /* ================= BOOKING PAGE (Single Trip) ================= */
    private VBox bookingPage(Trip trip) {
        VBox root = new VBox(18);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e3f2fd, #ffffff);");

        Label title = new Label("Booking Page: " + trip.getTripName());
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #0d47a1;");

        TextField name = new TextField();
        name.setPromptText("Full Name");
        name.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        TextField email = new TextField();
        email.setPromptText("Email");
        if(loggedInUserEmail != null) email.setText(loggedInUserEmail); // Auto-fill
        email.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        TextField guests = new TextField();
        guests.setPromptText("Number of Guests");
        guests.setStyle("-fx-background-color: #ffffff; -fx-border-color: #90caf9; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;");

        Label result = new Label();
        Button confirm = new Button("Confirm Booking");
        confirm.setStyle("-fx-background-color:#1b5e20; -fx-text-fill:white; -fx-font-weight:bold; -fx-padding:8 16;");

        confirm.setOnAction(e -> {
            try {
                int count = Integer.parseInt(guests.getText());
                int cusID = Customer.getIdByEmail(loggedInUserEmail);

                if (cusID != -1) {
                    // Create DB Object directly
                    Booking booking = new Booking(cusID, trip.getTripID(), new java.util.Date(), count, "Paid");
                    booking.calculatePrice(trip.getPrice());

                    if(booking.saveToDatabase()) {
                        result.setText("Booking Saved Successfully!");
                        result.setStyle("-fx-text-fill:green;");
                    } else {
                        result.setText("Error saving booking to Database.");
                        result.setStyle("-fx-text-fill:red;");
                    }
                } else {
                    result.setText("User error (ID not found).");
                    result.setStyle("-fx-text-fill:red;");
                }
            } catch (Exception ex) {
                result.setText("Invalid input (Enter a number).");
                result.setStyle("-fx-text-fill:red;");
            }
        });

        Button back = new Button("Back");
        back.setStyle("""
        -fx-background-color:#0d47a1;
        -fx-text-fill:white;
        -fx-font-weight:bold;
        -fx-padding:8 16 8 16;
        -fx-background-radius:6;
    """);
        back.setOnAction(e -> stage.setScene(new Scene(tripsPage(), 900, 600)));

        root.getChildren().addAll(title, name, email, guests, confirm, result, back);
        return root;
    }



    public static void main(String[] args) {
        launch(args);
    }
}

// =================================================================================
// ====================  BACKEND CLASSES (INTERNAL) ================================
// =================================================================================

class LinkSQL {
    public static Connection getConnection() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Tourism;encrypt=true;trustServerCertificate=true;";
            String user = "sa";
            String password = "NewStrongPassword123";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection Failed: " + e.getMessage());
            return null;
        }
    }
}

abstract class Person {
    private String fullName, email, passwordHash, phoneNumber;
    public Person(String fullName, String email, String passwordHash, String phoneNumber) {
        this.fullName = fullName; this.email = email; this.passwordHash = passwordHash; this.phoneNumber = phoneNumber;
    }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getPhoneNumber() { return phoneNumber; }
    public abstract void showInfo();
}

class Customer extends Person {
    private String city, nationality, state = "N/A";

    public Customer(String fullName, String email, String password, String phone, String city, String nationality) {
        super(fullName, email, password, phone);
        this.city = city;
        this.nationality = nationality;
    }

    public String getCity() { return city; }
    public String getNationality() { return nationality; }

    public static Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE Email = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return null;
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("FullName"), rs.getString("Email"), rs.getString("Password"), rs.getString("Phone"), rs.getString("City"), rs.getString("Nationality"));
            }
        } catch (SQLException e) { }
        return null;
    }

    public static boolean updateCustomer(String email, String newName, String newPhone, String newCity, String newNat) {
        String sql = "UPDATE Customer SET FullName=?, Phone=?, City=?, Nationality=? WHERE Email=?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return false;
            ps.setString(1, newName); ps.setString(2, newPhone); ps.setString(3, newCity); ps.setString(4, newNat); ps.setString(5, email);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { return false; }
    }

    public boolean saveToDatabase() {
        if (getIdByEmail(this.getEmail()) != -1) return false;
        String sql = "INSERT INTO Customer (FullName, Email, Password, City, State, Phone, Nationality) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return false;
            ps.setString(1, getFullName()); ps.setString(2, getEmail()); ps.setString(3, getPasswordHash());
            ps.setString(4, city); ps.setString(5, state); ps.setString(6, getPhoneNumber()); ps.setString(7, nationality);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    public static boolean checkLogin(String email, String password) {
        String sql = "SELECT * FROM Customer WHERE Email = ? AND Password = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return false;
            ps.setString(1, email); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) { return false; }
    }
    public static int getIdByEmail(String email) {
        String sql = "SELECT CusID FROM Customer WHERE Email = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return -1;
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("CusID");
        } catch (SQLException e) { }
        return -1;
    }
    @Override public void showInfo() {}
}

class Trip {
    private int tripID, availableSeats;
    private String tripName, destName, transType;
    private double price;
    public Trip(int tripID, String tripName, double price, int availableSeats, String destName, String transType) {
        this.tripID = tripID; this.tripName = tripName; this.price = price;
        this.availableSeats = availableSeats; this.destName = destName; this.transType = transType;
    }
    public int getTripID() { return tripID; }
    public String getTripName() { return tripName; }
    public double getPrice() { return price; }
    public String getDestName() { return destName; }
    public String getTransType() { return transType; }

    public static List<Trip> getAllTripsFromDB() {
        List<Trip> list = new ArrayList<>();
        String sql = "SELECT T.TripID, T.TripName, T.PricePerPerson, T.AvailSeats, D.City, D.Country, Tr.TransType FROM Trip T JOIN Destination D ON T.DesID = D.DesID JOIN Transport Tr ON T.TransID = Tr.TransID WHERE T.AvailSeats > 0";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con!=null) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String fullDest = rs.getString("City") + ", " + rs.getString("Country");
                    list.add(new Trip(rs.getInt("TripID"), rs.getString("TripName"), rs.getDouble("PricePerPerson"), rs.getInt("AvailSeats"), fullDest, rs.getString("TransType")));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}

class Booking {
    private int bookingID, cusID, tripID, numOfPeople;
    private java.util.Date bookDate;
    private double totalPrice;
    private String status, tripNameDisplay, destinationDisplay;

    // Constructor for DB Insert
    public Booking(int cusID, int tripID, java.util.Date bookDate, int numOfPeople, String status) {
        this.cusID = cusID; this.tripID = tripID; this.bookDate = bookDate; this.numOfPeople = numOfPeople; this.status = status;
    }

    // Constructor for Displaying Bookings List
    public Booking(int bookingID, String tripName, String destination, java.util.Date date, int num, double price) {
        this.bookingID = bookingID;
        this.tripNameDisplay = tripName;
        this.destinationDisplay = destination;
        this.bookDate = date;
        this.numOfPeople = num;
        this.totalPrice = price;
    }

    public void calculatePrice(double pricePerPerson) { this.totalPrice = numOfPeople * pricePerPerson; }

    // Getters
    public String getTripName() { return tripNameDisplay; }
    public String getDestination() { return destinationDisplay; }
    public int getGuests() { return numOfPeople; }
    public double getTotalPrice() { return totalPrice; }
    public int getBookingID() { return bookingID; }

    public static List<Booking> getBookingsForUser(String email) {
        List<Booking> list = new ArrayList<>();
        int cID = Customer.getIdByEmail(email);
        if(cID == -1) return list;

        String sql = "SELECT B.BookingID, T.TripName, D.City, D.Country, B.BookDate, B.NumOfPeople, B.Price FROM Booking B JOIN Trip T ON B.TripID = T.TripID JOIN Destination D ON T.DesID = D.DesID WHERE B.CusID = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con!=null) {
                ps.setInt(1, cID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    String dest = rs.getString("City") + ", " + rs.getString("Country");
                    list.add(new Booking(rs.getInt("BookingID"), rs.getString("TripName"), dest, rs.getDate("BookDate"), rs.getInt("NumOfPeople"), rs.getDouble("Price")));
                }
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return list;
    }

    public static boolean cancelBooking(int bID) {
        String sql = "DELETE FROM Booking WHERE BookingID = ?";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return false;
            ps.setInt(1, bID);
            ps.executeUpdate();
            return true;
        } catch(SQLException e) { return false; }
    }

    public boolean saveToDatabase() {
        String sql = "INSERT INTO Booking (CusID, TripID, BookDate, NumOfPeople, Price, PaymentStatus, PaymentMethod) VALUES (?, ?, ?, ?, ?, ?, 'Credit Card')";
        try (Connection con = LinkSQL.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if(con==null) return false;
            ps.setInt(1, cusID); ps.setInt(2, tripID);
            ps.setDate(3, new java.sql.Date(bookDate.getTime()));
            ps.setInt(4, numOfPeople); ps.setDouble(5, totalPrice); ps.setString(6, status);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}