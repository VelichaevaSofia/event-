package ru.university.velichaeva.repository;

import ru.university.velichaeva.model.Meeting;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MeetingDatabaseRepository implements MeetingRepository {

    private final DataSource dataSource;

    public MeetingDatabaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Meeting create(Meeting meeting) {
        var sql = """
                INSERT INTO meetings(id, interlocutor, meeting_date, start_time, description)
                VALUES (?::UUID, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, meeting.getId().toString());
            statement.setString(2, meeting.getInterlocutor());
            statement.setDate(3, Date.valueOf(meeting.getDate()));
            statement.setTime(4, Time.valueOf(meeting.getStartTime()));
            statement.setString(5, meeting.getDescription());

            statement.executeUpdate();
            return meeting;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании встречи: " + e.getMessage(), e);
        }
    }

    @Override
    public Meeting update(Meeting meeting) {
        var sql = """
                UPDATE meetings
                SET interlocutor = ?, meeting_date = ?, start_time = ?, description = ?
                WHERE id = ?::UUID
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, meeting.getInterlocutor());
            statement.setDate(2, Date.valueOf(meeting.getDate()));
            statement.setTime(3, Time.valueOf(meeting.getStartTime()));
            statement.setString(4, meeting.getDescription());
            statement.setString(5, meeting.getId().toString());

            statement.executeUpdate();
            return meeting;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении встречи: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(UUID id) {
        var sql = """
                DELETE FROM meetings
                WHERE id = ?::UUID
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id.toString());
            int deletedRows = statement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении встречи: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Meeting> list() {
        var sql = """
                SELECT id, interlocutor, meeting_date, start_time, description
                FROM meetings
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(sql);
            List<Meeting> meetings = new ArrayList<>();

            while (rs.next()) {
                var meeting = mapRowToMeeting(rs);
                meetings.add(meeting);
            }

            return meetings;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех встреч: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Meeting> getById(UUID id) {
        var sql = """
                SELECT id, interlocutor, meeting_date, start_time, description
                FROM meetings
                WHERE id = ?::UUID
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToMeeting(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске встречи: " + e.getMessage(), e);
        }
    }

    private Meeting mapRowToMeeting(ResultSet rs) throws SQLException {
        return new Meeting(
                rs.getObject("id", UUID.class),
                rs.getDate("meeting_date").toLocalDate(),
                rs.getString("interlocutor"),
                rs.getTime("start_time").toLocalTime(),
                rs.getString("description")
        );
    }
}