package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = List.of(
                new Post(1, "New post", "Description", new City(1, "Moscow"),
                        true, LocalDateTime.now()),
                new Post(2, "New post", "Description", new City(1, "Moscow"),
                        true, LocalDateTime.now())
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        HttpSession session = mock(HttpSession.class);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "Description", new City(1, "Moscow"),
                true, LocalDateTime.now());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}