package Models;

import java.util.Timer;
import java.util.TimerTask;

public class DailyMailTask {

	    private final Timer timer = new Timer();
	    private final int hours;

	    public DailyMailTask(int hours) {
	        this.hours = hours;
	    }

	    public void start() {
	        timer.scheduleAtFixedRate(new TimerTask() {
	            public void run() {
	                sendMail();	                
	            }
	            private void sendMail() {	                
	            	UserUpdates.mailAttendeesOfUpcomingEvents();
	            }
	        }, 60000, hours*60*60*1000);
	    }
}


