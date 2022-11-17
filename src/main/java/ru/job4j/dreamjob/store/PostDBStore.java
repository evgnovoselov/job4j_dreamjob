package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Repository
@ThreadSafe
public class PostDBStore {
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            new City(it.getInt("city_id"), null),
                            it.getTimestamp("created").toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }

    public void update(Post post) {
        // TODO Написать метод
    }

    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    post = new Post(it.getInt("id"),
                            it.getString("name"),
                            it.getString("description"),
                            new City(it.getInt("city_id"), null),
                            it.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return post;
    }

    public void add(Post post) {
        // TODO Написать метод
    }
}
