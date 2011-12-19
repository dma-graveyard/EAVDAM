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
package dk.frv.eavdam.layers;

import com.bbn.openmap.layer.location.ByteRasterLocation;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.EAVDAMUser;
import java.util.ArrayList;

/**
 * Class for presenting one AIS station state on the map as a byte raster image.
 */
public class OMBaseStation extends ByteRasterLocation {

	private static final long serialVersionUID = 1L;

	private ArrayList<double[]> transmitCoverageArea;
	private ArrayList<double[]> receiveCoverageArea;
	private ArrayList<double[]> interferenceCoverageArea;
	private Object datasetSource;
	private EAVDAMUser owner;
	private AISFixedStationData stationData;

	public OMBaseStation(Object datasetSource, AISFixedStationData stationData, EAVDAMUser owner, byte[] bytearr) {	    	    
		super(stationData.getLat(), stationData.getLon(), stationData.getStationName(), bytearr);		
		this.datasetSource = datasetSource;
		this.stationData = stationData;		
		this.owner = owner;
        this.setShowName(false);
	}
	
	public Object getDatasetSource() {
		return datasetSource;
	}
	
	public void setDatasetSource(Object datasetSource) {
		this.datasetSource = datasetSource;
	}
	
	public AISFixedStationData getStationData() {
		return stationData;
	}

	public void setStationData(AISFixedStationData stationData) {
		this.stationData = stationData;
	}

	public ArrayList<double[]> getTransmitCoverageArea() {
		return transmitCoverageArea;
	}

	public void setTransmitCoverageArea(ArrayList<double[]> transmitCoverageArea) {
		this.transmitCoverageArea = transmitCoverageArea;
	}
	
	public ArrayList<double[]> getReceiveCoverageArea() {
		return receiveCoverageArea;
	}

	public void setReceiveCoverageArea(ArrayList<double[]> receiveCoverageArea) {
		this.receiveCoverageArea = receiveCoverageArea;
	}

	public ArrayList<double[]> getInterferenceCoverageArea() {
		return interferenceCoverageArea;
	}

	public void setInterferenceCoverageArea(ArrayList<double[]> interferenceCoverageArea) {
		this.interferenceCoverageArea = interferenceCoverageArea;
	}	
	
	public EAVDAMUser getOwner() {
		return owner;
	}
	
	public void setOwner(EAVDAMUser owner) {
		this.owner = owner;
	}

}