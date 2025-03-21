package ro.unibuc.hello.dto;

public class User {
    private String id;
    private String fullName;
    private int age;
    private String email;
    private String phoneNumber;
    private int points;

    public User() {}

    public User(String id, String fullName, int age, String email, String phoneNumber, int points) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    
}
