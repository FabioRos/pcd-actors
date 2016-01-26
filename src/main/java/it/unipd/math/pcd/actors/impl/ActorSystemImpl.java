package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.ActorSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implements AbsActorSystem.
 *
 * @author Fabio Ros
 * @version 1.0
 * @since 1.0
 */

public class ActorSystemImpl extends AbsActorSystem {


    public ActorSystemImpl() {}


    /**
     * create new ActorRef with the given {@code mode}.
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
