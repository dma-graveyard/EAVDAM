package dk.frv.eavdam.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dk.frv.eavdam.data.AISDatalinkCheckArea;
import dk.frv.eavdam.data.AISDatalinkCheckResult;
import dk.frv.eavdam.data.AISFixedStationData;
import dk.frv.eavdam.data.ActiveStation;
import dk.frv.eavdam.data.EAVDAMData;
import dk.frv.eavdam.data.EAVDAMUser;
import dk.frv.eavdam.data.OtherUserStations;
import dk.frv.eavdam.data.Simulation;
import dk.frv.eavdam.healthcheck.PointInPolygon;
import dk.frv.eavdam.io.AISDatalinkCheckListener;

public class HealthCheckHandler {

	public static final int TRANSMISSION_COVERAGE = 1;
	public static final int INTERFERENCE_COVERAGE = 2;
	EAVDAMData data = null;
	
	public static int numberOfFrequencies = 2;
	public static int numberOfSlotsPerFrequency = 2250;
	
	public HealthCheckHandler(EAVDAMData data){
		this.data = data;
	}
	
	public AISDatalinkCheckResult startAISDatalinkCheck(AISDatalinkCheckListener listener, boolean checkRule1, boolean checkRule2, boolean checkRule3, boolean checkRule4, boolean checkRule5, boolean checkRule6, boolean checkRule7, double topLeftLatitude, double topLeftLongitude, double lowerRightLatitude, double lowerRightLongitude, double resolution){
		
		
		
		return null;
	}
	
