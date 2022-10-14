package ru.job4j.dreamjob.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oywayten on 12.10.2022.
 */
@Repository
public class PostDBStore {
    private static final String FIND_ALL = "SELECT * FROM post";
    private static final String ADD =
            "INSERT INTO post(fname, description, created, visible, city_id) VALUES (?, ?, ?, ?,?)";
    private static final String UPDATE =
            "UPDATE post SET fname = ?, description = ?, created = ?, visible = ?, city_id = ? WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT * FROM post WHERE id = ?";
    private static final Logger LOG = LogManager.getLogger(PostDBStore.class);

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(getPost(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return posts;
    }

    private Post getPost(ResultSet it) throws SQLException {
        return new Post(it.getInt("id"), it.getString("fname"), it.getString("description"),
                it.getTimestamp("created").toLocalDateTime(), it.getBoolean("visible"),
                new City(it.getInt("city_id"), null));
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, true);
            ps.setInt(5, post.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.setInt(6, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return getPost(it);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
