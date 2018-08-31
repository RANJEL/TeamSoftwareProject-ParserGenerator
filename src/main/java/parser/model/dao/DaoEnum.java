package parser.model.dao;

import parser.model.dao.daoImplementations.MysqlDao;
import parser.model.dao.daoImplementations.OracleDao;
import parser.model.dao.daoImplementations.PostgresqlDao;

import java.sql.Driver;

/**
 * TODO class_description
 * Created by Filip Šmíd [smidfil3@fit.cvut.cz] on 05.11.2016.
 */
public enum DaoEnum {
    ORACLE(DatabaseName.ORACLE, OracleDao.class, "oracle.jdbc.driver.OracleDriver"),
    MYSQL(DatabaseName.MYSQL, MysqlDao.class, "com.mysql.cj.jdbc.Driver"),
    POSTGRESQL(DatabaseName.POSTGRESQL, PostgresqlDao.class, "org.postgresql.Driver");

    private final DatabaseName mDbName;
    private final Class<? extends AbstractDao> mDaoImplClass;
    private final String mDbDriverClass;

    DaoEnum(DatabaseName dbName, Class<? extends AbstractDao> daoImplClass, String driverClass) {
        mDbName = dbName;
        mDaoImplClass = daoImplClass;
        mDbDriverClass = driverClass;
    }

    public DatabaseName getDbName() {
        return mDbName;
    }

    public Class<? extends AbstractDao> getDaoImplClass() {
        return mDaoImplClass;
    }

    public String getDbDriverClass() {
        return mDbDriverClass;
    }

    public enum DatabaseName {
        ORACLE("Oracle"),
        MYSQL("MySQL"),
        POSTGRESQL("PostgreSQL");

        private String mName;

        DatabaseName(String name) {
            mName = name;
        }

        public String getName() {
            return mName;
        }
    }
}
