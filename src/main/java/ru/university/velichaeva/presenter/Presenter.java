package ru.university.velichaeva.presenter;

import ru.university.velichaeva.model.Birthday;
import ru.university.velichaeva.model.Meeting;

public class Presenter {
    public void showBirthday(Birthday birthday){
        System.out.println("!!!День рождение!!!");
        System.out.println("Имя: " + birthday.getName());
        System.out.println("Дата: " + birthday.getDate());
        System.out.println("Время: " + birthday.getHourBirth());
        System.out.println("Подарок: " + birthday.getGift());
        System.out.println("Описание: " + birthday.getDescription());
    }

    public void showMeeting(Meeting meeting){
        System.out.println("!Встреча!");
        System.out.println("Дата: " + meeting.getDate());
        System.out.println("Собеседник:" + meeting.getInterlocutor());
        System.out.println("Время начала: " + meeting.getStartTime());
        System.out.println("Описание: " + meeting.getDescription());
    }
}
