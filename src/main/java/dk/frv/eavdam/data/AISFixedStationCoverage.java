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
package dk.frv.eavdam.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for holding all information for AIS base station coverage area.
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public class AISFixedStationCoverage {
	
	ArrayList<double[]> coveragePoints;
	int coverageType; //1 = transmit, 2 = receiver, 3 = interference
	
	public AISFixedStationCoverage(){

	}

	
	public void addCoveragePoint(double lat, double lon){
		if(this.coveragePoints == null) coveragePoints = new ArrayList<double[]>();
		
		double[] c = new double[2];
		c[0] = lat;
		c[1] = lon;
		
		coveragePoints.add(c);
		
	}


	public ArrayList<double[]> getCoveragePoints() {
		return coveragePoints;
	}


	public void setCoveragePoints(ArrayList<double[]> coveragePoints) {
		this.coveragePoints = coveragePoints;
	}


	public int getCoverageType() {
		return coverageType;
	}


	public void setCoverageType(int coverageType) {
		this.coverageType = coverageType;
	}

}