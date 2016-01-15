package it.unipd.math.pcd.actors.impl.MyTestClasses;

import it.unipd.math.pcd.actors.*;
import it.unipd.math.pcd.actors.impl.ActorSystemImpl;

/**
 * Created by fabioros on 15/01/16.
 */
public class mainClassForTest {
    public static void main(String[] args) {
        ActorSystem as=new ActorSystemImpl();

        MyCounterActor attore1 = new MyCounterActor();
        //as.actorOf(attore1);

    }
}
