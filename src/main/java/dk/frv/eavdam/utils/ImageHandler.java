package dk.frv.eavdam.utils;

import dk.frv.eavdam.data.TimeslotReservation;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.List;

public class ImageHandler {

    public static Image getTimeslotImage(String title, List<TimeslotReservation> timeslotReservations) {
 
		int width = 400;
		int height = 15;

		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bimage.createGraphics();

		g2d.setColor(Color.black);		
		g2d.fillRect(50, 0, 400, 15);
		
		double divider = 2250 / 400;
			
		g2d.setColor(Color.red);			
		if (timeslotReservations != null) {
			for (TimeslotReservation timeslotReservation : timeslotReservations) {
				int startslot = timeslotReservation.getStartslot();
				int blockSize = timeslotReservation.getBlockSize();
				int increment = timeslotReservation.getIncrement();
				if (increment == 0) {
					for (int i=0; i<blockSize; i++) {
						g2d.drawLine((int) Math.round((startslot+i)/divider), 0, (int) Math.round((startslot+i)/divider), 15);
						g2d.drawLine((int) Math.round((startslot+i)/divider)+1, 0, (int) Math.round((startslot+i)/divider)+1, 15);				
					}
				} else if (increment > 0) {
					int i = 0;
					while (i*increment <= 2249) {							
						for (int j=0; j<blockSize; j++) {
							g2d.drawLine((int) Math.round((startslot+j+(i*increment))/divider), 0, (int) Math.round((startslot+j+(i*increment))/divider), 15);
							g2d.drawLine((int) Math.round((startslot+j+(i*increment))/divider)+1, 0, (int) Math.round((startslot+j+(i*increment))/divider)+1, 15);
						}
						i++;
					}					
				}
			}
		}	
	
		g2d.dispose();
	
		return bimage;
    
	}
 
}
        