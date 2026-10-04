package ru.university.velichaeva.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Birthday {
    private LocalDate date;
    private String name;
    private LocalTime hourBirth;
    private String gift;
    private String description;

    public Birthday(LocalDate date, String name, LocalTime hourBirth, String gift, String description) {
        this.date = date;
        this.name = name;
        this.hourBirth = hourBirth;
        this.gift = gift;
        this.description = description;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public LocalTime getHourBirth(){
        return hourBirth;
    }
    public void setHourBirth(LocalTime hourBirth){
        this.hourBirth = hourBirth;
    }

    public String getGift(){
        return gift;
    }
    public void setGift(String gift){
        this.gift = gift;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
