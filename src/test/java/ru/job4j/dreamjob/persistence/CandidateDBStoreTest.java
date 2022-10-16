package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Oywayten on 16.10.2022.
 */
class CandidateDBStoreTest {

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Pavel", "This is candidate 0", LocalDateTime.now(), null);
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Ivan", "This is candidate 1", LocalDateTime.now(), null);
        Candidate candidate2 = new Candidate(0, "Piter", "This is candidate 2", LocalDateTime.now(), null);
        candidate = store.add(candidate);
        candidate2.setId(candidate.getId());
        store.update(candidate2);
        Candidate candidateInDb = store.findById(candidate2.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate2.getName());
    }

    @Test
    public void whenFindAll() {
        CandidateDBStore store = new CandidateDBStore(new Main().loadPool());
        Candidate candidate = new Candidate(0, "Vasia", "This is candidate 3", LocalDateTime.now(), null);
        Candidate candidate2 = new Candidate(0, "Bob", "This is candidate 4", LocalDateTime.now(), null);
        candidate = store.add(candidate);
        candidate2 = store.add(candidate2);
        List<Candidate> all = store.findAll();
        assertThat(all.containsAll(List.of(candidate, candidate2))).isEqualTo(true);
    }
}