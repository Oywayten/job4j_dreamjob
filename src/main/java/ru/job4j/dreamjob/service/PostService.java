package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.persistence.PostDBStore;

import java.util.Collection;

/**
 * Created by Oywayten on 10.10.2022.
 */
@Service
@ThreadSafe
public class PostService {
    private final PostDBStore store;

    public PostService(PostDBStore store) {
        this.store = store;
    }

    public Collection<Post> findAll() {
        return store.findAll();
    }

    public Post findById(int id) {
        return store.findById(id);
    }

    public void add(Post post) {
        int cityID = post.getCity().getId();
        store.add(post);
    }

    public void update(Post post) {
        store.update(post);
    }
}
