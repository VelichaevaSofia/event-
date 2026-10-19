package ru.university.velichaeva.repository;

import ru.university.velichaeva.model.Meeting;
import java.util.*;

public class MeetingInMemoryRepository implements MeetingRepository {

    private final Map<UUID, Meeting> data;

    public MeetingInMemoryRepository(Map<UUID, Meeting> data) {
        this.data = data;
    }

    public MeetingInMemoryRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public Meeting create(Meeting meeting) {
        UUID id = meeting.getId();
        data.put(id, meeting);
        return meeting;
    }

    @Override
    public Meeting update(Meeting meeting) {
        UUID id = meeting.getId();
        data.put(id, meeting);
        return meeting;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }

    @Override
    public List<Meeting> list() {
        return data.values().stream().toList();
    }

    @Override
    public Optional<Meeting> getById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }
}