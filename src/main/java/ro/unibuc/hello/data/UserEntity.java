package ro.unibuc.hello.data;
import org.springframework.data.annotation.Id;

public class UserEntity {
    @Id
    private String id;
    private String fullName;
    private int age;
    private String email;
    private String password;
    private String phoneNumber;
    private int points;

    public UserEntity() {}

    public UserEntity(String fullName, int age, String email, String password, String phoneNumber, int points) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.points = points;
    }

    public UserEntity(String id, String fullName, int age, String email, String password, String phoneNumber, int points) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.password = password;
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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    @Override
    public String toString() {
        return String.format(
                "UserEntity[id='%s', fullName='%s', age='%d', email='%s', phoneNumber='%s', points='%d']",
                id, fullName, age, email, phoneNumber, points);
    }
}
