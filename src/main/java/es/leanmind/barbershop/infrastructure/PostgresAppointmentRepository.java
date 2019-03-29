package es.leanmind.barbershop.infrastructure;

import es.leanmind.barbershop.domain.AppointmentRepository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.SQLException;
import java.sql.Timestamp;

public class PostgresAppointmentRepository implements AppointmentRepository {

    private Sql2o sql2o;

    public PostgresAppointmentRepository(String connectionUrl) {
        sql2o = new Sql2o(connectionUrl, "barbershop", "12345");
    }


    @Override
    public void create(String user, Timestamp appointmentTime) throws SQLException {

    }

    @Override
    public boolean haveAppointment(String username) throws SQLException {
        String query =  "SELECT COUNT(owner_id)" +
                        " FROM appointments" +
                        " WHERE owner_id =   (SELECT id" +
                                            " FROM owners" +
                                            " WHERE username = :username)";
        Connection connection = sql2o.open();
        Integer queryResult = connection.createQuery(query)
            .addParameter("username", username)
            .executeScalar(Integer.class);

        return queryResult > 0;
    }
}
