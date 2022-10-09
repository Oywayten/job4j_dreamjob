package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
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
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_DAY, 0)
                .toFormatter();
        candidates.put(1, new Candidate(1, "Ivan", "This is Junior", LocalDateTime.parse("1993-01-01", fmt)));
        candidates.put(2, new Candidate(2, "Sergey", "This is Middle", LocalDateTime.parse("1989-01-02", fmt)));
        candidates.put(3, new Candidate(3, "Bob", "This is Senior", LocalDateTime.parse("1983-01-03", fmt)));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}