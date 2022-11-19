package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@ThreadSafe
public class PostDBStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostDBStore.class.getSimpleName());
    private static final String SQL_SELECT_ALL_POST = "SELECT * FROM post";
    private static final String SQL_FIND_BY_ID_POST = "SELECT * FROM post WHERE id = ?";
    private static final String SQL_ADD_POST = "INSERT INTO post(name, description, city_id, visible, created) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE_POST = "UPDATE post SET name = ?, description = ?, city_id = ?, visible = ?, created = ? WHERE id = ?";
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_SELECT_ALL_POST)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(createPost(it));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error PostDBStore.findAll");
        }
        return posts;
    }

    public void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     SQL_UPDATE_POST
             )) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setBoolean(4, post.isVisible());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.setInt(6, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOGGER.error("Error PostDBStore.update id = {}", post.getId());
        }
    }

    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(SQL_FIND_BY_ID_POST)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    post = createPost(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error PostDBStore.findById id = {}", id);
        }
        return post;
    }

    public void add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     SQL_ADD_POST,
                     PreparedStatement.RETURN_GENERATED_KEYS
             )) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setInt(3, post.getCity().getId());
            ps.setBoolean(4, post.isVisible());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error PostDBStore.add id = {}", post.getId());
        }
    }

    private static Post createPost(ResultSet it) throws SQLException {
        return new Post(it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                new City(it.getInt("city_id"), null),
                it.getBoolean("visible"),
                it.getTimestamp("created").toLocalDateTime());
    }
}
