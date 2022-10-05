package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс - хранилище вакансий, синглтон.
 */
public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "This is Junior Java Job", "2022-01-01"));
        posts.put(2, new Post(2, "Middle Java Job", "This is Middle Java Job", "2022-01-02"));
        posts.put(3, new Post(3, "Senior Java Job", "This is Senior Java Job", "2022-01-03"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}