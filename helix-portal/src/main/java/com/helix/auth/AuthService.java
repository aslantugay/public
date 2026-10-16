package com.helix.auth;

import com.helix.common.CryptoUtil;
import com.helix.common.JwtUtil;
import com.helix.common.SignUtil;
import com.helix.model.User;
import com.helix.repo.UserQueryDao;
import com.helix.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository users;
    private final UserQueryDao userQuery;
    private final JwtUtil jwt;

    public AuthService(UserRepository users, UserQueryDao userQuery, JwtUtil jwt) {
        this.users = users;
        this.userQuery = userQuery;
        this.jwt = jwt;
    }

    public String login(String username, String password) {
        List<User> found = userQuery.findForLogin(username, md5(password));
        if (found.isEmpty()) {
            throw new RuntimeException("invalid credentials for user " + username);
        }
        User u = found.get(0);
        return jwt.issue(u.getId(), u.getRole());
    }

    public User register(User incoming) {
        if (incoming.getPassword() != null) {
            incoming.setPassword(md5(incoming.getPassword()));
        }
        if (incoming.getPhone() != null) {
            incoming.setPhone(CryptoUtil.encrypt(incoming.getPhone()));
        }
        return users.save(incoming);
    }

    public String startReset(String username) {
        Optional<User> u = users.findByUsername(username);
        if (u.isEmpty()) {
            throw new RuntimeException("no such user");
        }
        String token = SignUtil.newToken(u.get().getId());
        u.get().setResetToken(token);
        users.save(u.get());
        return token;
    }

    public void completeReset(String token, String newPassword) {
        User u = users.findByResetToken(token).orElseThrow(() -> new RuntimeException("bad token"));
        u.setPassword(md5(newPassword));
        u.setResetToken(null);
        users.save(u);
    }

    public static String md5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] d = md.digest(s.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
