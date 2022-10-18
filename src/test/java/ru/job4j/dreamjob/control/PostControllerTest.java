package ru.job4j.dreamjob.control;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Oywayten on 18.10.2022.
 */
public class PostControllerTest {
    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post"),
                new Post(2, "New post")
        );
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        when(httpSession.getAttribute(any())).thenReturn(new User());
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.posts(model, httpSession);
        verify(model, times(1)).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void whenCreatePosts() {
        Post post = new Post(1, "New post");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.createPost(post);
        verify(postService, only()).add(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenUpdatePosts() {
        Post post2 = new Post(2, "Update post");
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.updatePost(post2);
        verify(postService, only()).update(post2);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void whenFormAddPost() {
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(httpSession.getAttribute(any())).thenReturn(new User());
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.addPost(model, httpSession);
        verify(model).addAttribute(any(String.class), any(User.class));
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void whenFormUpdatePost() {
        Post post = new Post(1, "New post");
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(httpSession.getAttribute(any())).thenReturn(new User());
        when(postService.findById(anyInt())).thenReturn(post);
        PostController postController = new PostController(
                postService,
                cityService
        );
        String page = postController.formUpdatePost(model, 1, httpSession);
        verify(model).addAttribute("post", post);
        assertThat(page).isEqualTo("updatePost");
    }
}