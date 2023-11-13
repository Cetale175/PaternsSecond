package test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static java.ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static java.ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static java.ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {


    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should succes sfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[date-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[date-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error massage if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $("[date-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[date-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[button.button").click();
        $("[date-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[date-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[date-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[button.button").click();
        $("[date-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(10))
                .shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[date-test-id='login'] input").setValue(wrongLogin);
        $("[date-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[button.button").click();
        $("[date-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe((Condition.visible));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[date-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[date-test-id='password'] input").setValue(wrongPassword);
        $("[button.button").click();
        $("[date-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(10))
                .shouldBe((Condition.visible));
    }
}
