package Common;

public class Connection {
    private String name;
    private int roomNumber;
    private String email;
    private String mobile;

    public Connection(String name, int roomNumber, String email, String mobile) {
        this.name = name;
        this.roomNumber = roomNumber;
        this.email = email;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public String toString() {
        return "Name: " + name + " | Room Number: " + roomNumber + " | Email: " + email + " | Mobile: " + mobile;
    }
}
