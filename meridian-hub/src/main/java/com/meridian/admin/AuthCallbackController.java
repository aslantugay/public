package com.meridian.admin;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Single sign-on landing endpoint. After the identity provider authenticates
 * the user, the browser is returned to wherever it started.
 */
@RestController
public class AuthCallbackController {

    @GetMapping("/api/auth/callback")
    public void callback(@RequestParam(name = "return_to") String returnTo,
                         HttpServletResponse response) throws IOException {
        response.sendRedirect(returnTo);
    }
}
