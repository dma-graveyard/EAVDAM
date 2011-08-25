package dk.frv.eavdam.layers;

import java.util.ArrayList;

import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMPoly;

public class OMBaseStationReachPoly extends OMPoly{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2277892485304921004L;

	public OMBaseStationReachPoly(double[] latlon) {
		super(latlon, OMGraphic.DECIMAL_DEGREES, OMGraphic.LINETYPE_GREATCIRCLE);

	}

}
