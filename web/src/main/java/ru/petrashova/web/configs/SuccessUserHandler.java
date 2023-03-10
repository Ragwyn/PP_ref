package ru.petrashova.web.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.petrashova.web.models.User;

import java.io.IOException;
import java.util.Set;


@Component
// Spring Security использует объект Authentication, пользователя авторизованной сессии.
public class SuccessUserHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")){
            httpServletResponse.sendRedirect("/admin/show_all");
        }
        else if (roles.contains("ROLE_USER")) {
            int userId = (int) ((User)authentication.getPrincipal()).getId();
            httpServletResponse.sendRedirect("/user/" + userId);
        }
        else {
            httpServletResponse.sendRedirect("/");
        }
    }
}
