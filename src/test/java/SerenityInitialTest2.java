import models.users.Datum;
import models.users.RegisterUserInfo;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import questions.GetUsersQuestion;
import questions.ResponseCode;
import tasks.GetUsers;
import tasks.RegisterUser;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(SerenityRunner.class)
public class SerenityInitialTest2 {

    public static final String restApiUrl = "http://localhost:5000/api";

    @Test
    public void initialTest(){
        Actor francisco = Actor.named("Francisco the student")
                .whoCan(CallAnApi.at(restApiUrl));

        francisco.attemptsTo(
                GetUsers.fromPage(1)
        );

        francisco.should(
                seeThat("el codigo de respuesta",
                        new ResponseCode(),
                        equalTo(200))
        );

      Datum user = new GetUsersQuestion().answeredBy(francisco)
                .getData().stream().filter(x -> x.getId() == 1).findFirst().orElse(null);

      francisco.should(
                seeThat("usuario no es nulo", act -> user, notNullValue())
      );

      francisco.should(
                seeThat("el email del usuario", act -> user.getEmail(), equalTo("george.bluth@reqres.in")),
                seeThat("el email del usuario", act -> user.getAvatar(), equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"))
      );
    }

    @Test
    public void registerUserTest(){
        Actor francisco = Actor.named("Francisco the student")
                .whoCan(CallAnApi.at(restApiUrl));

        RegisterUserInfo registerUserInfo = new RegisterUserInfo();

        registerUserInfo.setName("Emma");
        registerUserInfo.setJob("leader");
        registerUserInfo.setEmail("emma.wong@reqres.in");
        registerUserInfo.setPassword("serenity");

        francisco.attemptsTo(
                RegisterUser.withInfo(registerUserInfo)
        );

        francisco.should(
                seeThat("el codigo de respuesta",
                        new ResponseCode(),
                        equalTo(200))
        );
    }
}
