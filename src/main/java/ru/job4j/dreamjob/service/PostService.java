package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.store.PostDBStore;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@ThreadSafe
public class PostService {
    private final PostDBStore store;

    public PostService(PostDBStore store) {
        this.store = store;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void add(Post post) {
        post.setCreated(LocalDateTime.now());
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }
}
