package bg.tu_varna.sit.hotel.presentation.models;

public class UserModel {
    private Long id;
    private String firstName;
    private String lastName;

    public UserModel(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", this.id, this.firstName, this.lastName);
    }
}