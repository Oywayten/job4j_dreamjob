package ru.job4j.dreamjob.persistence;

import org.junit.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by Oywayten on 15.10.2022.
 */
public class PostDBStoreTest {
    PostDBStore store = new PostDBStore(new Main().loadPool());

    @Test
    public void whenCreatePost() {
        Post post = new Post(0, "Java Job", "This is job", LocalDateTime.now(), true, new City(1, "Москва"));
        store.add(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        Post post2 = new Post(0, "New Java Job", "This is new job", LocalDateTime.now(), true, new City(2, "СПб"));
        store.update(post2);
        Post postInDb = store.findById(post2.getId());
        assertThat(postInDb.getName(), is(post2.getName()));
    }
}