package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;


/**
 * Defines local implementation of AbsActorRef.
 *
 * @author Fabio Ros
 * @version 1.1
 * @since 1.1
 */

public class ActorRefLocal<T extends Message> extends AbsActorRef<T> {


    public ActorRefLocal(ActorSystem system){
        super(system);
    }


    /**
     * @param message The message to send
     * @param to The actor to which sending the message
     */
    @Override
    public void send(T message, ActorRef to) {
        ((AbsActor<T>)system.getActorFromActorRef(to)).pushMessage(new MessageWithSender<>(message, this));
    }


}

