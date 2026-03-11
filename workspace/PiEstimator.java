
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class PiEstimator{
//the following code is just to jog your memory about how labels and buttons work!
//implement your Pi Es timator as described in the project. You may do it all in main below or you 
//may implement additional functions if you feel it necessary.
	boolean clicked;
	JLabel value;
	JLabel trials;
	public PiEstimator()
	{
		
		clicked = true;
		JFrame f=new JFrame("Button Example");  
	    JButton b=new JButton("Stop");  
		trials = new JLabel("Number of Trials is ");
	    JLabel example = new JLabel("Actual Pi: " + Double.toString(Math.PI));
		PiThread thread = new PiThread(47000000);
		thread.start();
		
		b.addActionListener(e -> 
		{
			if(!clicked)
			{
				clicked = true;
				b.setText("Stop");
				thread.setReady();
			}
			else
			{
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
	}

  	

  	class PiThread extends Thread 
	{
		int numTrials;
		int trialsPassed;
		int inCircle;
		double area;
		boolean isReady = false;

		
      	synchronized void waitForReady(){
      	    while(!isReady) {
      	        try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Wait until the object is ready
      	    }
      	    
      	}

      	synchronized void setReady() {
			isReady = true;
      	    notifyAll(); // Notify all waiting threads that the object is ready
      	}
	
  	    public PiThread(int trials) {
			numTrials = 0;
			inCircle = 0;
			area = 0.0;
			
  	    }
	  
  	    public void run() {
    		double randomX;
    		double randomY;

    		while (true && numTrials < Integer.MAX_VALUE) {
				if (!clicked)
				{
					isReady = false;
    		    	waitForReady();
				}
				numTrials++;
    		    randomX = Math.random();
    		    randomY = Math.random();
			
    		    if (randomX * randomX + randomY * randomY < 1) {
    		        inCircle++;
    		    }
			
    		    double piEstimate = (4.0 * inCircle) / (numTrials + 1);
			
    		    SwingUtilities.invokeLater(() -> {
    		        value.setText("Estimated Pi: " + piEstimate);
					trials.setText("Number of trials is: " + numTrials);
    		    });
    		}
		}
	}

	public static void main(String[] args)
 	{  
		PiEstimator a = new PiEstimator();  
	}  
}