package com.kodilla.carrental.weather.doimain;

import lombok.Getter;

import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class Days {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private String today = LocalDate.now().atTime(15,00,00).format(formatter);
    private String nextDayOne = LocalDate.now().atTime(15,00,00).plusDays(1).format(formatter);
    private String nextDayTwo = LocalDate.now().atTime(15,00,00).plusDays(2).format(formatter);
    private String nextDayThree = LocalDate.now().atTime(15,00,00).plusDays(3).format(formatter);
    private String nextDayFour = LocalDate.now().atTime(15,00,00).plusDays(4).format(formatter);
}
