package ru.university.velichaeva.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Meeting {
    private UUID id;
    private LocalDate date;
    private String interlocutor;
    private LocalTime startTime;
    private String description;

    public Meeting(UUID id, LocalDate date, String interlocutor, LocalTime startTime, String description){
        this.id = id;
        this.date = date;
        this.interlocutor = interlocutor;
        this.startTime = startTime;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    public String getInterlocutor(){
        return interlocutor;
    }
    public void setInterlocutor(String interlocutor){
        this.interlocutor = interlocutor;
    }

    public LocalTime getStartTime(){
        return startTime;
    }
    public void setStartTime(LocalTime startTime){
        this.startTime = startTime;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
