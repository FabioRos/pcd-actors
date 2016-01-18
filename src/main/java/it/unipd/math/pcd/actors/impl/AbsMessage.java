package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by fabioros on 18/01/16.
 */



public final class AbsMessage<T extends Message> implements MessageI<T> {
    private final T message;
    private final ActorRef<T> sender;


    public AbsMessage(T message, ActorRef<T> sender) {
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
