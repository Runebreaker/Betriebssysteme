package os_ex_08_4.meeting;

import os_ex_08.Arrival;
import os_ex_08.Meeting;

import java.util.concurrent.Semaphore;

public class ForN implements Meeting {

    private int amount;
    private Arrival[] notificators;
    private Semaphore[] continuePermits;

    protected class Token implements Arrival {

        protected int who;

        protected Token(int id) {
            who = id;
        }

        // Implemented
        public void announce() throws InterruptedException {
            arrivalOf(who); // Access to outer class
        }
    }

    public ForN(int amount)
    {
        this.amount = amount;
        notificators = new Arrival[amount];
        continuePermits = null;
    }

    protected void arrivalOf(int who) throws InterruptedException {
        int other = (who == 1) ? 0 : 1;
        continuePermits[other].release();
        continuePermits[who].acquire();
    }

    // Implemented
    public synchronized Arrival[] arrange() {
        if (continuePermits != null) {
            String msg = "Tried to arrange a meeting that has " +
                    "already been arranged!";
            throw new IllegalStateException(msg);
        }

        continuePermits = new Semaphore[amount];
        for(int i = 0; i < continuePermits.length; i++) {
            continuePermits[i] = new Semaphore(0);
        }

        for(int i = 0; i < notificators.length; i++)
        {
            notificators[i] = new Token(i);
        }

        return notificators;
    }
}
