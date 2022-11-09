package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "Senior Java Developer",
                LocalDateTime.of(2022, 11, 7, 20, 14)));
        candidates.put(2, new Candidate(2, "Kristina", "Middle Java Developer",
                LocalDateTime.of(2022, 11, 8, 21, 14)));
        candidates.put(3, new Candidate(3, "Anna", "Junior Java Developer",
                LocalDateTime.of(2022, 11, 9, 22, 14)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