	/**
	 * 
	 * Gets the stations and checks the bandwith of the given point. The point is the top left corner of the area.
	 * 
	 * @param lat TOP LEFT Lat point of the area
	 * @param lon TOP LEFT Lon point of the area
	 * @return
	 */
	public AISDatalinkCheckResult checkAISDatalinkAtPoint(double lat, double lon, double resolution){
		if(this.data == null){
			
			this.data = DBHandler.getData(); 
		}
		
		double[] point = {lat, lon};
		
		EAVDAMData filtered = getStationsAtPoint(data, point);
		AISDatalinkCheckResult result = new AISDatalinkCheckResult();
		

		
		if(filtered == null) return null;

		Set<String> reservationsA = new HashSet<String>();
		Set<String> reservationsB = new HashSet<String>();
		
		//Check all active stations
		if(filtered.getActiveStations() != null){
			for(ActiveStation as : filtered.getActiveStations()){
				if(as.getStations() != null){
					for(AISFixedStationData s : as.getStations()){
						if(s.getFATDMAChannelA() != null){
							for(Integer a : s.getReservedBlocksForChannelA()){
								reservationsA.add(a.intValue()+"");
							}
						}
						
						if(s.getFATDMAChannelB() != null){
							for(Integer a : s.getReservedBlocksForChannelB()){
								reservationsB.add(a.intValue()+"");
							}
						}
					}
				}
				
				
			}
		}
		System.out.println("Checked active stations..");
		//TODO Is there a need to check proposals also? What to do to the planned stations?
		
		if(filtered.getOtherUsersStations() != null){
			for(OtherUserStations other : filtered.getOtherUsersStations()){
				if(other.getStations() != null){
					for(ActiveStation as : other.getStations()){
						if(as.getStations() != null){
							for(AISFixedStationData s : as.getStations()){
								if(s.getFATDMAChannelA() != null){
									for(Integer a : s.getReservedBlocksForChannelA()){
										reservationsA.add(a.intValue()+"");
									}
								}
								
								if(s.getFATDMAChannelB() != null){
									for(Integer a : s.getReservedBlocksForChannelB()){
										reservationsB.add(a.intValue()+"");
									}
								}
							}
						}
					}
				}
			}
		}
		

		//Get the reservation %
		double percentage = 1.0*(reservationsA.size()+reservationsB.size())/(numberOfFrequencies*numberOfSlotsPerFrequency);
		
		AISDatalinkCheckArea area = new AISDatalinkCheckArea(lat, lon, getIncrementedLatitude(resolution, lat), getIncrementedLongitude(resolution, lon), percentage);
		List<AISDatalinkCheckArea> areas = new ArrayList<AISDatalinkCheckArea>();
		areas.add(area);
		result.setAreas(areas);
		
		return result;
	}
	
	
	/**
	 * Gets the stations with the coverage at the given point. Both interference coverage and transmission coverage are checked. If only one of those is needed, use getStationsAtPoint(EAVDAMData data, double[] point, int coverageType).
	 * 
	 * @param data The EAVDAMData object that holds all the stations and their covarege areas.
	 * @param point Array of two double values where point[0] = lat and point[1] = lon.
	 * @return EAVDAMData object that is filtered to only contain the stations which have a coverage area overlapping the point.
	 */
	public static EAVDAMData getStationsAtPoint(EAVDAMData data, double[] point){
		EAVDAMData filtered = new EAVDAMData();
		
		if(data == null) return filtered;
		
		
		if(data.getActiveStations() != null){
			List<ActiveStation> activeStations = new ArrayList<ActiveStation>();
			for(ActiveStation as : data.getActiveStations()){
				List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();

				for(AISFixedStationData s : as.getStations()){
//					System.out.println("Checking: "+s.getLat()+";"+s.getLon());
//					if(s.getLat() == 60 && s.getLon() == 24) System.out.println("\t"+s.getInterferenceCoverage().getCoveragePoints()+" | "+s.getTransmissionCoverage().getCoveragePoints());
					if(PointInPolygon.isPointInPolygon(s.getInterferenceCoverage().getCoveragePoints(), point)){
//						System.out.println("ADDED: (I)"+s.getLat()+";"+s.getLon());
						stations.add(s);
					}else if(PointInPolygon.isPointInPolygon(s.getTransmissionCoverage().getCoveragePoints(), point)){
//						System.out.println("ADDED (T): "+s.getLat()+";"+s.getLon());
						stations.add(s);
					}
				}
				
//				System.out.println("Stations: "+stations.size());
				ActiveStation a = new ActiveStation();
				if(stations.size() > 0){
					a.setStations(stations);
					activeStations.add(a);
				}else{
					a.setStations(null);
				}
	
				if(as.getProposals() != null){
					boolean addedProposal = false;
					for(EAVDAMUser u : as.getProposals().keySet()){
						if(PointInPolygon.isPointInPolygon(as.getProposals().get(u).getInterferenceCoverage().getCoveragePoints(), point)){
							Map<EAVDAMUser, AISFixedStationData> proposals = new HashMap<EAVDAMUser, AISFixedStationData>();
							proposals.put(u, as.getProposals().get(u));
							a.setProposals(proposals);
							addedProposal = true;
						}else if(PointInPolygon.isPointInPolygon(as.getProposals().get(u).getTransmissionCoverage().getCoveragePoints(), point)){
							Map<EAVDAMUser, AISFixedStationData> proposals = new HashMap<EAVDAMUser, AISFixedStationData>();
							proposals.put(u, as.getProposals().get(u));
							a.setProposals(proposals);
							addedProposal = true;
						}
					}
					
					if(as.getStations() != null && as.getStations().size() == 0 && stations.size() == 0){
						a.setStations(stations);
					}
					
					if(addedProposal)
						activeStations.add(a);
				}
				

			}
			
//			System.out.println("Active stations checked. Result: "+activeStations.size());
			filtered.setActiveStations(activeStations);
		}
		
		if(data.getOtherUsersStations() != null){
			List<OtherUserStations> others = new ArrayList<OtherUserStations>();
			for(OtherUserStations other : data.getOtherUsersStations()){
				List<ActiveStation> otherActiveStations = new ArrayList<ActiveStation>();
				for(ActiveStation as : other.getStations()){
					List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();
					
					for(AISFixedStationData s : as.getStations()){
						if(PointInPolygon.isPointInPolygon(s.getInterferenceCoverage().getCoveragePoints(), point)){
//							System.out.println("ADDED OTHER (I): "+s.getLat()+";"+s.getLon());
							stations.add(s);
						}else if(PointInPolygon.isPointInPolygon(s.getTransmissionCoverage().getCoveragePoints(), point)){
//							System.out.println("ADDED OTHER (T): "+s.getLat()+";"+s.getLon());
							stations.add(s);
						}
					}
					
					ActiveStation a = new ActiveStation();
					if(stations.size() > 0){
						a.setStations(stations);
					}else if(as.getStations() != null && as.getStations().size() == 0 && stations.size() == 0){
						a.setStations(stations);
					}else{
						a.setStations(null);
					}
					
					otherActiveStations.add(a);
					
				}
				
				OtherUserStations o = new OtherUserStations();
				o.setUser(other.getUser());
				o.setStations(otherActiveStations);
				others.add(o);
				
			}
			
			filtered.setOtherUsersStations(others);
		}
		
		
		if(data.getSimulatedStations() != null){
			List<Simulation> simulations = new ArrayList<Simulation>();
			
			for(Simulation sim : data.getSimulatedStations()){
				List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();
				
				for(AISFixedStationData s : sim.getStations()){
					if(PointInPolygon.isPointInPolygon(s.getInterferenceCoverage().getCoveragePoints(), point)){
						stations.add(s);
					}else if(PointInPolygon.isPointInPolygon(s.getTransmissionCoverage().getCoveragePoints(), point)){
						stations.add(s);
					}
				}
				
				Simulation s = new Simulation();
				s.setName(sim.getName());
				
				if(stations.size() > 0){
					s.setStations(stations);
				}else if(sim.getStations() != null && sim.getStations().size() == 0 && stations.size() == 0){
					s.setStations(stations);
				}else{
					s.setStations(null);
				}
	
				simulations.add(s);
			}
			
			filtered.setSimulatedStations(simulations);
		}

		
		return filtered;
	}
	
	
	
