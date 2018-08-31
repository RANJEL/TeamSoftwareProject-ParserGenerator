package parser.model.dao.daoImplementations;

/**
 * Test for {@link MysqlDao}
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 30.10.2016.
 */
public class MysqlDaoTest {

    private static final String TABLE_NAME = "test_table";
    private static final String COLUMN_NAME = "test_column";
    private static final String VALUE = "string value";

    private static final String SCRIPT = "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES (\'" + VALUE + "\');";

//    @Test
//    public void createTableScript() {
//        // TODO: 30.10.2016
//    }
//
//    @Test
//    public void insertScript() {
//        try {
//            Method insertScriptMethod = AbstractDao.class.getDeclaredMethod("insertScript", InsertData.class);
//            insertScriptMethod.setAccessible(true);
//            String insertScript = (String) insertScriptMethod.invoke(mFactory, mData);
//            Assert.assertEquals(SCRIPT, insertScript);
//        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }
}