package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by Oywayten on 16.10.2022.
 * Класс-фильтр производит проверку доступа.
 */
@Component
public class AuthFilter implements Filter {
    /**
     * Список постфиксов страниц разрешенных для доступа без аутентификации.
     */
    private static final Set<String> ALLOWED_PAGE_SET = Set.of(
            "loginPage",
            "login",
            "addUser",
            "registration",
            "index");

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (pageCheck(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean pageCheck(String uri) {
        return ALLOWED_PAGE_SET.stream().anyMatch(uri::endsWith);
    }
}
