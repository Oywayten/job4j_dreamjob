package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс - хранилище кандидатов, синглтон.
 */
public class CandidateStore {

    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private CandidateStore() {
        posts.put(1, new Post(1, "Ivan", "This is Junior", "1993-01-01"));
        posts.put(2, new Post(2, "Sergey", "This is Middle", "1989-01-02"));
        posts.put(3, new Post(3, "Bob", "This is Senior", "1983-01-03"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}