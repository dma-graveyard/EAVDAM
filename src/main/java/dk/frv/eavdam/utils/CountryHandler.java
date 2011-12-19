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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for handling the conversion between countries and country codes.
 */
public class CountryHandler {

	private static List<String> countries = new ArrayList<String>();
	private static Map<String, String> countryNames = new HashMap<String, String>();
	private static Map<String, String> countryCodes = new HashMap<String, String>();	
	
	private static boolean inited = false;

	/**
	 * Gets a country name of a given country code.
	 *
	 * @param countryCode  Country code
	 * @return             Country name of the given country code
	 */
	public static String getCountryName(String countryCode) {
		if (!inited) {
			init();
		}
		if (countryCodes.containsKey(countryCode)) {
			return countryCodes.get(countryCode);
		}
		return null;
	}	

	/**
	 * Gets a country code of a given country name.
	 *
	 * @param countryName  Country name
	 * @return             Country code of the given country name
	 */	
	public static String getCountryCode(String countryName) {	
		if (!inited) {
			init();
		}
		if (countryNames.containsKey(countryName)) {
			return countryNames.get(countryName);
		}
		return null;
	}
	
	/**
	 * Gets all country names in a list.
	 *
	 * @return  All countries in a list
	 */
	public static List<String> getCountries() {	
		if (!inited) {
			init();
		}	
		return countries;
	}
	
