package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.ActorSystem;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by fabioros on 18/01/16.
 */
public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {

    protected final AbsActorSystem system;

    public AbsActorRef(ActorSystem system){ this.system = (AbsActorSystem)system; }


    public void execute(Runnable r){
        ((AbsActorSystem)system).systemExecute(r);
    }


    @Override
    public int compareTo(ActorRef o) {
        return (this == o)? 0:1;
    }
}
