package it.unipd.math.pcd.actors.impl;

import it.unipd.math.pcd.actors.ActorRef;
import it.unipd.math.pcd.actors.Message;

/**
 * Created by fabioros on 18/01/16.
 */

public interface MessageI<T extends Message>{
    T getMessage();
    ActorRef<T> getSender();
}
