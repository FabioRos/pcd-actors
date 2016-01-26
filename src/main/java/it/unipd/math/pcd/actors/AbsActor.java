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
import it.unipd.math.pcd.actors.impl.MessageWithSender;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Defines common properties of all actors.
 *
 * @author Fabio Ros
 * @version 1.1
 * @since 1.1
 */
public abstract class AbsActor<T extends Message> implements Actor<T>, Serializable {

    /**
     * Self-reference of the actor
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message
     */
    protected ActorRef<T> sender;

    /**
     * Mailbox implemented using a thread safe Queue    ADDED
     */
    private transient MailBoxDaemon mailBox;

    /**
     * Set true if the ActorSystem kill the process ADDED
     */
    private volatile boolean isStopped;


    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    public final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    /**
     * Constructor
     */
    public AbsActor(){
        mailBox= new MailBoxDaemon();
        isStopped = false;
    }


    public synchronized boolean isStopped(){
        return isStopped;
    }

    public void stop() {
        synchronized (this) {
            if (isStopped) {
                throw new NoSuchActorException();
            }
            this.isStopped = true;
        }
    }

    public void pushMessage(MessageWithSender<T> message) {
        mailBox.enqueueMessage(message);
    }

    //Mailbox inner class

    /**
     * The mailbox of the actor is a Daemon
     */
    protected class MailBoxDaemon extends Thread{

        private ConcurrentLinkedQueue<MessageWithSender<T>> private_mailbox;

        public MailBoxDaemon(){
            private_mailbox=new ConcurrentLinkedQueue<>();
            setDaemon(true);
            this.start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (this) {
                        if (private_mailbox.isEmpty())
                            this.wait();
                    }
                    while (!private_mailbox.isEmpty()) {
                        processMessage();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * This method take the first message of the mailbox, save sender reference and call the receive message
         */
        private void processMessage(){
            synchronized (private_mailbox) {
                MessageWithSender msg = private_mailbox.poll();
                if (msg != null) {
                    sender = (ActorRef<T>)(msg.getSender());
                    receive((T) msg.getMessage());
                }
            }
        }

        /**
         * This method enqueue the  {@code message} into current actor's mailbox
         * @param message the message to enqueue in the mailbox
         */
        public synchronized void enqueueMessage(MessageWithSender<T> message){
                if (isStopped)
                    throw new NoSuchActorException();

                private_mailbox.add(message);
                sender = message.getSender();
                mailBox.notify();

        }

    }// MAILBOX_DAEMON CLASS END

    /**
     * Final users must implements receive(T message);
     */
}// ABS_ACTOR CLASS END
