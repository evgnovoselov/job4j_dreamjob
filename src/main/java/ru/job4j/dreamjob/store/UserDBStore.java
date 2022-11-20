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

@Repository
@ThreadSafe
public class UserDBStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDBStore.class.getSimpleName());
    private static final String SQL_ADD_USER = "INSERT INTO \"user\"(email, password) VALUES (?,?)";
    private static final String SQL_FIND_USER_BY_EMAIL_AND_PASSWORD = "";
    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public User add(User user) {
        User result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     SQL_ADD_USER,
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
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
        return result;
    }

    public User findUserByEmailAndPassword(String email, String password) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            // TODO add code.
        } catch (SQLException e) {
            LOGGER.error("Error UserDBStore.findUserByEmailAndPassword with email = {} and password = {}", email, password);
        }
        return user;
    }
}
