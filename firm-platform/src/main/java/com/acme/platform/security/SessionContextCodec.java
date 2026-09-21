package com.acme.platform.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import org.springframework.stereotype.Component;

/**
 * Serializes a {@link SessionContext} into a portable string and back.
 * Used by the "carry my workspace" feature so a caller can move an active
 * context between the interactive API and the batch worker without a round
 * trip to the session store.
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
            throw new IllegalStateException("Unable to encode session context", e);
        }
    }

    public SessionContext decode(String token) {
        byte[] raw = Base64.getDecoder().decode(token);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(raw))) {
            return (SessionContext) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Malformed session context token", e);
        }
    }
}
