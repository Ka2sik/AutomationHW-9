package ru.netology.delivery.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class NegativeTests {
    DataGenerator.UserInfo client = DataGenerator.Registration.generateUser("ru");
    int daysBeforeFirstMeeting = 4;
    String firstMeetingDate = DataGenerator.generateDate(daysBeforeFirstMeeting);
    String wrongPhone = "+712345";
    String wrongName = "Alexey Ivanov";

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should Show Error When Phone Number Is Not Valid")
    void ShouldShowErrorWhenPhoneNumberIsNotValid() {

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(client.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(client.getName());
        $("[data-test-id=phone] input").setValue(wrongPhone);
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан некорректно.Номер должен состоять из 11 цифр"))
                .shouldBe(visible);
    }

    @Test
    @DisplayName("Should Show Error When Phone Number Is Empty")
    void ShouldShowErrorWhenPhoneNumberIsEmpty() {

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(client.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(client.getName());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"))
                .shouldBe(visible);
    }

    @Test
    @DisplayName("Should Show Error When Name Is Not Valid")
    void ShouldShowErrorWhenNameIsNotValid() {

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(client.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(wrongName);
        $("[data-test-id=phone] input").setValue(client.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(visible);
    }
}

