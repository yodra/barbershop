package es.leanmind.barbershop.infrastructure;

import es.leanmind.barbershop.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class PostgresEstablishmentRepository implements EstablishmentRepository {

    private Sql2o sql2o;

    public PostgresEstablishmentRepository(String connectionUrl) {
        sql2o = new Sql2o(connectionUrl, "barbershop", "12345");
    }

    @Override
    public void create(Establishment establishment) throws SQLException {
        System.out.println("--- Creating establishment");
        String query = "INSERT INTO establishments(id, name) VALUES (DEFAULT, :name)";

        Connection connection = sql2o.open();
        connection.getJdbcConnection().setAutoCommit(false);
        Integer establishmentId = (Integer) connection.createQuery(query, true)
                .addParameter("name", establishment.name())
                .executeUpdate()
                .getKey();
        createAndRelateMany(establishment.owners(), establishmentId, connection);
        connection.commit();
        System.out.println("--- Establishment created:" + establishmentId);
    }

    private void createAndRelateMany(Set<Owners> owners, Integer establishmentId, Connection connection) throws SQLException {
        String query = "INSERT INTO owners(username, passwordhash) " +
                       "VALUES (:username, :passwordhash)";

        for (Owners owner : owners) {
            if (ownerDoesNotExist(owner.getUsername(), connection)) {
                Integer ownerId = (Integer) connection.createQuery(query, true)
                        .addParameter("username", owner.getUsername())
                        .addParameter("passwordhash", owner.getPasswordHash())
                        .executeUpdate()
                        .getKey();
                createManyToManyRelation(ownerId, establishmentId);
            } else {
                createManyToManyRelation(retrieveIdFor(owner.getUsername()), establishmentId);
            }
        }
    }

    private void createManyToManyRelation(Integer ownerId, Integer establishmentId) throws SQLException {
        String query = "INSERT INTO establishments_owners(establishment, owner) " +
                       "VALUES (:establishmentId, :ownerId)";

        Connection connection = sql2o.open();
        connection.getJdbcConnection().setAutoCommit(false);
        connection.createQuery(query)
                .addParameter("ownerId", ownerId)
                .addParameter("establishmentId", establishmentId)
                .executeUpdate();
        connection.commit();
    }

    private boolean ownerDoesNotExist(String username, Connection connection) {
        return connection.createQuery("SELECT username FROM owners WHERE username = :username")
                .addParameter("username", username)
                .executeScalarList(String.class).size() == 0;
    }

    @Override
    public List<EstablishmentDTO> retrieveEstablishmentsFor(String username) {
        Integer ownerId = retrieveIdFor(username);
        String query = "SELECT establishments.id, establishments.name " +
                       "FROM establishments " +
                       "LEFT JOIN establishments_owners ON establishments.id = establishments_owners.establishment " +
                       "WHERE establishments_owners.owner = :ownerId";

        List<Map<String, Object>> queryResults;
        Connection connection = sql2o.open();
        queryResults = connection.createQuery(query)
                .addParameter("ownerId", ownerId)
                .executeAndFetchTable()
                .asList();

        return queryResults.stream()
                .map(this::toEstablishmentDTO)
                .collect(toList());
    }

    private EstablishmentDTO toEstablishmentDTO(Map<String, Object> result) {
        return new EstablishmentDTO((Integer) result.get("id"), (String) result.get("name"));
    }

    private Integer retrieveIdFor(String username) {
        Connection connection = sql2o.open();
        return connection.createQuery("SELECT id FROM owners WHERE username = :username")
                .addParameter("username", username)
                .executeScalarList(Integer.class).get(0);
    }

    @Override
    public String getOwnerHashedPassword(String username) {
        return hashedPasswordFor(username, "owners");
    }

    private String hashedPasswordFor(String username, final String usersTable) {
        String query = "SELECT passwordhash " +
                "FROM " + usersTable + " " +
                "WHERE username = :username";

        List<String> matchingHashedPasswords;
        Connection connection = sql2o.open();
        matchingHashedPasswords = connection.createQuery(query)
                .addParameter("username", username)
                .executeScalarList(String.class);

        if (matchingHashedPasswords.size() != 1){
            throw new UsernameNotFoundException(username);
        }

        return matchingHashedPasswords.get(0);
    }
}