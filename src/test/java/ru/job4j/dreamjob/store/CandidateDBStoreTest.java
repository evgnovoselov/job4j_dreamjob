package ru.job4j.dreamjob.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CandidateDBStoreTest {
    /**
     * Проверяем добавление кандидата в бд и получение его по id.
     */
    @Test
    public void whenCreateCandidateThenHaveCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, new byte[]{}, "Java Developer", "Description",
                new City(1, "Moscow"), LocalDateTime.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        Assertions.assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidateThenHaveChangedCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, new byte[]{}, "Java Developer", "Description",
                new City(1, "Moscow"), LocalDateTime.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        candidateInDb.setName("Super Java Developer");
        store.update(candidateInDb);
        candidateInDb = store.findById(candidate.getId());
        Assertions.assertThat(candidateInDb.getName()).isEqualTo("Super Java Developer");
    }
}