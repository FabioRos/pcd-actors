package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Implements a message with a sender.
 *
 * @author Fabio Ros
 * @version 1.0
 * @since 1.0
 */


public final class MessageWithSender<T extends Message> implements MessageI<T> {
    private final T message;
    private final ActorRef<T> sender;


    public MessageWithSender(T message, ActorRef<T> sender) {
        this.message = message;
        this.sender = sender;
    }


    @Override
    public T getMessage() {
        return message;
    }

    @Override
    public ActorRef<T> getSender() {
        return sender;
    }
}
