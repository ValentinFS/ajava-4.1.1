package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {
    DataGenerator() {
    }

    private static Faker faker = new Faker(new Locale("ru"));
    private static Faker fakerWrong = new Faker(new Locale("pt"));


    public static String getDate(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String getCity() {
        return faker.address().city();
    }

    public static String getName() {
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String getPhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String getWrongCity() {
        return fakerWrong.address().city();
    }

    public static String getWrongName() {
        return fakerWrong.name().lastName() + " " + fakerWrong.name().firstName();
    }

    public static String getWrongPhone() {
        return fakerWrong.phoneNumber().phoneNumber();
    }


}






