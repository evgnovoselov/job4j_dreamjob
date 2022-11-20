package ru.job4j.dreamjob.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

class PostDBStoreTest {

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void findById() {
    }

    @Test
    void whenCreatePostThenHavePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java job", "Description", new City(1, "Moscow"),
                true, LocalDateTime.now());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        Assertions.assertThat(postInDb.getName()).isEqualTo(post.getName());
    }
}