package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;

/**
 * Created by Oywayten on 10.10.2022.
 */
@Service
public class CandidateService {
    private static final CandidateService INST = new CandidateService();
    private final CandidateStore store = CandidateStore.instOf();

    private CandidateService() {
    }

    public static CandidateService instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return store.findAll();
    }

    public Candidate findById(int id) {
        return store.findById(id);
    }

    public void add(Candidate candidate) {
        store.add(candidate);
    }

    public void update(Candidate candidate) {
        store.update(candidate);
    }
}
