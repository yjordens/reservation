package org.onlinetaskforce.common.utils;

import java.util.UUID;

/**
 * This class is initially based on a corresponding class of the LED project.
 */
public final class IdGenerator {

    private IdGenerator() {
    }

    /**
     * Retrieve a pseudo randomly generated UUID.
     *
     * @return a randomly generated UUID
     * {@inheritDoc} java.util.UUID#randomUUID()
     */
    public static String createId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
