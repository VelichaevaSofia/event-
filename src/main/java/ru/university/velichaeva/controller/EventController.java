package ru.university.velichaeva.controller;

import ru.university.velichaeva.exception.BusinessException;
import ru.university.velichaeva.model.Birthday;
import ru.university.velichaeva.model.Meeting;
import ru.university.velichaeva.presenter.Presenter;
import ru.university.velichaeva.service.BirthdayService;
import ru.university.velichaeva.service.MeetingService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class EventController {

    private final Scanner scanner;
    private final BirthdayService birthdayService;
    private final MeetingService meetingService;
    private final Presenter presenter;

    public EventController(Scanner scanner, BirthdayService birthdayService,
                           MeetingService meetingService, Presenter presenter) {
        this.scanner = scanner;
        this.birthdayService = birthdayService;
        this.meetingService = meetingService;
        this.presenter = presenter;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== ОРГАНАЙЗЕР ===");
            System.out.println("1. Дни рождения");
            System.out.println("2. Встречи");
            System.out.println("0. Выход");
            System.out.print("Выберите раздел: ");

            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1":
                        birthdayMenu();
                        break;
                    case "2":
                        meetingMenu();
                        break;
                    case "0":
                        System.out.println("Выход из программы");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неправильно введена команда");
                }
            } catch (BusinessException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    private void birthdayMenu() {
        while (true) {
            System.out.println("\n=== ДНИ РОЖДЕНИЯ ===");
            System.out.println("1. Показать все");
            System.out.println("2. Найти по ID");
            System.out.println("3. Создать");
            System.out.println("4. Обновить");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1":
                        showAllBirthdays();
                        break;
                    case "2":
                        showBirthdayById();
                        break;
                    case "3":
                        createBirthday();
                        break;
                    case "4":
                        updateBirthday();
                        break;
                    case "5":
                        deleteBirthday();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неправильно введена команда");
                }
            } catch (BusinessException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    private void meetingMenu() {
        while (true) {
            System.out.println("\n=== ВСТРЕЧИ ===");
            System.out.println("1. Показать все");
            System.out.println("2. Найти по ID");
            System.out.println("3. Создать");
            System.out.println("4. Обновить");
            System.out.println("5. Удалить");
            System.out.println("0. Назад");
            System.out.print("Выберите действие: ");

            String command = scanner.nextLine();

            try {
                switch (command) {
                    case "1":
                        showAllMeetings();
                        break;
                    case "2":
                        showMeetingById();
                        break;
                    case "3":
                        createMeeting();
                        break;
                    case "4":
                        updateMeeting();
                        break;
                    case "5":
                        deleteMeeting();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Неправильно введена команда");
                }
            } catch (BusinessException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
    }

    private void showAllBirthdays() {
        List<Birthday> all = birthdayService.getAll();
        if (all.isEmpty()) {
            System.out.println("Дней рождения нет");
            return;
        }
        for (Birthday b : all) {
            presenter.showBirthday(b);
        }
    }

    private void showBirthdayById() {
        UUID id = getUUID();
        Optional<Birthday> birthday = birthdayService.getById(id);
        if (birthday.isPresent()) {
            presenter.showBirthday(birthday.get());
        } else {
            System.out.println("День рождения не найден");
        }
    }

    private void createBirthday() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        LocalDate date = getDate("Введите дату (ГГГГ-ММ-ДД): ");
        LocalTime hourBirth = getTime("Введите время (ЧЧ:ММ): ");

        System.out.print("Введите подарок: ");
        String gift = scanner.nextLine();

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        birthdayService.create(name, date, hourBirth, gift, description);
        System.out.println("День рождения успешно создан");
    }

    private void updateBirthday() {
        UUID id = getUUID();
        Optional<Birthday> existing = birthdayService.getById(id);

        if (existing.isPresent()) {
            Birthday old = existing.get();

            System.out.print("Введите новое имя (или Enter чтобы оставить " + old.getName() + "): ");
            String name = scanner.nextLine();
            if (name.isBlank()) name = old.getName();

            LocalDate date = getDate("Введите новую дату (или Enter чтобы оставить " + old.getDate() + "): ");
            if (date == null) date = old.getDate();

            LocalTime hourBirth = getTime("Введите новое время (или Enter чтобы оставить " + old.getHourBirth() + "): ");
            if (hourBirth == null) hourBirth = old.getHourBirth();

            System.out.print("Введите новый подарок (или Enter чтобы оставить " + old.getGift() + "): ");
            String gift = scanner.nextLine();
            if (gift.isBlank()) gift = old.getGift();

            System.out.print("Введите новое описание (или Enter чтобы оставить " + old.getDescription() + "): ");
            String description = scanner.nextLine();
            if (description.isBlank()) description = old.getDescription();

            Birthday updated = new Birthday(id, date, name, hourBirth, gift, description);
            birthdayService.update(updated);
            System.out.println("День рождения обновлен");
        } else {
            System.out.println("День рождения не найден");
        }
    }

    private void deleteBirthday() {
        UUID id = getUUID();
        if (birthdayService.deleteById(id)) {
            System.out.println("День рождения удален");
        }
    }

    private void showAllMeetings() {
        List<Meeting> all = meetingService.getAll();
        if (all.isEmpty()) {
            System.out.println("Встреч нет");
            return;
        }
        for (Meeting m : all) {
            presenter.showMeeting(m);
        }
    }

    private void showMeetingById() {
        UUID id = getUUID();
        Optional<Meeting> meeting = meetingService.getById(id);
        if (meeting.isPresent()) {
            presenter.showMeeting(meeting.get());
        } else {
            System.out.println("Встреча не найдена");
        }
    }

    private void createMeeting() {
        System.out.print("Введите собеседника: ");
        String interlocutor = scanner.nextLine();

        LocalDate date = getDate("Введите дату (ГГГГ-ММ-ДД): ");
        LocalTime startTime = getTime("Введите время начала (ЧЧ:ММ): ");

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        meetingService.create(interlocutor, date, startTime, description);
        System.out.println("Встреча успешно создана");
    }

    private void updateMeeting() {
        UUID id = getUUID();
        Optional<Meeting> existing = meetingService.getById(id);

        if (existing.isPresent()) {
            Meeting old = existing.get();

            System.out.print("Введите нового собеседника (или Enter чтобы оставить " + old.getInterlocutor() + "): ");
            String interlocutor = scanner.nextLine();
            if (interlocutor.isBlank()) interlocutor = old.getInterlocutor();

            LocalDate date = getDate("Введите новую дату (или Enter чтобы оставить " + old.getDate() + "): ");
            if (date == null) date = old.getDate();

            LocalTime startTime = getTime("Введите новое время (или Enter чтобы оставить " + old.getStartTime() + "): ");
            if (startTime == null) startTime = old.getStartTime();

            System.out.print("Введите новое описание (или Enter чтобы оставить " + old.getDescription() + "): ");
            String description = scanner.nextLine();
            if (description.isBlank()) description = old.getDescription();

            Meeting updated = new Meeting(id, date, interlocutor, startTime, description);
            meetingService.update(updated);
            System.out.println("Встреча обновлена");
        } else {
            System.out.println("Встреча не найдена");
        }
    }

    private void deleteMeeting() {
        UUID id = getUUID();
        if (meetingService.deleteById(id)) {
            System.out.println("Встреча удалена");
        }
    }

    private UUID getUUID() {
        while (true) {
            System.out.print("Введите ID: ");
            String id = scanner.nextLine();
            try {
                return UUID.fromString(id);
            } catch (Exception e) {
                System.out.println("Неправильный формат ID");
            }
        }
    }

    private LocalDate getDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isBlank()) return null;
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Неправильный формат даты");
            }
        }
    }

    private LocalTime getTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input.isBlank()) return null;
            try {
                return LocalTime.parse(input);
            } catch (Exception e) {
                System.out.println("Неправильный формат времени");
            }
        }
    }
}