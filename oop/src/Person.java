public abstract class Person {
    private String fullName;
    private String email;
    private String passwordHash;
    private String phoneNumber;

    public Person(String fullName, String email, String passwordHash, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword_hash() {
        return passwordHash;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            System.out.println("Invalid email!");
            return;
        }
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            System.out.println("Invalid phone number!");
            return;
        }
        this.phoneNumber = phoneNumber;
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.length() < 6) {
            System.out.println("Password too short!");
            return;
        }
        this.passwordHash = passwordHash;
    }


    public abstract void showInfo() ;
}
