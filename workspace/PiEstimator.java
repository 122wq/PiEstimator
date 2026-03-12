import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class PiEstimator{
//the following code is just to jog your memory about how labels and buttons work!
//implement your Pi Es timator as described in the project. You may do it all in main below or you 
//may implement additional functions if you feel it necessary.
	//these varibles are needed for both the main and PiThread 
	//if the button has been clicked (at the start assume it is)
	boolean clicked;
	//the calculated pi
	JLabel value;
	//number of trials calculated
	JLabel trials;
	public PiEstimator()
	{
		clicked = true;
		JFrame f=new JFrame("Pi Calculation");  
	    JButton b=new JButton("Stop");  
		trials = new JLabel("Number of Trials is ");
	    JLabel example = new JLabel("Actual Pi: " + Double.toString(Math.PI));
		PiThread thread = new PiThread();
		thread.start();
		
		b.addActionListener(e -> 
		{
			//if it was clicked when it was paused. set clicked to true and resume the thread
			if(!clicked)
			{
				clicked = true;
				b.setText("Stop");
				synchronized(thread){
					thread.notify();
				}
			}
			else
			{
				//if the button was clicked when the pithread is running, make clicked false (trigger wait method in the thread) and change th text
				clicked = false;
				b.setText("Start");
				
			}
		});

		Border border = BorderFactory.createLineBorder(Color.BLUE);
		
		value = new JLabel("Calculation has not started");
		trials.setBorder(border); b.setBorder(border); example.setBorder(border); value.setBorder(border);
		f.add(example); f.add(trials); f.add(value); f.add(b); 
	    f.setSize(300,300);  
	    f.setLayout(new GridLayout(4, 1));  
		
	    f.setVisible(true); 
		long time = System.currentTimeMillis();
		while(true){
			if(System.currentTimeMillis()-time >2000){
    		        value.setText("Estimated Pi: " + thread.piEstimate);
					trials.setText("Number of trials is " + thread.numTrials);
			}
		}
	}

  	

  	class PiThread extends Thread 
	{
		volatile int numTrials;
		int trialsPassed;
		int inCircle;
		double area;
		volatile double piEstimate;
	
  	    public PiThread() {
			numTrials = 0;
			inCircle = 0;
			area = 0.0;
			
  	    }
		//precondtion: less than the 32bit interger value of trials has been run
		//postcondtion: update the pi value calculated as well as number of trials
  	    public void run() {
    		double randomX;
    		double randomY;

			
    		while (numTrials < Integer.MAX_VALUE - 1) {
				//this will trigger after the text is change to "start" 
		
				while(!clicked) {
      	        try {
					synchronized(this){
						wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Wait until the object is ready
      	    }
				numTrials++;
    		    randomX = Math.random();
    		    randomY = Math.random();
			
    		    if (randomX * randomX + randomY * randomY < 1) {
    		        inCircle++;
    		    }
			
    		    piEstimate = (4.0 * inCircle) / (numTrials + 1);
				//change the label's text
    		}
		}
	}

	public static void main(String[] args)
 	{  
		PiEstimator a = new PiEstimator();  
	}  
}
