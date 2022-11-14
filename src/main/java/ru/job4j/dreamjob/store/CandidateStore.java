package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "Senior Java Developer", new City(1, "Москва"),
                LocalDateTime.of(2022, 11, 7, 20, 14)));
        candidates.put(2, new Candidate(2, "Kristina", "Middle Java Developer", new City(3, "ЕКб"),
                LocalDateTime.of(2022, 11, 8, 21, 14)));
        candidates.put(3, new Candidate(3, "Anna", "Junior Java Developer", new City(2, "СПб"),
                LocalDateTime.of(2022, 11, 9, 22, 14)));
        id.set(3);
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void add(Candidate candidate) {
        int currentId = id.incrementAndGet();
        candidate.setId(currentId);
        candidates.put(currentId, candidate);
    }

    public void update(Candidate candidate) {
        candidates.replace(candidate.getId(), candidate);
    }
}
