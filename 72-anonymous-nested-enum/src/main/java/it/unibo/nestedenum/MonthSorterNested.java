package it.unibo.nestedenum;

import java.util.Comparator;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    
    /**
     * Costants representing the number of days in a month.
     */
    public static final int MONTH_31 = 31;
    public static final int MONTH_30 = 30;
    public static final int MONTH_28 = 28;

    /**
     * Enumeration representing the months in a non-leap year.
     */
    public enum Month {
        GENUARY(MONTH_31), FEBRUARY(MONTH_28), MARCH(MONTH_31),
        APRIL(MONTH_30), MAY(MONTH_31), JUNE(MONTH_30),
        JULY(MONTH_31), AUGUST(MONTH_31), SEPTEMBER(MONTH_30),
        OCTOBER(MONTH_31), NOVEMBER(MONTH_30), DECEMBER(MONTH_31);

        private final int days;

        /**
         * Messages used for the exceptions.
         */
        public static final String MSG_GREATER = "Too many months found";
        public static final String MSG_ZERO = "No months found";

        /**
         * Construct a Month enum with the specified number of days.
         * 
         * @param days The number of days in a month.
         */
        private Month(final int days) {
            this.days = days;
        }

        /**
         * Given an input string, returns the month which best represents it.
         * Throws an exception if the input string matches with no months or more than one month. 
         * 
         * @param inputMonth The input string.
         * @return The month found.
         * @throws IllegalArgumentException if the input string matches with no months or more than one month. 
         */
        public static Month fromString(final String inputMonth) {
            Month searchMonth = null;
            int numberOfMonthFound = 0;
    
            for (final var elem : values()) {
                if (elem.name().startsWith(inputMonth.toUpperCase())) {
                    searchMonth = elem;
                    numberOfMonthFound++;
                }
            }
    
            if (numberOfMonthFound > 1) {
                throw new java.lang.IllegalArgumentException(MSG_GREATER);
            }
            if (numberOfMonthFound == 0) {
                throw new java.lang.IllegalArgumentException(MSG_ZERO);
            }

            return searchMonth;
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }

    /**
     * Comparator static nested class which sort months by the number of days.
     */
    public static class SortByDate implements Comparator<String> {
        
        @Override
        public int compare(String o1, String o2) {
            return Integer.compare(Month.fromString(o1).days, Month.fromString(o2).days);
        }
        
    }
    
    /**
     * Comparator static nested class which sort months by the order in the calendar.
     */
    public static class SortByMonthOrder implements Comparator<String> {
        
        @Override
        public int compare(String o1, String o2) {
            return Month.fromString(o1).compareTo(Month.fromString(o2));
        }
        
    }
}
