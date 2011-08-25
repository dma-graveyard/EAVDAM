package dk.frv.eavdam;

import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMPoly;

public class AISInterferencePolygon extends OMPoly{

	private static final long serialVersionUID = -5452999099903671242L;

	public AISInterferencePolygon(double[] latlon) {
		super(latlon, OMGraphic.DECIMAL_DEGREES, OMGraphic.LINETYPE_GREATCIRCLE);
		
	}

}
