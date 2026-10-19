package ru.university.velichaeva;

import ru.university.velichaeva.controller.EventController;
import ru.university.velichaeva.model.Birthday;
import ru.university.velichaeva.model.Meeting;
import ru.university.velichaeva.presenter.Presenter;
import ru.university.velichaeva.repository.BirthdayInMemoryRepository;
import ru.university.velichaeva.repository.BirthdayRepository;
import ru.university.velichaeva.repository.MeetingInMemoryRepository;
import ru.university.velichaeva.repository.MeetingRepository;
import ru.university.velichaeva.service.BirthdayService;
import ru.university.velichaeva.service.MeetingService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));
        Map<UUID, Birthday> birthdayMap = new HashMap<>();
        Map<UUID, Meeting> meetingMap = new HashMap<>();

        BirthdayRepository birthdayRepository = new BirthdayInMemoryRepository(birthdayMap);
        MeetingRepository meetingRepository = new MeetingInMemoryRepository(meetingMap);

        BirthdayService birthdayService = new BirthdayService(birthdayRepository);
        MeetingService meetingService = new MeetingService(meetingRepository);

        Scanner scanner = new Scanner(System.in);
        Presenter presenter = new Presenter();

        EventController controller = new EventController(
                scanner,
                birthdayService,
                meetingService,
                presenter
        );

        controller.run();
    }
}