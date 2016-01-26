package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.AbsActorSystem;
import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.ActorSystem;
import it.unipd.math.pcd.actors.Message;

/**
 * Defines common properties of an ActorRef.
 *
 * @author Fabio Ros
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActorRef<T extends Message> implements ActorRef<T> {

    protected final AbsActorSystem system;

    public AbsActorRef(ActorSystem system){ this.system = (AbsActorSystem)system; }

    @Override
    public int compareTo(ActorRef o) {
        if (this == o)
            return 0;
        return -1;
    }
}
