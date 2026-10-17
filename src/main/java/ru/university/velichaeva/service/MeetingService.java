package ru.university.velichaeva.service;

import ru.university.velichaeva.exception.EventNotFoundException;
import ru.university.velichaeva.exception.InterlocutorEmptyException;
import ru.university.velichaeva.model.Meeting;
import ru.university.velichaeva.repository.MeetingRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MeetingService {

    private final MeetingRepository repo;

    public MeetingService(MeetingRepository repo) {
        this.repo = repo;
    }

    public Meeting create(String interlocutor, LocalDate date, LocalTime startTime, String description) {
        if (interlocutor.isBlank()) {
            throw new InterlocutorEmptyException();
        }

        var id = UUID.randomUUID();
        var meeting = new Meeting(id, date, interlocutor, startTime, description);

        repo.create(meeting);

        return meeting;
    }

    public Meeting update(Meeting newMeeting) {
        if (newMeeting.getInterlocutor().isBlank()) {
            throw new InterlocutorEmptyException();
        }

        repo.update(newMeeting);

        return newMeeting;
    }

    public List<Meeting> getAll() {
        return repo.list();
    }

    public Optional<Meeting> getById(UUID id) {
        return repo.getById(id);
    }

    public boolean deleteById(UUID id) {
        if (!repo.delete(id)) {
            throw new EventNotFoundException(id);
        }
        return true;
    }
}