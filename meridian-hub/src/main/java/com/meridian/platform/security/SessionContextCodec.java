package com.meridian.platform.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import org.springframework.stereotype.Component;

/**
 * Serializes a {@link SessionContext} to a portable string and back so a caller
 * can carry an active context between the interactive API and the batch worker.
 */
@Component
public class SessionContextCodec {

    public String encode(SessionContext context) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(context);
            oos.flush();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException("Unable to encode context", e);
        }
    }

    public SessionContext decode(String token) {
        byte[] raw = Base64.getDecoder().decode(token);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw))) {
            return (SessionContext) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Malformed context token", e);
        }
    }
}
