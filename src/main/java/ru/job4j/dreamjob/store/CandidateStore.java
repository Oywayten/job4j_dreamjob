package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс - хранилище кандидатов, синглтон.
 */
public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Ivan", "This is Junior", LocalDate.parse("1993-01-01")));
        candidates.put(2, new Candidate(2, "Sergey", "This is Middle", LocalDate.parse("1989-01-02")));
        candidates.put(3, new Candidate(3, "Bob", "This is Senior", LocalDate.parse("1983-01-03")));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}