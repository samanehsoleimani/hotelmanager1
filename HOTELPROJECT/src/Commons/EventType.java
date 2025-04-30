package common;

public enum EventType {
    TECH_SEMINAR("سمینارهای فناوری و IT", "IT & Technology Seminars"),
    ENG_CONFERENCE("کنفرانسهای مهندسی و معماری", "Engineering & Architecture Conferences"),
    ACADEMIC_MEETING("نشستهای پژوهشی و دانشگاهی", "Academic Research Conferences");

    private final String persianTitle;
    private final String englishTitle;

    EventType(String persianTitle, String englishTitle) {
        this.persianTitle = persianTitle;
        this.englishTitle = englishTitle;
    }

    public String getPersianTitle() {
        return persianTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }
}