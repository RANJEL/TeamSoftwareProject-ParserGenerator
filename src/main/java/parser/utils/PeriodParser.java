package parser.utils;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 20.01.2017.
 */
public class PeriodParser {

    private final long mPeriod;
    private final TimeUnit mTimeUnit;

    private PeriodParser(long period, TimeUnit timeUnit) {
        mPeriod = period;
        mTimeUnit = timeUnit;
    }

    public static PeriodParser parse(@Nonnull String argument) throws IllegalArgumentException {
        try {
            long period;
            TimeUnit timeUnit;

            period = Long.parseLong(argument.substring(0, argument.length() - 1));
            if (period < 1) {
                throw new IllegalArgumentException("Period is smaller than 1.");
            }
            switch (argument.substring(argument.length() - 1)) {
                case "s":
                case "S":
                    timeUnit = TimeUnit.SECONDS;
                    break;
                case "m":
                case "M":
                    timeUnit = TimeUnit.MINUTES;
                    break;
                case "h":
                case "H":
                    timeUnit = TimeUnit.HOURS;
                    break;
                case "d":
                case "D":
                    timeUnit = TimeUnit.DAYS;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported type of time unit.");
            }
            return new PeriodParser(period, timeUnit);
        } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalArgumentException("Argument is empty.");
        }
    }

    public long getPeriod() {
        return mPeriod;
    }

    public TimeUnit getTimeUnit() {
        return mTimeUnit;
    }
}
