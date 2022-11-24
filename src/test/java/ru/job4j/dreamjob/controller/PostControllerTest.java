package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PostControllerTest {
    @Test
    public void whenPostsThenPosts() {
        List<Post> posts = List.of(
                new Post(1, "New post", "Description", new City(1, "Moscow"),
                        true, LocalDateTime.now()),
                new Post(2, "New post", "Description", new City(1, "Moscow"),
                        true, LocalDateTime.now())
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        HttpSession session = mock(HttpSession.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenFormAddPostThenAddPost() {
        List<City> cities = List.of(new City(1, "Москва"), new City(2, "СПб"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formAddPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenCreatePostThenRedirectPosts() {
        City city = new City(1, "Москва");
        Post post = mock(Post.class);
        when(post.getCity()).thenReturn(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(anyInt())).thenReturn(city);
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(post);
        verify(post).setCity(city);
        verify(postService).add(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenFormUpdatePostThenUpdatePost() {
        Post post = new Post(1, "New Post", "Description", new City(1, "Москва"),
                true, LocalDateTime.now());
        List<City> cities = List.of(new City(1, "Москва"), new City(2, "СПб"));
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findById(anyInt())).thenReturn(post);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, 1, session);
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("updatePost");
    }

    @Test
    public void whenUpdatePostThenRedirectPosts() {
        City city = new City(1, "Москва");
        Post post = mock(Post.class);
        when(post.getCity()).thenReturn(city);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(anyInt())).thenReturn(city);
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(post);
        verify(post).setCity(city);
        verify(postService).update(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }
}