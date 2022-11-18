package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "We need Junior Java Developer", new City(1, "Москва"),
                true, LocalDateTime.of(2022, 11, 9, 16, 5)));
        posts.put(2, new Post(2, "Middle Java Job", "We need Middle Java Developer", new City(3, "Екб"),
                true, LocalDateTime.of(2022, 11, 9, 17, 10)));
        posts.put(3, new Post(3, "Senior Java Job", "We need Senior Java Developer", new City(2, "СПб"),
                true, LocalDateTime.of(2022, 11, 9, 18, 15)));
        id.set(3);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(Integer id) {
        return posts.get(id);
    }

    public void add(Post post) {
        post.setId(id.incrementAndGet());
        posts.put(post.getId(), post);
    }

    public void update(Post post) {
        posts.replace(post.getId(), post);
    }
}
