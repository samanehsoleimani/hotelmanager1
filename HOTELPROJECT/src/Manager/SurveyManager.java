package Manager;

import Commons.commons;
import Commons.Survey;
import txtfilemanager.txtfilemanager;

import java.util.ArrayList;
import java.util.List;

public class SurveyManager {
    private txtfilemanager fn;

    // Constructor
    public SurveyManager() {
        fn = new txtfilemanager("SURVEY.txt");
    }

    // ✍️ ذخیره نظر جدید
    public void saveSurvey(Survey s) {
        String data = SurveyToStr(s);
        fn.AppendRow(data); // ذخیره نظر در فایل
    }

    // 📋 دریافت همه نظرات
    public List<Survey> getAllSurveys() {
        String[] lines = fn.getArrayFromFile(); // دریافت خطوط از فایل
        List<Survey> list = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().isEmpty()) continue; // نادیده گرفتن خطوط خالی
            Survey survey = Str2Survey(line);
            if (survey != null) {
                list.add(survey); // فقط اگر تبدیل موفق بود، آن را اضافه کنید
            }
        }

        return list;
    }

    // 🔍 دریافت نظر مشتری خاص
    public String getSurveyInfo(String customerName) {
        String[] lines = fn.getArrayFromFile(); // دریافت خطوط از فایل
        System.out.println("File Content: ");
        for (int i = 0; i < lines.length; i++) {
            System.out.println(lines[i]);  // نمایش هر خط از فایل
        }

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().isEmpty()) continue; // نادیده گرفتن خطوط خالی

            Survey s = Str2Survey(line); // تبدیل هر خط به شیء Survey
            if (s != null && s.getCustomerName().equalsIgnoreCase(customerName)) {
                return "🔎 نظر برای " + customerName + ":\n" + SurveyToStr(s);
            }
        }

        return "❌ نظری از این مشتری پیدا نشد."; // اگر هیچ نظر پیدا نشد
    }

    // ❌ حذف نظر مشتری
    public void deleteSurvey(String customerName) {
        String[] lines = fn.getArrayFromFile();
        StringBuilder newData = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.trim().isEmpty()) continue;

            Survey s = Str2Survey(line);
            if (s != null && !s.getCustomerName().equalsIgnoreCase(customerName)) {
                newData.append(line).append("\n");
            }
        }

        fn.setIntoFile(newData.toString().trim()); // آپدیت فایل با اطلاعات جدید
    }

    // 🔄 تبدیل رشته به شی Survey
    public Survey Str2Survey(String line) {
        String[] parts = line.split("\\|");  // استفاده از جداکننده '|'

        if (parts.length != 4) {  // مطمئن شو که داده‌ها به درستی تفکیک شده‌اند
            System.out.println("Error parsing line: " + line);  // چاپ ارور برای مشاهده خطای ورودی
            return null;  // بازگرداندن null در صورت خطا
        }

        try {
            Survey survey = new Survey();
            survey.setCustomerName(parts[0].trim());
            survey.setRoomNumber(Integer.parseInt(parts[1].trim()));  // بررسی درست بودن داده‌ها
            survey.setRating(Integer.parseInt(parts[2].trim()));
            survey.setComment(parts[3].trim());
            return survey;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing line: " + line);  // چاپ ارور در صورت وجود مشکل در تبدیل
            return null;
        }
    }

    // 🔄 تبدیل شی Survey به رشته برای ذخیره در فایل
    private String SurveyToStr(Survey s) {
        return s.getCustomerName() + commons.Splitter +
               s.getRoomNumber() + commons.Splitter +
               s.getRating() + commons.Splitter +
               s.getComment();
    }
}
