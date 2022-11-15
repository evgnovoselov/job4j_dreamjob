package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@ThreadSafe
public class CandidateService {
    private final CandidateStore candidateStore;

    public CandidateService(CandidateStore candidateStore) {
        this.candidateStore = candidateStore;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void add(Candidate candidate) {
        candidate.setCreated(LocalDateTime.now());
        candidateStore.add(candidate);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}
