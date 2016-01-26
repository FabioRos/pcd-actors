package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Defines an interface for message with a sender.
 *
 * @author Fabio Ros
 * @version 1.0
 * @since 1.0
 */

public interface MessageI<T extends Message>{
    /**
     * returns The Message
     * @return the message
     */
    T getMessage();

    /**
     * @return  the sender of the message
     */
    ActorRef<T> getSender();
}
