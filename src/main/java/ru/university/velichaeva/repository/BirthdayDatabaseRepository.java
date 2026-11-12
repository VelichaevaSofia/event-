package ru.university.velichaeva.repository;

import ru.university.velichaeva.model.Birthday;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BirthdayDatabaseRepository implements BirthdayRepository {

    private final DataSource dataSource;

    public BirthdayDatabaseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Birthday create(Birthday birthday) {
        var sql = """
                INSERT INTO birthdays(id, name, birth_date, hour_birth, gift, description)
                VALUES (?::UUID, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, birthday.getId().toString());
            statement.setString(2, birthday.getName());
            statement.setDate(3, Date.valueOf(birthday.getDate()));
            statement.setTime(4, Time.valueOf(birthday.getHourBirth()));
            statement.setString(5, birthday.getGift());
            statement.setString(6, birthday.getDescription());

            statement.executeUpdate();
            return birthday;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании дня рождения: " + e.getMessage(), e);
        }
    }

    @Override
    public Birthday update(Birthday birthday) {
        var sql = """
                UPDATE birthdays
                SET name = ?, birth_date = ?, hour_birth = ?, gift = ?, description = ?
                WHERE id = ?::UUID
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, birthday.getName());
            statement.setDate(2, Date.valueOf(birthday.getDate()));
            statement.setTime(3, Time.valueOf(birthday.getHourBirth()));
            statement.setString(4, birthday.getGift());
            statement.setString(5, birthday.getDescription());
            statement.setString(6, birthday.getId().toString());

            statement.executeUpdate();
            return birthday;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении дня рождения: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(UUID id) {
        var sql = """
                DELETE FROM birthdays
                WHERE id = ?::UUID
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id.toString());
            int deletedRows = statement.executeUpdate();
            return deletedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении дня рождения: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Birthday> list() {
        var sql = """
                SELECT id, name, birth_date, hour_birth, gift, description
                FROM birthdays
                """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet rs = statement.executeQuery(sql);
            List<Birthday> birthdays = new ArrayList<>();

            while (rs.next()) {
                var birthday = mapRowToBirthday(rs);
                birthdays.add(birthday);
            }

            return birthdays;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех дней рождений: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Birthday> getById(UUID id) {
        var sql = """
                SELECT id, name, birth_date, hour_birth, gift, description
                FROM birthdays
                WHERE id = ?::UUID
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRowToBirthday(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске дня рождения: " + e.getMessage(), e);
        }
    }

    private Birthday mapRowToBirthday(ResultSet rs) throws SQLException {
        return new Birthday(
                rs.getObject("id", UUID.class),
                rs.getDate("birth_date").toLocalDate(),
                rs.getString("name"),
                rs.getTime("hour_birth").toLocalTime(),
                rs.getString("gift"),
                rs.getString("description")
        );
    }
}