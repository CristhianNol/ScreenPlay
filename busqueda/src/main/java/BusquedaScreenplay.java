import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.log4j.BasicConfigurator;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class BusquedaScreenplay {
    public static class AbrirPagina implements Task {
        private final String url;

        public AbrirPagina(String url) {
            this.url = url;
        }

        @Override
        public <T extends Actor> void performAs(T actor) {
            actor.attemptsTo(Open.url(url));
        }

        public static Performable en(String url) {
            return instrumented(AbrirPagina.class, url);
        }
    }

    public static class IngresarPalabraClave implements Task {
        private final String palabraClave;

        public IngresarPalabraClave(String palabraClave) {
            this.palabraClave = palabraClave;
        }

        @Override
        public <T extends Actor> void performAs(T actor) {
            actor.attemptsTo(
                    Enter.theValue(palabraClave).into(CajaTexto.BUSQUEDA),
                    Click.on(Boton.BUSCAR)
            );
        }

        public static Performable conPalabraClave(String palabraClave) {
            return instrumented(IngresarPalabraClave.class, palabraClave);
        }
    }

    public static class HacerClickEnPrimeraOpcion implements Task {
        @Override
        public <T extends Actor> void performAs(T actor) {
            actor.attemptsTo(
                    Click.on(OpcionBusqueda.PRIMERA_OPCION)
            );
        }

        public static Performable enResultados() {
            return instrumented(HacerClickEnPrimeraOpcion.class);
        }
    }

    public static class CajaTexto {
        public static final Target BUSQUEDA = Target.the("campo de búsqueda")
                .located(By.name("q"));
    }

    public static class Boton {
        public static final Target BUSCAR = Target.the("botón buscar")
                .located(By.name("btnK"));
    }

    public static class OpcionBusqueda {
        public static final Target PRIMERA_OPCION = Target.the("primera opción de búsqueda")
                .located(By.cssSelector("div#search a"));
    }

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        Actor usuario = Actor.named("Usuario");

        usuario.attemptsTo(
                AbrirPagina.en("https://www.google.com"),
                IngresarPalabraClave.conPalabraClave("falabella"),
                HacerClickEnPrimeraOpcion.enResultados()
        );
    }
}
