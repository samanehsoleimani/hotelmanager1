package Manager;

import Commons.commons;
import Commons.Guests;
import txtfilemanager.txtfilemanager;
import Commons.Room1; // تغییر از Room به Room1
import txtfilemanager.txtfilemanager;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {

    public txtfilemanager fn;

    public RoomManager() {
        fn = new txtfilemanager("ROOM.txt");
    }

    // ✅ افزودن یا بروزرسانی اتاق
    public void saveOrUpdateRoom(Room1 b) {
        String[] rooms = fn.getArrayFromFile();
        List<String> updatedRooms = new ArrayList<>();
        boolean found = false;

        for (String line : rooms) {
            if (line.trim().isEmpty()) continue;

            Room1 existing = Str2Room(line);
            if (existing.getRoomNumber() == b.getRoomNumber()) {
                // اگر شماره اتاق یکی بود، به‌روزرسانی کن
                updatedRooms.add(RoomToStr(b));
                found = true;
            } else {
                updatedRooms.add(line);
            }
        }

        // اگر اتاق جدید بود، اضافه کن
        if (!found) {
            updatedRooms.add(RoomToStr(b));
        }

        // نوشتن کل فایل از نو
        StringBuilder data = new StringBuilder();
        for (String r : updatedRooms) {
            data.append(r).append("\n");
        }

        fn.setIntoFile(data.toString().trim());
    }

    // 🔄 تبدیل رشته به Room1
    private Room1 Str2Room(String s) {
        String[] A = s.split(commons.Splitter);
        Room1 r = new Room1();
        r.setRoomNumber(Integer.parseInt(A[0]));
        r.setStatus(A[1]);
        r.setGuestsName(A[2]);
        r.setBed(A[3]);
        return r;
    }

    // 🔄 تبدیل Room1 به رشته ذخیره‌سازی
    private String RoomToStr(Room1 b) {
        return b.getRoomNumber() + commons.Splitter +
               b.getStatus() + commons.Splitter +
               b.getGuestsName() + commons.Splitter +
               b.getBed();
    }

    public void DeleteRoom(int roomNumber) {
        String[] rooms = fn.getArrayFromFile();
        String newData = "";

        for (String room : rooms) {
            if (room.trim().isEmpty()) continue;

            Room1 r = Str2Room(room);
            if (roomNumber == r.getRoomNumber()) continue;

            newData += room + "\n";
        }

        fn.setIntoFile(newData.trim());
    }

    public String return_information(int roomNumber) {
        String[] rooms = fn.getArrayFromFile();

        for (String room : rooms) {
            if (room.trim().isEmpty()) continue;

            Room1 r = Str2Room(room);
            if (r.getRoomNumber() == roomNumber) {
                return "📄 اطلاعات فایل برای اتاق " + roomNumber + ":\n" + RoomToStr(r);
            }
        }

        // اگه داخل فایل نبود ولی جزو 10 اتاق اولیه بود
        if (roomNumber >= 1 && roomNumber <= 10) {
            Room1 defaultRoom = new Room1();
            defaultRoom.setRoomNumber(roomNumber);
            defaultRoom.setStatus("موجود");
            defaultRoom.setGuestsName("");
            if (roomNumber <= 5) {
                defaultRoom.setBed("4");
            } else if (roomNumber <= 7) {
                defaultRoom.setBed("2");
            } else {
                defaultRoom.setBed("3");
            }

            return "📄 اتاق " + roomNumber + " هنوز مقداردهی نشده، اطلاعات پیش‌فرض:\n" + RoomToStr(defaultRoom);
        }

        return "❌ اطلاعاتی برای این اتاق پیدا نشد.";
    }

    public String showAllRoomsStatus() {
        String[] rooms = fn.getArrayFromFile();
        Room1[] allRooms = new Room1[10]; // از 0 تا 9 برای اتاق 1 تا 10

        for (String room : rooms) {
            if (room.trim().isEmpty()) continue;

            Room1 r = Str2Room(room);
            int index = r.getRoomNumber() - 1;
            if (index >= 0 && index < 10) {
                allRooms[index] = r;
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= 10; i++) {
            Room1 r = allRooms[i - 1];

            if (r == null) {
                // اتاق مقداردهی نشده، پیش‌فرض
                r = new Room1();
                r.setRoomNumber(i);
                r.setStatus("موجود");
                r.setGuestsName("");
                if (i <= 5) {
                    r.setBed("4");
                } else if (i <= 7) {
                    r.setBed("2");
                } else {
                    r.setBed("3");
                }
            }

            if (!r.getStatus().equals("موجود")) {
                result.append("رزرو - اتاق ")
                      .append(r.getRoomNumber())
                      .append("\n");
            } else {
                result.append(r.getBed())
                      .append(" - موجود - اتاق ")
                      .append(r.getRoomNumber())
                      .append("\n");
            }
        }

        return result.toString().trim();
    }

    // ⚡ متد مقداردهی اولیه فایل اتاق‌ها
    public void initializeRooms() {
        String[] rooms = fn.getArrayFromFile();
        if (rooms.length > 0 && !rooms[0].trim().isEmpty()) {
            return; // اگر فایل خالی نیست، مقداردهی نکن
        }

        List<Room1> defaultRooms = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Room1 r = new Room1();
            r.setRoomNumber(i);
            r.setStatus("Available");
            r.setGuestsName(""); // مهمان ندارد

            if (i <= 5) {
                r.setBed("4");
            } else if (i <= 7) {
                r.setBed("2");
            } else {
                r.setBed("3");
            }

            defaultRooms.add(r);
        }

        StringBuilder sb = new StringBuilder();
        for (Room1 r : defaultRooms) {
            sb.append(RoomToStr(r)).append("\n");
        }

        fn.setIntoFile(sb.toString().trim());
    }
}
