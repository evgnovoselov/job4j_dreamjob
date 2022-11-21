package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@ThreadSafe
public class UserDBStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDBStore.class.getSimpleName());
    private static final String SQL_ADD_USER = "INSERT INTO users(name, email, password) VALUES (?,?,?)";
    private static final String SQL_FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     SQL_ADD_USER,
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
            result = user;
        } catch (Exception e) {
            LOGGER.error("Error UserDBStore.add User email = {}", user.getEmail());
        }
        return Optional.ofNullable(result);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    user = createUser(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error UserDBStore.findUserByEmailAndPassword with email = {} and password = {}", email, password);
        }
        return Optional.ofNullable(user);
    }

    private static User createUser(ResultSet it) throws SQLException {
        return new User(
                it.getInt("id"),
                it.getString("name"),
                it.getString("email"),
                it.getString("password")
        );
    }
}
