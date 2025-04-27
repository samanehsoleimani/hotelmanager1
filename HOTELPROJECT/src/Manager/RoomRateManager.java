package Manager;

import Common.Room;
import txtFileManager.txtfilemanager;
import java.util.Scanner;

public class RoomRateManager {
    private Room[] rooms;
    private Scanner scanner;
    private txtfilemanager fileManager;

    public RoomRateManager() {
        scanner = new Scanner(System.in);
        fileManager = new txtfilemanager("ROOMRATE.txt");

        rooms = new Room[] {
                new Room(101, 1, 1000000),
                new Room(102, 1, 1000000),
                new Room(103, 1, 1000000),
                new Room(104, 1, 1000000),
                new Room(105, 1, 1000000),
                new Room(201, 2, 2000000),
                new Room(202, 2, 2000000),
                new Room(203, 2, 2000000),
                new Room(204, 2, 2000000),
                new Room(205, 2, 2000000),
                new Room(301, 3, 3000000),
                new Room(302, 3, 3000000),
                new Room(303, 3, 3000000),
                new Room(401, 4, 4000000),
                new Room(402, 4, 4000000),
                new Room(403, 4, 4000000),
                new Room(404, 4, 4000000),
                new Room(501, 5, 5000000),
                new Room(502, 5, 6000000),
                new Room(503, 5, 6000000)
        };
    }

    public void startRoomBooking() {
        System.out.println("🏨 Welcome to Room Booking");
        System.out.print("Please enter your name: ");
        String guestName = scanner.nextLine().trim();

        listAllRooms();

        System.out.print("Please enter the room number you want to reserve: ");
        int selectedRoomNumber = scanner.nextInt();

        Room selectedRoom = getRoomByNumber(selectedRoomNumber);
        if (selectedRoom == null) {
            System.out.println("❌ Room not found!");
            return;
        }

        System.out.print("How many nights will you stay? ");
        int nights = scanner.nextInt();
        scanner.nextLine();

        double totalPrice = selectedRoom.getPricePerNight() * nights;

        StringBuilder reservationDetails = new StringBuilder();
        reservationDetails.append("\n[Room Rating]\n");
        reservationDetails.append("Guest Name: ").append(guestName).append("\n");
        reservationDetails.append("Room Number: ").append(selectedRoom.getRoomNumber()).append("\n");
        reservationDetails.append("Room Capacity: ").append(selectedRoom.getCapacity()).append(" beds\n");
        reservationDetails.append("Price per Night: ").append(selectedRoom.getPricePerNight()).append(" Toman\n");
        reservationDetails.append("Nights: ").append(nights).append("\n");
        reservationDetails.append("Total Price: ").append(totalPrice).append(" Toman\n");

        fileManager.AppendRow(reservationDetails.toString());

        System.out.println("✅ Reservation completed successfully!");
        System.out.println("💰 Total price: " + totalPrice + " Toman");
    }

    public void listAllRooms() {
        System.out.println("📋 Available Rooms:");
        for (int i = 0; i < rooms.length; i++) {
            Room room = rooms[i];
            System.out.println("Room Number: " + room.getRoomNumber() +
                    " | Capacity: " + room.getCapacity() + " beds" +
                    " | Price/Night: " + room.getPricePerNight() + " Toman");
        }
    }

    private Room getRoomByNumber(int number) {
        for (int i = 0; i < rooms.length; i++) {
            if (rooms[i].getRoomNumber() == number) {
                return rooms[i];
            }
        }
        return null;
    }
}
