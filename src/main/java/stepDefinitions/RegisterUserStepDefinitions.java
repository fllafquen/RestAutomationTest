package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import models.users.RegisterUserInfo;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.annotations.Steps;
import questions.ResponseCode;
import tasks.RegisterUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class RegisterUserStepDefinitions {

    public String restApiUrl = "http://localhost:5000/api";

    @Steps
    Actor francisco;

    @Given("^Francisco es un cliente que quiere poder administrar sus productos bancarios$")
    public void francisco_es_un_cliente_que_quiere_poder_administrar_sus_productos_bancarios() {

        francisco = Actor.named("Francisco the student")
                .whoCan(CallAnApi.at(restApiUrl));
    }

    @When("^el envia la información requerida para el registro$")
    public void el_envia_la_información_requerida_para_el_registro() {

        RegisterUserInfo registerUserInfo = new RegisterUserInfo();

        registerUserInfo.setName("Emma");
        registerUserInfo.setJob("leader");
        registerUserInfo.setEmail("emma.wong@reqres.in");
        registerUserInfo.setPassword("serenity");

        francisco.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)
        );
    }

    @Then("^el debe obtener una cuenta virtual para poder ingresar cuando lo requiera$")
    public void el_debe_obtener_una_cuenta_virtual_para_poder_ingresar_cuando_lo_requiera() {

        francisco.should(
                seeThat("el codigo de respuesta",
                        new ResponseCode(),
                        equalTo(200))
        );
    }

}
