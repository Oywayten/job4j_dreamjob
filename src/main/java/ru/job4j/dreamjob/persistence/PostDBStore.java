package ru.job4j.dreamjob.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class);

    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("fname"), it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(), it.getBoolean("visible"),
                            new City(it.getInt("city_id"), null)));
                }
            }
        } catch (Exception e) {
            LOG.trace("Exception in log: ", e);
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(fname, description, created, visible, city_id) VALUES (?, ?, ?, ?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
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
            LOG.trace("Exception in log: ", e);
        }
        return post;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET fname = ?, description = ?, created = ?, visible = ?, city_id = ? WHERE id = ?"
             )) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.setInt(6, post.getId());
            ps.execute();
        } catch (SQLException e) {
            LOG.trace("Exception in log: ", e);
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"), it.getString("fname"), it.getString("description"),
                            it.getTimestamp("created").toLocalDateTime(), it.getBoolean("visible"),
                            new City(it.getInt("city_id"), null));
                }
            }
        } catch (Exception e) {
            LOG.trace("Exception in log: ", e);
        }
        return null;
    }
}
