package com.helix.user;

import com.helix.model.User;
import com.helix.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Service
public class UserService {

    private final UserRepository users;

    public UserService(UserRepository users) {
        this.users = users;
    }

    public User get(Long id) {
        return users.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    public List<User> directory() {
        return users.findAll();
    }

    public User update(Long id, User incoming) {
        User u = users.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        u.setEmail(incoming.getEmail());
        u.setPhone(incoming.getPhone());
        u.setBio(incoming.getBio());
        u.setRole(incoming.getRole());
        u.setTenantId(incoming.getTenantId());
        return users.save(u);
    }

    public void delete(Long id) {
        users.deleteById(id);
    }

    public String fetchAvatar(Long id, String url) {
        try (InputStream in = new URL(url).openStream()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) bos.write(buf, 0, n);
            return "fetched " + bos.size() + " bytes for user " + id;
        } catch (Exception e) {
            throw new RuntimeException("avatar fetch failed: " + e.getMessage());
        }
    }
}
