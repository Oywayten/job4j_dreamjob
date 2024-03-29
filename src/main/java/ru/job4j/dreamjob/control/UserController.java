package ru.job4j.dreamjob.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Created by Oywayten on 15.10.2022.
 */
@Controller
@ThreadSafe
public class UserController {
    private final UserService userService;

    public UserController(UserService service) {
        userService = service;
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User(0, "Заполните поле", "Заполните поле", "Заполните поле"));
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, final RedirectAttributes redirectAttributes) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/addUser";
        }
        return "redirect:/posts";
    }

    /*
    /loginPage?fail=false
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
        public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        System.out.println(session.getId());
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }
}
