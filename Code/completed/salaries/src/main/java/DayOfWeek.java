public enum DayOfWeek {
    SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7);

    private final int dayOfWeekIndex;

    DayOfWeek(int dayOfWeekIndex) {
        this.dayOfWeekIndex = dayOfWeekIndex;
    }

    public int getDayOfWeekIndex() {
        return dayOfWeekIndex;
    }

    public static DayOfWeek fromIndex(int dayOfWeekIndex) {
        return values()[dayOfWeekIndex - 1];
    }

    public String toString() {
        return toTitleCase(name());
    }

    public static String toTitleCase(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
