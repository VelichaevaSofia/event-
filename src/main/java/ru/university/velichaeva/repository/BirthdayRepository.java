package ru.university.velichaeva.repository;

import ru.university.velichaeva.model.Birthday;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BirthdayRepository {

    Birthday create(Birthday birthday);

    Birthday update(Birthday birthday);

    boolean delete(UUID id);

    List<Birthday> list();

    Optional<Birthday> getById(UUID id);
}