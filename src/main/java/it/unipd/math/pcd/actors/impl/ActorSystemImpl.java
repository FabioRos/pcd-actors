package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.ActorSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fabioros on 18/01/16.
 */
public class ActorSystemImpl extends AbsActorSystem {


    /**
     * the constructor creates inizialize e to a new cached thread pool in order to execute all MailboxManager
     */
    public ActorSystemImpl() {
    }


    /**
     * create new ActorRef with the selected mode
     * @param mode sets Actor mode local or remote
     * @return ActorRef
     */
    @Override
    protected ActorRef createActorReference(ActorMode mode) {
        if(mode == ActorMode.LOCAL) return new ActorRefLocal(this);
        else
            throw new IllegalArgumentException();
    }


}
