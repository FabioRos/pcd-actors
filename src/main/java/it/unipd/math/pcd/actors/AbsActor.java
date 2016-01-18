/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */

/**
 * Please, insert description here.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;
import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;

import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {


    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * Mailbox implemented using a thread safe Queue
     */
    private ConcurrentLinkedQueue<Message> mailBox;

    /**
     * Set true if the ActorSystem kill the process
     */
    private volatile boolean stopped;


    //__METHODS__

    public AbsActor(){
        mailBox= new ConcurrentLinkedQueue<>();
        stopped = false;
        new MailBoxDaemon().start();
    }

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    /**
     * @param s
     * set sender of current actor equals to s
     */
    protected final void setSender(ActorRef<T> s) {
        this.sender = s;
    }


    @Override
    public void receive(T message) {
        enqueueMessage( message, null);
    }
    public void receive(T message, ActorRef sender) {
        enqueueMessage( message, sender);
    }

    public synchronized boolean isStopped() {
        return stopped;
    }

    public void stop() {
        synchronized (this) {
            if (stopped) {
                throw new NoSuchActorException();
            }
            this.stopped = true;
        }
    }



    /**
     * Add the message m to the mailbox
     * @param m message
     * @param sndr sender
     * @throws NoSuchActorException even if it try to add a message when the actor is going stop
     */
    public synchronized void enqueueMessage( Message m, ActorRef sndr){
        try {
            if (isStopped() == true)
                throw new NoSuchActorException();
            mailBox.add(m);
            sender=sndr;
            mailBox.notify();
        } catch (NoSuchActorException e) {
            e.printStackTrace();
        }
    }


    protected class MailBoxDaemon extends Thread{

        public MailBoxDaemon(){
            setDaemon(true);
        }

        @Override
        public void run() {
            try {
                if (mailBox.isEmpty())
                    synchronized (this) {
                        this.wait();
                    }
                while (!mailBox.isEmpty()){
                    processMessage();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void processMessage(){
            synchronized (mailBox) {
                Message m = mailBox.poll();
                if (m != null) {
                    //setSender(m.getSender());
                    receive((T) m);
                }
            }

        }
    }

}
