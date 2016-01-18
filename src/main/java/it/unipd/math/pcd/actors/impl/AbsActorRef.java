package it.unipd.math.pcd.actors.impl;

import com.sun.xml.internal.ws.message.AbstractMessageImpl;
import it.unipd.math.pcd.actors.*;

/**
 * Created by fabioros on 15/01/16.
 */
public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {
    protected final AbsActorSystem system;

    public AbsActorRef(ActorSystem system){
        this.system = (AbsActorSystem)system;
    }

    @Override
    public int compareTo(ActorRef o) {
        return (this == o) ? 0 : -1;
    }

    @Override
    public void send(T message, ActorRef to) {
         ((AbsActor<T>)(system.match(to))).enqueueMessage(message,this);
    }

    public void execute(Runnable r){//system.systemExecute(r);

    }
}
