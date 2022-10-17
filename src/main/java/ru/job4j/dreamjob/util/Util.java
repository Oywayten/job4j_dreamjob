package ru.job4j.dreamjob.util;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Oywayten on 17.10.2022.
 * Утилитный класс.
 */
public final class Util {

    private Util() {
    }

    public static void setUser(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}
