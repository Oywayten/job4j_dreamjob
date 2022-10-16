package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Oywayten on 15.10.2022.
 */
public class PostDBStoreTest {

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "This is job", LocalDateTime.now(), true, new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName()).isEqualTo(post.getName());
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job", "This is job", LocalDateTime.now(), true, new City(1, "Москва"));
        Post post2 = new Post(0, "New Java Job", "This is new job", LocalDateTime.now(), true, new City(2, "СПб"));
        post = store.add(post);
        post2.setId(post.getId());
        store.update(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getName()).isEqualTo(post2.getName());
    }

    @Test
    public void whenFindAll() {
        PostDBStore store = new PostDBStore(new Main().loadPool());
        Post post = new Post(0, "Java Job 15", "This is job 15", LocalDateTime.now(), true, new City(1, "Москва"));
        Post post2 = new Post(0, "New Java Job 16", "This is new job 16", LocalDateTime.now(), true, new City(2, "СПб"));
        post = store.add(post);
        post2 = store.add(post2);
        List<Post> all = store.findAll();
        assertThat(all.containsAll(List.of(post, post2))).isEqualTo(true);
    }
}