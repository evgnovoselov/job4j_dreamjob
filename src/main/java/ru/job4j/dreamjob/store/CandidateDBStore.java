package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;

@Repository
@ThreadSafe
public class CandidateDBStore {
    public Collection<Candidate> findAll() {
        return null;
    }

    public Candidate findById(int id) {
        return null;
    }

    public void add(Candidate candidate) {
        
    }

    public void update(Candidate candidate) {

    }
}