	private static void init() {
	
		countries = new ArrayList<String>();
		countryNames = new HashMap<String, String>();
		countryCodes = new HashMap<String, String>();		

		countries.add("Afghanistan");
		countries.add("Aland Islands");
		countries.add("Albania");
		countries.add("Algeria");
		countries.add("American Samoa");
		countries.add("Andorra");
		countries.add("Angola");
		countries.add("Anguilla");
		countries.add("Antarctica");
		countries.add("Antigua and Barbuda");
		countries.add("Argentina");
		countries.add("Armenia");
		countries.add("Aruba");
		countries.add("Australia");
		countries.add("Austria");
		countries.add("Azerbaijan");
		countries.add("Bahamas");
		countries.add("Bahrain");
		countries.add("Bangladesh");
		countries.add("Barbados");
		countries.add("Belarus");
		countries.add("Belgium");
		countries.add("Belize");
		countries.add("Benin");
		countries.add("Bermuda");
		countries.add("Bhutan");
		countries.add("Bolivia, Plurinational State of");
		countries.add("Bonaire, Sint Eustatius and Saba");
		countries.add("Bosnia and Herzegovina");
		countries.add("Botswana");
		countries.add("Bouvet Island");
		countries.add("Brazil");
		countries.add("British Indian Ocean Territory");
		countries.add("Brunei Darussalam");
		countries.add("Bulgaria");
		countries.add("Burkina Faso");
		countries.add("Burundi");
		countries.add("Cambodia");
		countries.add("Cameroon");
		countries.add("Canada");
		countries.add("Cape Verde");
		countries.add("Cayman Islands");
		countries.add("Central African Republic");
		countries.add("Chad");
		countries.add("Chile");
		countries.add("China");
		countries.add("Christmas Island");
		countries.add("Cocos (Keeling) Islands");
		countries.add("Colombia");
		countries.add("Comoros");
		countries.add("Congo");
		countries.add("Congo, the Democratic Republic of the");
		countries.add("Cook Islands");
		countries.add("Costa Rica");
		countries.add("Cote d'Ivoire");
		countries.add("Croatia");
		countries.add("Cuba");
		countries.add("Curacao");
		countries.add("Cyprus");
		countries.add("Czech Republic");
		countries.add("Denmark");
		countries.add("Djibouti");
		countries.add("Dominica");
		countries.add("Dominican Republic");
		countries.add("Ecuador");
		countries.add("Egypt");
		countries.add("El Salvador");
		countries.add("Equatorial Guinea");
		countries.add("Eritrea");
		countries.add("Estonia");
		countries.add("Ethiopia");
		countries.add("Falkland Islands (Malvinas)");
		countries.add("Faroe Islands");
		countries.add("Fiji");
		countries.add("Finland");
		countries.add("France");
		countries.add("French Guiana");
		countries.add("French Polynesia");
		countries.add("French Southern Territories");
		countries.add("Gabon");
		countries.add("Gambia");
		countries.add("Georgia");
		countries.add("Germany");
		countries.add("Ghana");
		countries.add("Gibraltar");
		countries.add("Greece");
		countries.add("Greenland");
		countries.add("Grenada");
		countries.add("Guadeloupe");
		countries.add("Guam");
		countries.add("Guatemala");
		countries.add("Guernsey");
		countries.add("Guinea");
		countries.add("Guinea-Bissau");
		countries.add("Guyana");
		countries.add("Haiti");
		countries.add("Heard Island and McDonald Islands");
		countries.add("Holy See (Vatican City State)");
		countries.add("Honduras");
		countries.add("Hong Kong");
		countries.add("Hungary");
		countries.add("Iceland");
		countries.add("India");
		countries.add("Indonesia");
		countries.add("Iran, Islamic Republic of");
		countries.add("Iraq");
		countries.add("Ireland");
		countries.add("Isle of Man");
		countries.add("Israel");
		countries.add("Italy");
		countries.add("Jamaica");
		countries.add("Japan");
		countries.add("Jersey");
		countries.add("Jordan");
		countries.add("Kazakhstan");
		countries.add("Kenya");
		countries.add("Kiribati");
		countries.add("Korea, Democratic People's Republic of");
		countries.add("Korea, Republic of");
		countries.add("Kuwait");
		countries.add("Kyrgyzstan");
		countries.add("Lao People's Democratic Republic");
		countries.add("Latvia");
		countries.add("Lebanon");
		countries.add("Lesotho");
		countries.add("Liberia");
		countries.add("Libya");
		countries.add("Liechtenstein");
		countries.add("Lithuania");
		countries.add("Luxembourg");
		countries.add("Macao");
		countries.add("Macedonia, the former Yugoslav Republic of");
		countries.add("Madagascar");
		countries.add("Malawi");
		countries.add("Malaysia");
		countries.add("Maldives");
		countries.add("Mali");
		countries.add("Malta");
		countries.add("Marshall Islands");
		countries.add("Martinique");
		countries.add("Mauritania");
		countries.add("Mauritius");
		countries.add("Mayotte");
		countries.add("Mexico");
		countries.add("Micronesia, Federated States of");
		countries.add("Moldova, Republic of");
		countries.add("Monaco");
		countries.add("Mongolia");
		countries.add("Montenegro");
		countries.add("Montserrat");
		countries.add("Morocco");
		countries.add("Mozambique");
		countries.add("Myanmar");
		countries.add("Namibia");
		countries.add("Nauru");
		countries.add("Nepal");
		countries.add("Netherlands");
		countries.add("New Caledonia");
		countries.add("New Zealand");
		countries.add("Nicaragua");
		countries.add("Niger");
		countries.add("Nigeria");
		countries.add("Niue");
		countries.add("Norfolk Island");
		countries.add("Northern Mariana Islands");
		countries.add("Norway");
		countries.add("Oman");
		countries.add("Pakistan");
		countries.add("Palau");
		countries.add("Palestinian Territory, Occupied");
		countries.add("Panama");
		countries.add("Papua New Guinea");
		countries.add("Paraguay");
		countries.add("Peru");
		countries.add("Philippines");
		countries.add("Pitcairn");
		countries.add("Poland");
		countries.add("Portugal");
		countries.add("Puerto Rico");
		countries.add("Qatar");
		countries.add("Reunion");
		countries.add("Romania");
		countries.add("Russian Federation");
		countries.add("Rwanda");
		countries.add("Saint Barthelemy");
		countries.add("Saint Helena, Ascension and Tristan da Cunha");
		countries.add("Saint Kitts and Nevis");
		countries.add("Saint Lucia");
		countries.add("Saint Martin (French part)");
		countries.add("Saint Pierre and Miquelon");
		countries.add("Saint Vincent and the Grenadines");
		countries.add("Samoa");
		countries.add("San Marino");
		countries.add("Sao Tome and Principe");
		countries.add("Saudi Arabia");
		countries.add("Senegal");
		countries.add("Serbia");
		countries.add("Seychelles");
 		countries.add("Sierra Leone");
		countries.add("Singapore");
		countries.add("Sint Maarten (Dutch part)");
		countries.add("Slovakia");
		countries.add("Slovenia");
		countries.add("Solomon Islands");
		countries.add("Somalia");
		countries.add("South Africa");
		countries.add("South Georgia and the South Sandwich Islands");
		countries.add("South Sudan");
		countries.add("Spain");
		countries.add("Sri Lanka");
		countries.add("Sudan");
		countries.add("Suriname");
		countries.add("Svalbard and Jan Mayen");
		countries.add("Swaziland");
		countries.add("Sweden");
		countries.add("Switzerland");
		countries.add("Syrian Arab Republic");
		countries.add("Taiwan, Province of China");
		countries.add("Tajikistan");
		countries.add("Tanzania, United Republic of");
		countries.add("Thailand");
		countries.add("Timor-Leste");
		countries.add("Togo");
		countries.add("Tokelau");
		countries.add("Tonga");
		countries.add("Trinidad and Tobago");
		countries.add("Tunisia");
		countries.add("Turkey");
		countries.add("Turkmenistan");
		countries.add("Turks and Caicos Islands");
		countries.add("Tuvalu");
		countries.add("Uganda");
		countries.add("Ukraine");
		countries.add("United Arab Emirates");
		countries.add("United Kingdom");
		countries.add("United States");
		countries.add("United States Minor Outlying Islands");
		countries.add("Uruguay");
		countries.add("Uzbekistan");
		countries.add("Vanuatu");
		countries.add("Venezuela, Bolivarian Republic of");
		countries.add("Viet Nam");
		countries.add("Virgin Islands, British");
		countries.add("Virgin Islands, U.S.");
		countries.add("Wallis and Futuna");
		countries.add("Western Sahara");
		countries.add("Yemen");
		countries.add("Zambia");
		countries.add("Zimbabwe");
		
		countryNames.put("Afghanistan", "AF");
		countryNames.put("Aland Islands", "AX");
		countryNames.put("Albania", "AL");
		countryNames.put("Algeria", "DZ");
		countryNames.put("American Samoa", "AS");
		countryNames.put("Andorra", "AD");
		countryNames.put("Angola", "AO");
		countryNames.put("Anguilla", "AI");
		countryNames.put("Antarctica", "AQ");
		countryNames.put("Antigua and Barbuda", "AG");
		countryNames.put("Argentina", "AR");
		countryNames.put("Armenia", "AM");
		countryNames.put("Aruba", "AW");
		countryNames.put("Australia", "AU");
		countryNames.put("Austria", "AT");
		countryNames.put("Azerbaijan", "AZ");
		countryNames.put("Bahamas", "BS");
		countryNames.put("Bahrain", "BH");
		countryNames.put("Bangladesh", "BD");
		countryNames.put("Barbados", "BB");
		countryNames.put("Belarus", "BY");
		countryNames.put("Belgium", "BE");
		countryNames.put("Belize", "BZ");
		countryNames.put("Benin", "BJ");
		countryNames.put("Bermuda", "BM");
		countryNames.put("Bhutan", "BT");
		countryNames.put("Bolivia, Plurinational State of", "BO");
		countryNames.put("Bonaire, Sint Eustatius and Saba", "BQ");
		countryNames.put("Bosnia and Herzegovina", "BA");
		countryNames.put("Botswana", "BW");
		countryNames.put("Bouvet Island", "BV");
		countryNames.put("Brazil", "BR");
		countryNames.put("British Indian Ocean Territory", "IO");
		countryNames.put("Brunei Darussalam", "BN");
		countryNames.put("Bulgaria", "BG");
		countryNames.put("Burkina Faso", "BF");
		countryNames.put("Burundi", "BI");
		countryNames.put("Cambodia", "KH");
		countryNames.put("Cameroon", "CM");
		countryNames.put("Canada", "CA");
		countryNames.put("Cape Verde", "CV");
		countryNames.put("Cayman Islands", "KY");
		countryNames.put("Central African Republic", "CF");
		countryNames.put("Chad", "TD");
		countryNames.put("Chile", "CL");
		countryNames.put("China", "CN");
		countryNames.put("Christmas Island", "CX");
		countryNames.put("Cocos (Keeling) Islands", "CC");
		countryNames.put("Colombia", "CO");
		countryNames.put("Comoros", "KM");
		countryNames.put("Congo", "CG");
		countryNames.put("Congo, the Democratic Republic of the", "CD");
		countryNames.put("Cook Islands", "CK");
		countryNames.put("Costa Rica", "CR");
		countryNames.put("Cote d'Ivoire", "CI");
		countryNames.put("Croatia", "HR");
		countryNames.put("Cuba", "CU");
		countryNames.put("Curacao", "CW");
		countryNames.put("Cyprus", "CY");
		countryNames.put("Czech Republic", "CZ");
		countryNames.put("Denmark", "DK");
		countryNames.put("Djibouti", "DJ");
		countryNames.put("Dominica", "DM");
		countryNames.put("Dominican Republic", "DO");
		countryNames.put("Ecuador", "EC");
		countryNames.put("Egypt", "EG");
		countryNames.put("El Salvador", "SV");
		countryNames.put("Equatorial Guinea", "GQ");
		countryNames.put("Eritrea", "ER");
		countryNames.put("Estonia", "EE");
		countryNames.put("Ethiopia", "ET");
		countryNames.put("Falkland Islands (Malvinas)", "FK");
		countryNames.put("Faroe Islands", "FO");
		countryNames.put("Fiji", "FJ");
		countryNames.put("Finland", "FI");
		countryNames.put("France", "FR");
		countryNames.put("French Guiana", "GF");
		countryNames.put("French Polynesia", "PF");
		countryNames.put("French Southern Territories", "TF");
		countryNames.put("Gabon", "GA");
		countryNames.put("Gambia", "GM");
		countryNames.put("Georgia", "GE");
		countryNames.put("Germany", "DE");
		countryNames.put("Ghana", "GH");
		countryNames.put("Gibraltar", "GI");
		countryNames.put("Greece", "GR");
		countryNames.put("Greenland", "GL");
		countryNames.put("Grenada", "GD");
		countryNames.put("Guadeloupe", "GP");
		countryNames.put("Guam", "GU");
		countryNames.put("Guatemala", "GT");
		countryNames.put("Guernsey", "GG");
		countryNames.put("Guinea", "GN");
		countryNames.put("Guinea-Bissau", "GW");
		countryNames.put("Guyana", "GY");
		countryNames.put("Haiti", "HT");
		countryNames.put("Heard Island and McDonald Islands", "HM");
		countryNames.put("Holy See (Vatican City State)", "VA");
		countryNames.put("Honduras", "HN");
		countryNames.put("Hong Kong", "HK");
		countryNames.put("Hungary", "HU");
		countryNames.put("Iceland", "IS");
		countryNames.put("India", "IN");
		countryNames.put("Indonesia", "ID");
		countryNames.put("Iran, Islamic Republic of", "IR");
		countryNames.put("Iraq", "IQ");
		countryNames.put("Ireland", "IE");
		countryNames.put("Isle of Man", "IM");
		countryNames.put("Israel", "IL");
		countryNames.put("Italy", "IT");
		countryNames.put("Jamaica", "JM");
		countryNames.put("Japan", "JP");
		countryNames.put("Jersey", "JE");
		countryNames.put("Jordan", "JO");
		countryNames.put("Kazakhstan", "KZ");
		countryNames.put("Kenya", "KE");
		countryNames.put("Kiribati", "KI");
		countryNames.put("Korea, Democratic People's Republic of", "KP");
		countryNames.put("Korea, Republic of", "KR");
		countryNames.put("Kuwait", "KW");
		countryNames.put("Kyrgyzstan", "KG");
		countryNames.put("Lao People's Democratic Republic", "LA");
		countryNames.put("Latvia", "LV");
		countryNames.put("Lebanon", "LB");
		countryNames.put("Lesotho", "LS");
		countryNames.put("Liberia", "LR");
		countryNames.put("Libya", "LY");
		countryNames.put("Liechtenstein", "LI");
		countryNames.put("Lithuania", "LT");
		countryNames.put("Luxembourg", "LU");
		countryNames.put("Macao", "MO");
		countryNames.put("Macedonia, the former Yugoslav Republic of", "MK");
		countryNames.put("Madagascar", "MG");
		countryNames.put("Malawi", "MW");
		countryNames.put("Malaysia", "MY");
		countryNames.put("Maldives", "MV");
		countryNames.put("Mali", "ML");
		countryNames.put("Malta", "MT");
		countryNames.put("Marshall Islands", "MH");
		countryNames.put("Martinique", "MQ");
		countryNames.put("Mauritania", "MR");
		countryNames.put("Mauritius", "MU");
		countryNames.put("Mayotte", "YT");
		countryNames.put("Mexico", "MX");
		countryNames.put("Micronesia, Federated States of", "FM");
		countryNames.put("Moldova, Republic of", "MD");
		countryNames.put("Monaco", "MC");
		countryNames.put("Mongolia", "MN");
		countryNames.put("Montenegro", "ME");
		countryNames.put("Montserrat", "MS");
		countryNames.put("Morocco", "MA");
		countryNames.put("Mozambique", "MZ");
		countryNames.put("Myanmar", "MM");
		countryNames.put("Namibia", "NA");
		countryNames.put("Nauru", "NR");
		countryNames.put("Nepal", "NP");
		countryNames.put("Netherlands", "NL");
		countryNames.put("New Caledonia", "NC");
		countryNames.put("New Zealand", "NZ");
		countryNames.put("Nicaragua", "NI");
		countryNames.put("Niger", "NE");
		countryNames.put("Nigeria", "NG");
		countryNames.put("Niue", "NU");
		countryNames.put("Norfolk Island", "NF");
		countryNames.put("Northern Mariana Islands", "MP");
		countryNames.put("Norway", "NO");
		countryNames.put("Oman", "OM");
		countryNames.put("Pakistan", "PK");
		countryNames.put("Palau", "PW");
		countryNames.put("Palestinian Territory, Occupied", "PS");
		countryNames.put("Panama", "PA");
		countryNames.put("Papua New Guinea", "PG");
		countryNames.put("Paraguay", "PY");
		countryNames.put("Peru", "PE");
		countryNames.put("Philippines", "PH");
		countryNames.put("Pitcairn", "PN");
		countryNames.put("Poland", "PL");
		countryNames.put("Portugal", "PT");
		countryNames.put("Puerto Rico", "PR");
		countryNames.put("Qatar", "QA");
		countryNames.put("Reunion", "RE");
		countryNames.put("Romania", "RO");
		countryNames.put("Russian Federation", "RU");
		countryNames.put("Rwanda", "RW");
		countryNames.put("Saint Barthelemy", "BL");
		countryNames.put("Saint Helena, Ascension and Tristan da Cunha", "SH");
		countryNames.put("Saint Kitts and Nevis", "KN");
		countryNames.put("Saint Lucia", "LC");
		countryNames.put("Saint Martin (French part)", "MF");
		countryNames.put("Saint Pierre and Miquelon", "PM");
		countryNames.put("Saint Vincent and the Grenadines", "VC");
		countryNames.put("Samoa", "WS");
		countryNames.put("San Marino", "SM");
		countryNames.put("Sao Tome and Principe", "ST");
		countryNames.put("Saudi Arabia", "SA");
		countryNames.put("Senegal", "SN");
		countryNames.put("Serbia", "RS");
		countryNames.put("Seychelles", "SC");
		countryNames.put("Sierra Leone", "SL");
		countryNames.put("Singapore", "SG");
		countryNames.put("Sint Maarten (Dutch part)", "SX");
		countryNames.put("Slovakia", "SK");
		countryNames.put("Slovenia", "SI");
		countryNames.put("Solomon Islands", "SB");
		countryNames.put("Somalia", "SO");
		countryNames.put("South Africa", "ZA");
		countryNames.put("South Georgia and the South Sandwich Islands", "GS");
		countryNames.put("South Sudan", "SS");
		countryNames.put("Spain", "ES");
		countryNames.put("Sri Lanka", "LK");
		countryNames.put("Sudan", "SD");
		countryNames.put("Suriname", "SR");
		countryNames.put("Svalbard and Jan Mayen", "SJ");
		countryNames.put("Swaziland", "SZ");
		countryNames.put("Sweden", "SE");
		countryNames.put("Switzerland", "CH");
		countryNames.put("Syrian Arab Republic", "SY");
		countryNames.put("Taiwan, Province of China", "TW");
		countryNames.put("Tajikistan", "TJ");
		countryNames.put("Tanzania, United Republic of", "TZ");
		countryNames.put("Thailand", "TH");
		countryNames.put("Timor-Leste", "TL");
		countryNames.put("Togo", "TG");
		countryNames.put("Tokelau", "TK");
		countryNames.put("Tonga", "TO");
		countryNames.put("Trinidad and Tobago", "TT");
		countryNames.put("Tunisia", "TN");
		countryNames.put("Turkey", "TR");
		countryNames.put("Turkmenistan", "TM");
		countryNames.put("Turks and Caicos Islands", "TC");
		countryNames.put("Tuvalu", "TV");
		countryNames.put("Uganda", "UG");
		countryNames.put("Ukraine", "UA");
		countryNames.put("United Arab Emirates", "AE");
		countryNames.put("United Kingdom", "GB");
		countryNames.put("United States", "US");
		countryNames.put("United States Minor Outlying Islands", "UM");
		countryNames.put("Uruguay", "UY");
		countryNames.put("Uzbekistan", "UZ");
		countryNames.put("Vanuatu", "VU");
		countryNames.put("Venezuela, Bolivarian Republic of", "VE");
		countryNames.put("Viet Nam", "VN");
		countryNames.put("Virgin Islands, British", "VG");
		countryNames.put("Virgin Islands, U.S.", "VI");
		countryNames.put("Wallis and Futuna", "WF");
		countryNames.put("Western Sahara", "EH");
		countryNames.put("Yemen", "YE");
		countryNames.put("Zambia", "ZM");
		countryNames.put("Zimbabwe", "ZW");	
		
		for (Map.Entry<String, String> item : countryNames.entrySet()) {
			String countryName = item.getKey();
			String countryCode = item.getValue();
			countryCodes.put(countryCode, countryName);
		}

		inited = true;
	}	
	
}