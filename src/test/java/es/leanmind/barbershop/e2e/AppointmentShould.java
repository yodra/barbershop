package es.leanmind.barbershop.e2e;

import es.leanmind.barbershop.Configuration;
import es.leanmind.barbershop.domain.Credentials;
import es.leanmind.barbershop.domain.EstablishmentService;
import es.leanmind.barbershop.helpers.IntegrationTests;
import es.leanmind.barbershop.helpers.Properties;
import es.leanmind.barbershop.helpers.TestFactory;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {"" +
                "spring.datasource.url=jdbc:postgresql://localhost:5432/" + Configuration.testDb + "?user=" + Configuration.dbUser + "&password=" + Configuration.dbPassword,
                "server.port=" + Properties.WEB_SERVER_PORT
        })
@RunWith(SpringJUnit4ClassRunner.class)
public class AppointmentShould extends IntegrationTests {

    private WebDriver driver;
    private EstablishmentService establishmentService = TestFactory.establishmentService(Configuration.connectionTestDatabase);

    private WebDriver browser() {
        if (driver == null) {
            startChrome();
            //startFirefox();
        }
        return driver;
    }

    private void startFirefox() {
        driver = new FirefoxDriver();
    }

    private void startChrome() {
        System.setProperty("webdriver.chrome.driver", Configuration.chromeDriverPath);
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

    @Test
    public void be_made_by_the_customer() throws IOException, SQLException {
        createEstablishment();
        doWebLogin(Configuration.ownername, Configuration.webPassword);

        boolean confirmated = createAppointment(new Date());

        assertThat(confirmated).isTrue();

        //seleccionar fecha y hora
        //confirmar

    }

    private Boolean createAppointment(Date confirmated) {
        return true;
    }

    private void doWebLogin(String username, String password) {
        browser().get(Configuration.webUrl + Configuration.loginUrl);
        browser().findElement(By.name("username")).sendKeys(username);
        browser().findElement(By.name("password")).sendKeys(password);
        browser().findElement(By.name("submitLogin")).click();
        //TODO: assert that response status code is 200
    }

    private void createEstablishment() throws SQLException {
        Credentials webCredentials = new Credentials(Configuration.ownername, Configuration.webPassword);
        establishmentService.create(Configuration.establishmentName, webCredentials);
    }

    private void waitForElementWithId(String className, WebDriver browser) {
        WebDriverWait waiter = new WebDriverWait(browser, 5);
        waiter.until(presenceOfElementLocated(By.id(className)));
    }
}
