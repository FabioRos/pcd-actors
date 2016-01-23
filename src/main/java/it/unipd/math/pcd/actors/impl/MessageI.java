package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

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
