package Common;

public class Room {
    private int roomNumber;
    private int capacity;
    private double pricePerNight;

    public Room(int roomNumber, int capacity, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " | Capacity: " + capacity + " | Price/Night: " + pricePerNight + " Toman";
    }
}