	/**
	 * Gets the stations with the coverage at the given point. Both interference coverage and transmission coverage are checked. If only one of those is needed, use getStationsAtPoint(EAVDAMData data, double[] point, int coverageType).
	 * 
	 * @param data The EAVDAMData object that holds all the stations and their covarege areas.
	 * @param point Array of two double values where point[0] = lat and point[1] = lon.
	 * @param coverageType Type of coverage. Use HealthCheckHandler.TRANSMISSION_COVERAGE or HealthCheckHandler.INTERFERENCE_COVERAGE  
	 * @return List of stations with coverage at the given point.
	 */
	public static EAVDAMData getStationsAtPoint(EAVDAMData data, double[] point, int coverageType){
		if(coverageType <= 0){
			
			System.err.println("Coverage Type ("+coverageType+") not supported. Use values "+TRANSMISSION_COVERAGE+" or "+INTERFERENCE_COVERAGE+"! Getting results for both...");
			return getStationsAtPoint(data, point);
		}
		
		EAVDAMData filtered = new EAVDAMData();
		
		
		List<ActiveStation> activeStations = new ArrayList<ActiveStation>();
		for(ActiveStation as : data.getActiveStations()){
			List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();
			
			for(AISFixedStationData s : as.getStations()){
				if(coverageType == INTERFERENCE_COVERAGE && PointInPolygon.isPointInPolygon(s.getInterferenceCoverage().getCoveragePoints(), point)){
					stations.add(s);
				}else if(coverageType == TRANSMISSION_COVERAGE && PointInPolygon.isPointInPolygon(s.getTransmissionCoverage().getCoveragePoints(), point)){
					stations.add(s);
				}
			}
			
			ActiveStation a = new ActiveStation();
			if(stations.size() > 0){
				a.setStations(stations);
			}else if(as.getStations() != null && as.getStations().size() == 0 && stations.size() == 0){
				a.setStations(stations);
			}else{
				a.setStations(null);
			}

			activeStations.add(a);
	
			for(EAVDAMUser u : as.getProposals().keySet()){
				if(coverageType == INTERFERENCE_COVERAGE && PointInPolygon.isPointInPolygon(as.getProposals().get(u).getInterferenceCoverage().getCoveragePoints(), point)){
					Map<EAVDAMUser, AISFixedStationData> proposals = new HashMap<EAVDAMUser, AISFixedStationData>();
					proposals.put(u, as.getProposals().get(u));
					a.setProposals(proposals);

				}else if(coverageType == TRANSMISSION_COVERAGE && PointInPolygon.isPointInPolygon(as.getProposals().get(u).getTransmissionCoverage().getCoveragePoints(), point)){
					Map<EAVDAMUser, AISFixedStationData> proposals = new HashMap<EAVDAMUser, AISFixedStationData>();
					proposals.put(u, as.getProposals().get(u));
					a.setProposals(proposals);
				}
			}
				
			
		}
		
		List<OtherUserStations> others = new ArrayList<OtherUserStations>();
		for(OtherUserStations other : data.getOtherUsersStations()){
			List<ActiveStation> otherActiveStations = new ArrayList<ActiveStation>();
			for(ActiveStation as : other.getStations()){
				List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();
				
				for(AISFixedStationData s : as.getStations()){
					if(coverageType == INTERFERENCE_COVERAGE && PointInPolygon.isPointInPolygon(s.getInterferenceCoverage().getCoveragePoints(), point)){
						stations.add(s);
					}else if(coverageType == TRANSMISSION_COVERAGE && PointInPolygon.isPointInPolygon(s.getTransmissionCoverage().getCoveragePoints(), point)){
						stations.add(s);
					}
				}
				
				ActiveStation a = new ActiveStation();
				if(stations.size() > 0){
					a.setStations(stations);
				}else if(as.getStations() != null && as.getStations().size() == 0 && stations.size() == 0){
					a.setStations(stations);
				}else{
					a.setStations(null);
				}
				
				otherActiveStations.add(a);
				
			}
			
			OtherUserStations o = new OtherUserStations();
			o.setUser(other.getUser());
			o.setStations(otherActiveStations);
			others.add(o);
			
		}
		
		filtered.setOtherUsersStations(others);
		
		List<Simulation> simulations = new ArrayList<Simulation>();
		for(Simulation sim : data.getSimulatedStations()){
			List<AISFixedStationData> stations = new ArrayList<AISFixedStationData>();
			
			for(AISFixedStationData s : sim.getStations()){
				if(coverageType == INTERFERENCE_COVERAGE && PointInPolygon.isPointInPolygon(s.getInterferenceCoverage().getCoveragePoints(), point)){
					stations.add(s);
				}else if(coverageType == TRANSMISSION_COVERAGE && PointInPolygon.isPointInPolygon(s.getTransmissionCoverage().getCoveragePoints(), point)){
					stations.add(s);
				}
			}
			
			Simulation s = new Simulation();
			s.setName(sim.getName());
			
			if(stations.size() > 0){
				s.setStations(stations);
			}else if(sim.getStations() != null && sim.getStations().size() == 0 && stations.size() == 0){
				s.setStations(stations);
			}else{
				s.setStations(null);
			}

			simulations.add(s);
		}
		
		filtered.setSimulatedStations(simulations);
		
		
		return filtered;
	}
	
	private static double getIncrementedLongitude(double resolution, double startLon){
// 		 System.out.println(startLon+" | "+resolution+"/(60/"+Math.cos(startLon)+") + "+startLon+" = "+(1.0*resolution/(60.0/Math.cos(startLon)) + startLon));
		 double dres = 1.0*resolution/(60.0/Math.cos(startLon));
		 
		 return startLon + dres;
	}
	
	private static double getIncrementedLatitude(double resolution, double startLat){
//		System.out.println(startLat+" | "+resolution+"/(60/"+Math.cos(startLat)+") + "+startLat+" = "+(1.0*resolution/(60.0/Math.cos(startLat)) + startLat));
		return 1.0*resolution/(60.0/Math.cos(startLat)) + startLat;
	}
	
	public static void isPolygonIntersection(){
		
	}
}
