package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс - хранилище кандидатов, синглтон.
 */
@Repository
@ThreadSafe
public class CandidateStore {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    private CandidateStore() {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_DAY, 0)
                .toFormatter();
        candidates.put(id.getAndIncrement(), new Candidate(1, "Ivan", "This is Junior", LocalDateTime.parse("1993-01-01", fmt)));
        candidates.put(id.getAndIncrement(), new Candidate(2, "Sergey", "This is Middle", LocalDateTime.parse("1989-01-02", fmt)));
        candidates.put(id.getAndIncrement(), new Candidate(3, "Bob", "This is Senior", LocalDateTime.parse("1983-01-03", fmt)));
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void add(Candidate candidate) {
        candidate.setId(id.getAndIncrement());
        candidate.setCreated(LocalDateTime.now());
        candidates.put(candidate.getId(), candidate);
    }

    public void update(Candidate candidate) {
        candidate.setCreated(LocalDateTime.now());
        candidates.replace(candidate.getId(), candidate);
    }
}