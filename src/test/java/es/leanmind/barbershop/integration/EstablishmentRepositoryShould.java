package es.leanmind.barbershop.integration;

import es.leanmind.barbershop.Configuration;
import es.leanmind.barbershop.domain.Owners;
import es.leanmind.barbershop.helpers.IntegrationTests;
import es.leanmind.barbershop.domain.Establishment;
import es.leanmind.barbershop.domain.EstablishmentDTO;
import es.leanmind.barbershop.helpers.Any;
import es.leanmind.barbershop.infrastructure.PostgresEstablishmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EstablishmentRepositoryShould extends IntegrationTests {

    private PostgresEstablishmentRepository establishmentRepository;
    private Sql2o sql2o;

    @Before
    public void given_a_repository_and_a_database() {
        establishmentRepository = new PostgresEstablishmentRepository(Configuration.connectionTestDatabase);
        sql2o = new Sql2o(Configuration.connectionBaseUrl + Configuration.testDb, Configuration.dbUser, Configuration.dbPassword);
    }

    @Test
    public void finds_user_by_credentials() {
        String hash = "someKindOfHash124asdfavas3rasd";
        String username = "test";
        try(Connection connection = sql2o.open()) {
            connection.createQuery(
                    "INSERT INTO owners(username, passwordhash) values (:username, :passwordhash)")
                    .addParameter("username", username)
                    .addParameter("passwordhash", hash)
                    .executeUpdate();
        }

        assertThat(establishmentRepository.getOwnerHashedPassword(username)).isEqualTo(hash);
    }

    @Test
    public void not_create_a_web_user_if_it_already_exists() throws SQLException {
        String username = "Parroty";
        Owners owners = new Owners(username, "Money");
        Establishment establishmentOne = new Establishment(Any.string(), owners);
        Establishment establishmentTwo = new Establishment(Any.string(),owners);

        establishmentRepository.create(establishmentOne);
        establishmentRepository.create(establishmentTwo);

        List<String> ownernamesInDatabase = retrieveownernames();
        assertThat(ownernamesInDatabase.size()).isEqualTo(1);
        assertThat(ownernamesInDatabase.get(0)).isEqualTo(username);
    }

    @Test
    public void find_all_establishments_for_a_web_user() throws SQLException {
        String establishmentOneName = "Parrot Bar";
        String establishmentTwoName = "Sloth Bar";
        Owners owners = new Owners("Parroty", "Money");
        Establishment establishmentOne = new Establishment(establishmentOneName, owners);
        Establishment establishmentTwo = new Establishment(establishmentTwoName, owners);
        establishmentRepository.create(establishmentOne);
        establishmentRepository.create(establishmentTwo);

        List<EstablishmentDTO> retrievedEstablishments = establishmentRepository.retrieveEstablishmentsFor(owners.getUsername());

        assertThat(retrievedEstablishments.size()).isEqualTo(2);
        assertThat(retrievedEstablishments.get(0).name).isEqualTo(establishmentOneName);
        assertThat(retrievedEstablishments.get(1).name).isEqualTo(establishmentTwoName);
    }

    private Owners anyowner() {
        return new Owners(Any.string(), Any.string());
    }

    private List<String> retrieveownernames() {
        try(Connection connection = sql2o.open()) {
            return connection.createQuery("SELECT username FROM owners")
                    .executeScalarList(String.class);
        }
    }
}
