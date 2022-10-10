package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostStore;

import java.util.Collection;

/**
 * Created by Oywayten on 10.10.2022.
 */
public class PostService {
    private static final PostService INST = new PostService();
    private final PostStore store = PostStore.instOf();

    private PostService() {
    }

    public static PostService instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void add(Post post) {
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }
}
