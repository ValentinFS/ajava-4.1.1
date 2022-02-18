package ru.netology;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
//        Configuration.headless = true;
        open("http://localhost:9999");
    }


    @Test
    void shouldTestAllValidInfo() {
        $("[data-test-id='city'] [class='input__control']").setValue(DataGenerator.getCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(3));
        $("[data-test-id='name'] [class='input__control']").setValue(DataGenerator.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(DataGenerator.getPhone());
        $(".checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.getDate(3)));
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(5));
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Встреча успешно запланирована на " + DataGenerator.getDate(5)));
    }

    @Test
    void shouldTestFieldsEmpty() {
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongCity() {
        $("[data-test-id='city'] [class='input__control']").setValue(DataGenerator.getWrongCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(3));
        $("[data-test-id='name'] [class='input__control']").setValue(DataGenerator.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(DataGenerator.getPhone());
        $(".checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestWrongDate() {
        $("[data-test-id='city'] [class='input__control']").setValue(DataGenerator.getCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(-2));
        $("[data-test-id='name'] [class='input__control']").setValue(DataGenerator.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(DataGenerator.getPhone());
        $(".checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='date'] .input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestWrongName() {
        $("[data-test-id='city'] [class='input__control']").setValue(DataGenerator.getCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(3));
        $("[data-test-id='name'] [class='input__control']").setValue(DataGenerator.getWrongName());
        $("[data-test-id='phone'] [class='input__control']").setValue(DataGenerator.getPhone());
        $(".checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestWrongPhone() {
        $("[data-test-id='city'] [class='input__control']").setValue(DataGenerator.getCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(3));
        $("[data-test-id='name'] [class='input__control']").setValue(DataGenerator.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(DataGenerator.getWrongPhone());
        $(".checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='phone'].input_invalid .input__sub")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Неверный номер телефона"));
    }

    @Test
    void shouldTestCheckBoxEmpty() {
        $("[data-test-id='city'] [class='input__control']").setValue(DataGenerator.getCity());
        $("[data-test-id='date'] [class='input__control']").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id='date'] [class='input__control']").setValue(DataGenerator.getDate(3));
        $("[data-test-id='name'] [class='input__control']").setValue(DataGenerator.getName());
        $("[data-test-id='phone'] [class='input__control']").setValue(DataGenerator.getPhone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='agreement'] .checkbox__text")
                .shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}
