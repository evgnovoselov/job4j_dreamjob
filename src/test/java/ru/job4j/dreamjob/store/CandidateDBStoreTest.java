package ru.job4j.dreamjob.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
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
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
        store.delete(candidate.getId());
    }

    /**
     * Проверяем удаление кандидата.
     */
    @Test
    public void whenCreateCandidateAndDeleteThenNotHaveCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, new byte[]{}, "Java Developer", "Description",
                new City(1, "Moscow"), LocalDateTime.now());
        store.add(candidate);
        store.delete(candidate.getId());
        assertThat(store.findById(candidate.getId())).isNull();
    }

    /**
     * Проверяем обновление кандидата в бд.
     */
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
        assertThat(candidateInDb.getName()).isEqualTo("Super Java Developer");
        store.delete(candidate.getId());
    }

    /**
     * Проверяем получение коллекции кандидатов.
     */
    @Test
    public void whenCreateCandidatesAndFindAllThenHaveMoreCandidates() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate[] candidates = new Candidate[]{
                new Candidate(0, new byte[]{}, "Jun Java Developer", "Description",
                        new City(1, "Moscow"), LocalDateTime.now()),
                new Candidate(0, new byte[]{}, "Middle Java Developer", "Description",
                        new City(1, "Moscow"), LocalDateTime.now())
        };
        Arrays.stream(candidates).forEach(store::add);
        assertThat(store.findAll().size() > 1).isTrue();
        Arrays.stream(candidates).map(Candidate::getId).forEach(store::delete);
    }
}