package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@ThreadSafe
public class CandidateService {
    private final CandidateDBStore store;
    private final CityService cityService;

    public CandidateService(CandidateDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Candidate> findAll() {
        Collection<Candidate> candidates = store.findAll();
        candidates.forEach(candidate -> candidate.setCity(cityService.findById(candidate.getCity().getId())));
        return candidates;
    }

    public Candidate findById(int id) {
        Candidate candidate = store.findById(id);
        candidate.setCity(cityService.findById(candidate.getCity().getId()));
        return candidate;
    }

    public void add(Candidate candidate) {
        candidate.setCreated(LocalDateTime.now());
        store.add(candidate);
    }

    public void update(Candidate candidate) {
        Candidate oldCandidate = findById(candidate.getId());
        if (candidate.getPhoto().length == 0 && oldCandidate.getPhoto().length > 0) {
            candidate.setPhoto(oldCandidate.getPhoto());
        }
        store.update(candidate);
    }
}
