package ru.job4j.dreamjob.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Oywayten on 15.10.2022.
 */
@Repository
public class UserDBStore {
    private static final String ADD = "INSERT INTO users(fname, email, password) VALUES (?, ?, ?)";
    private static final String FIND_USER_BY_EMAIL_AND_PWD = "select * from users where email = ? and password = ?";
    private static final Logger LOG = LogManager.getLogger(UserDBStore.class);
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    private User getUser(ResultSet it) throws SQLException {
        return new User(it.getInt("id"), it.getString("fname"), it.getString("email"), it.getString("password"));
    }

    public Optional<User> add(User user) {
        Optional<User> optionalUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    optionalUser = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalUser;
    }

    public Optional<User> findUserByEmailAndPassword(String email, String pass) {
        Optional<User> optionalUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_USER_BY_EMAIL_AND_PWD)) {
            ps.setString(1, email);
            ps.setString(2, pass);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    optionalUser = Optional.of(getUser(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return optionalUser;
    }
}
