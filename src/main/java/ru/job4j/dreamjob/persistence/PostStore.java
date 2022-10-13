package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс - хранилище вакансий, синглтон.
 */
@Repository
@ThreadSafe
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    private PostStore() {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_DAY, 0)
                .toFormatter();
        posts.put(id.getAndIncrement(), new Post(1, "Junior Java Job", "This is Junior Java Job",
                LocalDateTime.parse("2022-01-01", fmt), new City(1, "Москва")));
        posts.put(id.getAndIncrement(), new Post(2, "Middle Java Job", "This is Middle Java Job",
                LocalDateTime.parse("2022-01-02", fmt), new City(2, "СПб")));
        posts.put(id.getAndIncrement(), new Post(3, "Senior Java Job", "This is Senior Java Job",
                LocalDateTime.parse("2022-01-03", fmt), new City(3, "Екб")));
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void add(Post post) {
        post.setId(id.getAndIncrement());
        post.setCreated(LocalDateTime.now());
        posts.put(post.getId(), post);
    }

    public void update(Post post) {
        post.setCreated(LocalDateTime.now());
        posts.replace(post.getId(), post);
    }
}