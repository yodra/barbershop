package es.leanmind.barbershop.integration;

import es.leanmind.barbershop.Configuration;
import es.leanmind.barbershop.helpers.IntegrationTests;
import es.leanmind.barbershop.domain.Credentials;
import es.leanmind.barbershop.domain.EstablishmentDTO;
import es.leanmind.barbershop.domain.EstablishmentService;
import es.leanmind.barbershop.helpers.TestFactory;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EstablishmentServiceShould extends IntegrationTests {

    @Test
    public void create_an_organization_with_one_owner_allowed() throws SQLException {
        EstablishmentService establishmentService = TestFactory.establishmentService(Configuration.connectionTestDatabase);
        Credentials webCredentials = new Credentials("web_username", "web_password");
        String ftpHomeFolder = "ftp_home_folder";
        establishmentService.create("Parrot Bar", webCredentials);

        boolean isOwnerAllowed = establishmentService.isOwnerAllowed(webCredentials.username, webCredentials.password);

        assertThat(isOwnerAllowed).isTrue();
    }

    @Test
    public void retrieve_all_establishments_for_an_owner_given_its_username_and_password() throws SQLException {
        EstablishmentService establishmentService = TestFactory.establishmentService(Configuration.connectionTestDatabase);
        String firstEstablishmentName = "First Establishment";
        String secondEstablishmentName = "Second Establishment";
        Credentials webCredentials = new Credentials("web_username", "web_password");
        establishmentService.create(firstEstablishmentName, webCredentials);
        establishmentService.create(secondEstablishmentName, webCredentials);

        List<EstablishmentDTO> establishments = establishmentService.retrieveEstablishmentsFor(webCredentials.username);

        assertThat(establishments.size()).isEqualTo(2);
        assertThat(establishments.get(0).name).isEqualTo(firstEstablishmentName);
        assertThat(establishments.get(1).name).isEqualTo(secondEstablishmentName);
    }
}
