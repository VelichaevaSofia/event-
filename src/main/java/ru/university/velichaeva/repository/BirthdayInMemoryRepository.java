package ru.university.velichaeva.repository;

import ru.university.velichaeva.model.Birthday;
import java.util.*;

public class BirthdayInMemoryRepository implements BirthdayRepository {

    private final Map<UUID, Birthday> data;

    public BirthdayInMemoryRepository(Map<UUID, Birthday> data) {
        this.data = data;
    }

    /* public BirthdayInMemoryRepository() {
      this.data = new HashMap<>();
    } */

    @Override
    public Birthday create(Birthday birthday) {
        UUID id = birthday.getId();
        data.put(id, birthday);
        return birthday;
    }

    @Override
    public Birthday update(Birthday birthday) {
        UUID id = birthday.getId();
        data.put(id, birthday);
        return birthday;
    }

    @Override
    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }

    @Override
    public List<Birthday> list() {
        return data.values().stream().toList();
    }

    @Override
    public Optional<Birthday> getById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }
}