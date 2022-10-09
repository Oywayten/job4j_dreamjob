package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс - хранилище вакансий, синглтон.
 */
public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger(1);

    private PostStore() {
        posts.put(id.getAndIncrement(), new Post(1, "Junior Java Job", "This is Junior Java Job", "2022-01-01"));
        posts.put(id.getAndIncrement(), new Post(2, "Middle Java Job", "This is Middle Java Job", "2022-01-02"));
        posts.put(id.getAndIncrement(), new Post(3, "Senior Java Job", "This is Senior Java Job", "2022-01-03"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void add(Post post) {
        post.setId(id.getAndIncrement());
        post.setCreated(LocalDate.now().toString());
        posts.put(post.getId(), post);
    }

    public void update(Post post) {
        post.setCreated(LocalDate.now().toString());
        posts.replace(post.getId(), post);
    }
}