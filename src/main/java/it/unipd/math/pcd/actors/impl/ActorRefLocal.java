package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

/**
 *  A local implementation of AbsActorRef
 */
public class ActorRefLocal<T extends Message> extends AbsActorRef<T> {


    public ActorRefLocal(ActorSystem system){
        super((AbsActorSystem)system);
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

