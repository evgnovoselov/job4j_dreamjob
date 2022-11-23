package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {
    private static final Set<String> URLS_GUEST = Set.of(
            "loginPage", "login", "registration", "success"
    );

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (hasAccessGuestTo(uri)) {
            chain.doFilter(req, resp);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, resp);
    }

    private static boolean hasAccessGuestTo(String uri) {
        boolean isAccess = false;
        for (String url : URLS_GUEST) {
            if (uri.endsWith(url)) {
                isAccess = true;
                break;
            }
        }
        return isAccess;
    }
}
