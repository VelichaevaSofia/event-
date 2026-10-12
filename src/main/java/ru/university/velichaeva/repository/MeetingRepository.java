package ru.university.velichaeva.repository;

import ru.university.velichaeva.model.Meeting;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeetingRepository {

    Meeting create(Meeting meeting);

    Meeting update(Meeting meeting);

    boolean delete(UUID id);

    List<Meeting> list();

    Optional<Meeting> getById(UUID id);
}