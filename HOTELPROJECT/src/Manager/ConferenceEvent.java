package manager;

import common.EventType;
import common.ConferenceRoom;
import java.util.ArrayList;
import java.util.List;

public class ConferenceEvent {
    private String eventId;
    private EventType eventType;
    private String date;
    private String startTime;
    private String endTime;
    private ConferenceRoom room;
    private List<String> speakers = new ArrayList<>();
    private int expectedAttendees;

    public ConferenceEvent(String eventId, EventType eventType, String date,
                           String startTime, String endTime, ConferenceRoom room,
                           int expectedAttendees) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.expectedAttendees = expectedAttendees;
    }

    public boolean reserve() {
        return room.reserveRoom(expectedAttendees);
    }

    public void displayEventInfo() {
        System.out.println("\n📌 اطلاعات رویداد:");
        System.out.println("🏷️ عنوان: " + eventType.getPersianTitle());
        System.out.println("🆔 کد رویداد: " + eventId);
        System.out.println("🏢 سالن: " + room.getRoomInfo());
        System.out.println("📅 تاریخ: " + date);
        System.out.println("⏰ زمان: " + startTime + " - " + endTime);
        System.out.println("👥 تعداد شرکت‌کنندگان: " + expectedAttendees + " نفر");
        System.out.println("🎤 سخنرانان: " + (speakers.isEmpty() ? "تعیین نشده" : String.join("، ", speakers)));
    }

    public void addSpeaker(String speakerName) {
        speakers.add(speakerName);
    }

    public String toFileString() {
        return String.format(
                """
                📌 رویداد جدید:
                🏷️ عنوان: %s
                🆔 کد رویداد: %s
                🏢 سالن: %s
                📅 تاریخ: %s
                ⏰ زمان: %s - %s
                👥 شرکت‌کنندگان: %d نفر
                🎤 سخنرانان: %s
                --------------------------
                """,
                eventType.getPersianTitle(),
                eventId,
                room.getRoomInfo(),
                date,
                startTime,
                endTime,
                expectedAttendees,
                speakers.isEmpty() ? "ندارد" : String.join("، ", speakers)
        );
    }
}