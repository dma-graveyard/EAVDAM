package dk.frv.eavdam.utils;

import com.bbn.openmap.gui.OpenMapFrame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import net.sf.image4j.codec.ico.ICODecoder;

public class IconChanger {

	public static void changeToEAVDAMIcon(OpenMapFrame openMapFrame) {
		try {
			List<BufferedImage> image = ICODecoder.read(new File("share/data/images/EAVDAM.ico"));
			openMapFrame.setIconImages(image);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 		
	}	

}