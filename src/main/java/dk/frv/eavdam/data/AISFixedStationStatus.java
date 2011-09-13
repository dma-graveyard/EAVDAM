package dk.frv.eavdam.data;

/**
 * @author ttesei
 * @version 1.0
 * @created 26-elo-2011 13:27:24
 */
public enum AISFixedStationStatus {
	/**
	 * Indicator that the station is in operative use.
	 */
	OPERATIVE,
	/**
	 * Indicator that the station is not in operative use, e.g. exists only on paper
	 * for scenario testing.
	 */
	INOPERATIVE,  // to be removed
	PLANNED,
	SIMULATED
}