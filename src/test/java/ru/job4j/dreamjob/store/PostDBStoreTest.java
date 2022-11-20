package ru.job4j.dreamjob.store;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PostDBStoreTest {
    /**
     * Проверяем добавление вакансии в бд и получение его по id.
     */
    @Test
    public void whenCreatePostThenHavePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java job", "Description", new City(1, "Moscow"),
                true, LocalDateTime.now());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    /**
     * Проверяем обновление вакансии в бд.
     */
    @Test
    public void whenUpdatePostThenHaveChangedPost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java job", "Description", new City(1, "Moscow"),
                true, LocalDateTime.now());
        store.add(post);
        Post postInDb = store.findById(post.getId());
        postInDb.setName("Super Java Job");
        store.update(postInDb);
        postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo("Super Java Job");
    }
}