package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.*;

/**
 * Created by fabioros on 15/01/16.
 */
public class ActorRefLocal <T extends Message> extends AbsActorRef<T> {


    private T message;
    private ActorRef to;

    public ActorRefLocal(ActorSystem system){
        super((AbsActorSystem) system);
    }

    @Override
    public void send(T message, ActorRef to) {
        ((AbsActor<T>)system.match(to)).enqueueMessage(message, this);
    }


}

