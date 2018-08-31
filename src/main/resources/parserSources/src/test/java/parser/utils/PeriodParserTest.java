package parser.utils;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link PeriodParser}
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 20.01.2017.
 */
public class PeriodParserTest {

    @Test
    public void parse() {
        PeriodParser periodParser = PeriodParser.parse("10d");
        assertEquals(periodParser.getPeriod(), 10L);
        assertEquals(periodParser.getTimeUnit(), TimeUnit.DAYS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkTimeUnit() {
        PeriodParser.parse("5T");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkN() {
        PeriodParser.parse("0s");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkN2() {
        PeriodParser.parse("-5s");
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkArgument() {
        PeriodParser.parse("d50");
    }
}