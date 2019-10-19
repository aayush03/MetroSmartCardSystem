package org.aayush.strategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class FareStrategyFactory {

    private static final FareStrategy weekdayFareStrategy = new WeekdayFareStrategy();
    private static final FareStrategy weekendFareStrategy = new WeekendFareStrategy();

    public static FareStrategy getFareStrategy(LocalDateTime localDateTime) {
        if (localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY)
            return weekendFareStrategy;

        return weekdayFareStrategy;
    }

}
