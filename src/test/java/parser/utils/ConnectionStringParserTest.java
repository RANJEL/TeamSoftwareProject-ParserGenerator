package parser.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link ConnectionStringParser}
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 08.11.2016.
 */
public class ConnectionStringParserTest {

    private static final String USER = "sp_16_valenta";
    private static final String PASSWORD = "SkoL.!";
    private static final String HOST = "oracle.fit.cvut.cz";
    private static final String PORT = "1521";
    private static final String DATABASE_NAME = "ORACLE";

    @Test
    public void parse() {
        ConnectionStringParser connectionStringParser = ConnectionStringParser.parse(
                USER + "/"
                        + PASSWORD + "@"
                        + HOST + ":"
                        + PORT + "/"
                        + DATABASE_NAME
        );

        assertEquals(connectionStringParser.getUsername(), USER);
        assertEquals(connectionStringParser.getPassword(), PASSWORD);
        assertEquals(connectionStringParser.getHost(), HOST);
        assertEquals(connectionStringParser.getPort(), PORT);
        assertEquals(connectionStringParser.getDatabaseName(), DATABASE_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkStructure() {
        ConnectionStringParser.parse(
                USER + "/"
                        + PASSWORD + "@"
                        + HOST + "/"
                        + PORT + "/"
                        + DATABASE_NAME
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkStructure2() {
        ConnectionStringParser.parse(
                USER + "/"
                        + PASSWORD + "@"
                        + HOST + ":"
                        + PORT
        );
    }
}