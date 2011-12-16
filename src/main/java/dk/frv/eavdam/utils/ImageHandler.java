/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.utils;

import dk.frv.eavdam.data.TimeslotReservation;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.util.List;

public class ImageHandler {

    public static Image getTimeslotImage(int width, int height, List<TimeslotReservation> timeslotReservations) {
 
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
				List<Integer> blocks = FATDMAUtils.getBlocks(new Integer(startslot), new Integer(blockSize), new Integer(increment));
				for (Integer block : blocks) {
					g2d.drawLine((int) Math.round(block.intValue()/divider), 0, (int) Math.round(block.intValue()/divider), height);
					if ((int) Math.round(block.intValue()/divider)+1 < width) {
						g2d.drawLine((int) Math.round(block.intValue()/divider)+1, 0, (int) Math.round(block.intValue()/divider)+1, height);
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
        