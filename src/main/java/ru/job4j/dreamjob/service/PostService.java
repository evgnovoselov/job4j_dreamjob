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
    private final CityService cityService;

    public PostService(PostDBStore store, CityService cityService) {
        this.store = store;
        this.cityService = cityService;
    }

    public Collection<Post> findAll() {
        Collection<Post> posts = store.findAll();
        posts.forEach(post -> post.setCity(cityService.findById(post.getCity().getId())));
        return posts;
    }

    public Post findById(int id) {
        Post post = store.findById(id);
        post.setCity(cityService.findById(post.getCity().getId()));
        return post;
    }

    public void add(Post post) {
        post.setCreated(LocalDateTime.now());
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }
}
