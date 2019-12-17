package os_ex_08_4.meeting;

import java.util.concurrent.Semaphore;

import os_ex_08.Arrival;
import os_ex_08.Meeting;

/** A meeting for two.
 */
public class ForTwo implements Meeting {
    /** Token with which the participants announce their arrival.
     */
    protected class Token implements Arrival {
	/** Identification of the token owner.
	 */
	protected int who;

	/** Construct a token for the owner with the given id.
	 */
	protected Token(int id) {
	    who = id;
	}

	// Implemented
	public void announce() throws InterruptedException {
	    arrivalOf(who); // Access to outer class
	}
    }

    /** Indication of permission for each participant.
     */
    protected Semaphore[] continuePermits = null;

    /** Announce the arrival of a participant.
     */
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
	continuePermits = new Semaphore[2];
	continuePermits[0] = new Semaphore(2-2);
	continuePermits[1] = new Semaphore(2-2);
	Arrival[] notificators = new Arrival[2];
	notificators[0] = new Token(0);
	notificators[1] = new Token(1);
	return notificators;
    }
}
