package es.leanmind.barbershop.helpers;

import es.leanmind.barbershop.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;

public class IntegrationTests {

    @BeforeClass
    public static void createDatabase() throws SQLException {
        try {
            System.out.println("======================== Creating database =============");
            Sql2o sql2o = new Sql2o(Configuration.connectionBaseUrl, Configuration.dbUser, Configuration.dbPassword);
            Connection connection = sql2o.open();
            connection.getJdbcConnection()
                    .createStatement()
                    .executeUpdate("DROP DATABASE IF EXISTS " + Configuration.testDb);
            // IMPORTANT: sequences are not copied when creating a database from template,
            // so the primary keys will conflict if the testDb is not empty
            connection.getJdbcConnection()
                    .createStatement()
                    .executeUpdate("CREATE DATABASE " + Configuration.testDb + " with TEMPLATE " + Configuration.productionDb + " OWNER " + Configuration.dbUser);
            System.out.println("======================== Creation Success =============");
        }
        catch(Exception ex){
            System.err.println("************************* Error creating database:" + ex.getMessage());
        }
    }

    @AfterClass
    public static void dropDatabase() throws SQLException {
        try {
            System.out.println("======================= Dropping database ==============");
            Sql2o sql2o = new Sql2o(Configuration.connectionBaseUrl, Configuration.dbUser, Configuration.dbPassword);
            Connection connection = sql2o.open();
            connection.getJdbcConnection()
                    .createStatement()
                    .executeUpdate("DROP DATABASE IF EXISTS " + Configuration.testDb);
            System.out.println("======================= Drop Success ==============");
        }
        catch(Exception ex){
            System.err.println("************************* Error dropping database:" + ex.getMessage());
        }
    }

    @Before
    public void setUpCascade() throws Exception {
        flushDatabase();
    }

    @After
    public void tearDownCascade() throws Exception {
        flushDatabase();
    }

    private void flushDatabase() throws SQLException {
        System.out.println("------------------------- Flushing database");
        Sql2o sql2o = new Sql2o(Configuration.connectionBaseUrl + Configuration.testDb, Configuration.dbUser, Configuration.dbPassword);
        Connection connection = sql2o.open();
        connection.getJdbcConnection().setAutoCommit(false);
        connection.createQuery(
                "DELETE FROM establishments")
                .executeUpdate();
        connection.createQuery(
                "DELETE FROM establishments_owners")
                .executeUpdate();
        connection.createQuery(
                "DELETE FROM owners")
                .executeUpdate();
        connection.commit();
        System.out.println("------------------------- Flushing success");
    }
}
