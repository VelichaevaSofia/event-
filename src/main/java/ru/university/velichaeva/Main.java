package ru.university.velichaeva;

import ru.university.velichaeva.controller.EventController;
import ru.university.velichaeva.model.Birthday;
import ru.university.velichaeva.model.Meeting;
import ru.university.velichaeva.presenter.Presenter;
import ru.university.velichaeva.repository.BirthdayRepository;
import ru.university.velichaeva.repository.MeetingRepository;
import ru.university.velichaeva.repository.BirthdayDatabaseRepository;
import ru.university.velichaeva.repository.MeetingDatabaseRepository;
import ru.university.velichaeva.service.BirthdayService;
import ru.university.velichaeva.service.MeetingService;

import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = connectDB();

        initDB(dataSource);

        BirthdayRepository birthdayRepository = new BirthdayDatabaseRepository(dataSource);
        MeetingRepository meetingRepository = new MeetingDatabaseRepository(dataSource);

        /*BirthdayRepository birthdayRepository = new ru.university.velichaeva.repository.BirthdayInMemoryRepository(new java.util.HashMap<>());
        MeetingRepository meetingRepository = new ru.university.velichaeva.repository.MeetingInMemoryRepository(new java.util.HashMap<>()); */

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

    public static DataSource connectDB() {
        var datasource = new PGSimpleDataSource();

        datasource.setServerNames(new String[]{"localhost"});
        datasource.setPortNumbers(new int[]{5433});
        datasource.setDatabaseName("event");
        datasource.setUser("event");
        datasource.setPassword("event");

        return datasource;
    }

    public static void initDB(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            var sqlBirthdays = """
                    CREATE TABLE IF NOT EXISTS birthdays (
                        id UUID PRIMARY KEY,
                        name TEXT NOT NULL,
                        birth_date DATE,
                        hour_birth TIME,
                        gift TEXT,
                        description TEXT
                    )
                    """;
            statement.executeUpdate(sqlBirthdays);


            var sqlMeetings = """
                    CREATE TABLE IF NOT EXISTS meetings (
                        id UUID PRIMARY KEY,
                        interlocutor TEXT NOT NULL,
                        meeting_date DATE,
                        start_time TIME,
                        description TEXT
                    )
                    """;
            statement.executeUpdate(sqlMeetings);

            System.out.println("Таблицы созданы или уже есть");

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблиц: " + e.getMessage(), e);
        }
    }
}