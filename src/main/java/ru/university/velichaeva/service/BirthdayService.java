package ru.university.velichaeva.service;

import ru.university.velichaeva.exception.EventNotFoundException;
import ru.university.velichaeva.exception.NameEmptyException;
import ru.university.velichaeva.model.Birthday;
import ru.university.velichaeva.repository.BirthdayRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BirthdayService {

    private final BirthdayRepository repo;

    public BirthdayService(BirthdayRepository repo) {
        this.repo = repo;
    }

    public Birthday create(String name, LocalDate date, LocalTime hourBirth, String gift, String description) {
        if (name.isBlank()) {
            throw new NameEmptyException();
        }

        var id = UUID.randomUUID();
        var birthday = new Birthday(id, date, name, hourBirth, gift, description);

        repo.create(birthday);

        return birthday;
    }

    public Birthday update(Birthday newBirthday) {
        if (newBirthday.getName().isBlank()) {
            throw new NameEmptyException();
        }

        repo.update(newBirthday);

        return newBirthday;
    }

    public List<Birthday> getAll() {
        return repo.list();
    }

    public Optional<Birthday> getById(UUID id) {
        return repo.getById(id);
    }

    public boolean deleteById(UUID id) {
        if (!repo.delete(id)) {
            throw new EventNotFoundException(id);
        }
        return true;
    }
}