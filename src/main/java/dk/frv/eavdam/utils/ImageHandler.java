package dk.frv.eavdam.utils;

import dk.frv.eavdam.data.TimeslotReservation;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.List;

public class ImageHandler {

    public static Image getTimeslotImage(int width, int height, List<TimeslotReservation> timeslotReservations) {
 
		//int width = 400;
		//int height = 15;

		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bimage.createGraphics();

		g2d.setColor(Color.black);		
		g2d.fillRect(0, 0, width, height);  // 50, 0..
		
		double divider = 2250 / width;
			
		g2d.setColor(Color.red);			
		if (timeslotReservations != null) {
			for (TimeslotReservation timeslotReservation : timeslotReservations) {
				int startslot = timeslotReservation.getStartslot();
				int blockSize = timeslotReservation.getBlockSize();
				int increment = timeslotReservation.getIncrement();
				if (increment == 0) {
					for (int i=0; i<blockSize; i++) {
						g2d.drawLine((int) Math.round((startslot+i)/divider), 0, (int) Math.round((startslot+i)/divider), height);
						if ((int) Math.round((startslot+i)/divider)+1 < width) {
							g2d.drawLine((int) Math.round((startslot+i)/divider)+1, 0, (int) Math.round((startslot+i)/divider)+1, height);				
						}
					}
				} else if (increment > 0) {
					int i = 0;
					while (i*increment <= 2249) {							
						for (int j=0; j<blockSize; j++) {
							g2d.drawLine((int) Math.round((startslot+j+(i*increment))/divider), 0, (int) Math.round((startslot+j+(i*increment))/divider), height);
							if ((int) Math.round((startslot+j+(i*increment))/divider)+1 < width) {
								g2d.drawLine((int) Math.round((startslot+j+(i*increment))/divider)+1, 0, (int) Math.round((startslot+j+(i*increment))/divider)+1, height);
							}
						}
						i++;
					}					
				}
			}
		}	
	
		g2d.dispose();
	
		return bimage;
    
	}
 
    public static Image getTimeslotImage(int width, int height, boolean[] blockReservations) {
 
		//int width = 400;
		//int height = 15;

		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bimage.createGraphics();

		g2d.setColor(Color.black);		
		g2d.fillRect(0, 0, width, height);  // 50, 0..
		
		double divider = 2250 / width;
			
		g2d.setColor(Color.red);			
		if (blockReservations.length == 2250) {
			for (int i=0; i<blockReservations.length; i++) {
				boolean reserved =  blockReservations[i];
				if (reserved) {						
					g2d.drawLine((int) Math.round(i/divider), 0, (int) Math.round(i/divider), height);
					if ((int) Math.round(i/divider)+1 < width) {
						g2d.drawLine((int) Math.round(i/divider)+1, 0, (int) Math.round(i/divider)+1, height);
					}					
				}
			}
		}	
	
		g2d.dispose();
	
		return bimage;
    
	} 
 
}
        