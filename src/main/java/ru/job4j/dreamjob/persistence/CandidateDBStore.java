package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oywayten on 13.10.2022.
 */
@Repository
public class CandidateDBStore {
    private static final String FIND_ALL = "SELECT * FROM candidate";
    private static final String ADD = "INSERT INTO candidate(fname, description, created, file) VALUES (?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE candidate SET fname = ?, description = ?, created = ?, file = ? WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private static final Logger LOG = LogManager.getLogger(CandidateDBStore.class);
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidates;
    }

    private Candidate getCandidate(ResultSet it) throws SQLException {
        return new Candidate(it.getInt("id"), it.getString("fname"), it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(), it.getBytes("file"));
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDesc());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Candidate findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
