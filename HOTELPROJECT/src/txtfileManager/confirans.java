package manager;

import common.EventType;
import common.ConferenceRoom;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static final String LOG_FILE = "events_log.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ConferenceRoom grandHall = new ConferenceRoom("R001", "سالن بزرگ", 250);

        System.out.println("📝 سیستم رزرو سالن کنفرانس");
        System.out.println("----------------------------");
        System.out.println("لطفاً اطلاعات رویداد را وارد کنید:");

        System.out.println("\n🔹 نوع رویداد (انتخاب کنید):");
        System.out.println("1. سمینارهای فناوری و IT");
        System.out.println("2. کنفرانسهای مهندسی و معماری");
        System.out.println("3. نشستهای پژوهشی و دانشگاهی");
        System.out.print("➡️ انتخاب شما (1-3): ");
        int eventTypeChoice = scanner.nextInt();
        scanner.nextLine();

        EventType eventType = switch (eventTypeChoice) {
            case 1 -> EventType.TECH_SEMINAR;
            case 2 -> EventType.ENG_CONFERENCE;
            case 3 -> EventType.ACADEMIC_MEETING;
            default -> {
                System.out.println("⚠️ انتخاب نامعتبر! به‌صورت پیش‌فرض سمینار فناوری انتخاب شد.");
                yield EventType.TECH_SEMINAR;
            }
        };

        System.out.print("\n📅 تاریخ رویداد (مثال: 1402/10/15): ");
        String date = scanner.nextLine();

        System.out.print("⏰ ساعت شروع (مثال: 14:00): ");
        String startTime = scanner.nextLine();

        System.out.print("⏰ ساعت پایان (مثال: 17:00): ");
        String endTime = scanner.nextLine();

        System.out.print("👥 تعداد شرکت‌کنندگان: ");
        int attendees = scanner.nextInt();
        scanner.nextLine();

        System.out.print("\n🔹 کد رویداد: ");
        String eventId = scanner.nextLine();

        ConferenceEvent userEvent = new ConferenceEvent(
                eventId, eventType, date, startTime, endTime, grandHall, attendees
        );

        System.out.print("\n🎤 نام سخنران (اگر ندارد Enter بزنید): ");
        String speaker = scanner.nextLine();
        if (!speaker.isEmpty()) {
            userEvent.addSpeaker(speaker);
        }

        if (userEvent.reserve()) {
            userEvent.displayEventInfo();
            saveEventToFile(userEvent);
        }

        scanner.close();
    }

    private static void saveEventToFile(ConferenceEvent event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(event.toFileString());
            System.out.println("\n💾 اطلاعات رویداد با موفقیت در فایل '" + LOG_FILE + "' ذخیره شد.");
        } catch (IOException e) {
            System.err.println("❌ خطا در ذخیره‌سازی فایل: " + e.getMessage());
        }
    }
}