package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "We need Junior Java Developer",
                LocalDateTime.of(2022, 11, 9, 16, 5)));
        posts.put(2, new Post(2, "Middle Java Job", "We need Middle Java Developer",
                LocalDateTime.of(2022, 11, 9, 17, 10)));
        posts.put(3, new Post(3, "Senior Java Job", "We need Senior Java Developer",
                LocalDateTime.of(2022, 11, 9, 18, 15)));
        id.set(3);
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        int currentId = id.incrementAndGet();
        post.setId(currentId);
        posts.put(currentId, post);
    }
}
