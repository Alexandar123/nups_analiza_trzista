package com.analyze.mapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.analyze.configuration.InsertRecordInDatabaseWithJdbcTemplate;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.validation.ValidationImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebNekretnineMapper {

	public static String prefix = "";// PREFIX FOR CITE
	public static String DATE = "1_1_2020"; // DATE OF DOWNLOADING DATA
	public static String NAME_OF_FILE = "webnekretnine_";
	public static String DIRECTORY = "webnekretnine_10_12_2019\\";
	public static int NUM_OF_FILE = 20;
	public static String tipOglasa;
	private static String type_of_ad = "sell";
	private static String FULL_PATH;

	public static void main(String[] args) {
		InsertRecordInDatabaseWithJdbcTemplate insert = new InsertRecordInDatabaseWithJdbcTemplate();
		ValidationImpl validation = new ValidationImpl();
		int imgCounter = 0;
		float price_per_mFloat = 0;
		int br = 0;
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\" + DIRECTORY;
		String EXTENSION = ".json";

		String FILE = "";

		FILE = prefix + NAME_OF_FILE + DATE + "_";
		FILE += 1;// Number of documents

		Instant start = Instant.now();

		System.out.println("***********************");
		FULL_PATH = BASE + FILE + EXTENSION;

		for (int i = 121; i <= 138; i++) {
			FILE = prefix + NAME_OF_FILE + DATE + "_";
			FILE += i;// Number of documents

			FULL_PATH = BASE + FILE + EXTENSION;
			System.out.println(FULL_PATH);
			try {
				readJson(FULL_PATH);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		Instant end = Instant.now();
		System.out.println("Vreme : " + Duration.between(start, end));
		System.out.println("Success");

	}

	private static boolean readJson(String FULL_PATH2) throws ParseException {
		JsonNode rootNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<AdvertiseWebNekretnine> ls = new ArrayList<AdvertiseWebNekretnine>();
		InsertRecordInDatabaseWithJdbcTemplate insert = new InsertRecordInDatabaseWithJdbcTemplate();
		int br = 0, price2 = 0, areasInt = 0, br1 = 0;
		byte[] image11 = null, image22 = null;

		// JsonArray groupObject = jsonObject.getAsJsonArray("group");
		/// POTREBNO URADITI DA IZ SVAKOG FAJLA CITA PODATKE!!!!!!
		int br2 = 0;

		try (FileReader reader = new FileReader(FULL_PATH2)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray lang = (JSONArray) jsonObject.get("address");

			System.out.println("Broj key-value pair: " + lang.size());
			String id = (String) jsonObject.get("price");

			rootNode = objectMapper.readTree(new File(FULL_PATH2));

			br = 0;
			while (rootNode.elements().hasNext() && br < lang.size()) {

				String nameOfAd = rootNode.path("address").get(br).path("name").asText();// OK
				String urlToTheAd = rootNode.path("address").get(br).path("url").asText();//
				String price = rootNode.path("address").get(br).path("price").asText();//
				String areas = rootNode.path("address").get(br).path("areas").asText();//
				String ad_published = rootNode.path("address").get(br).path("ad_pubished").asText();// OK String
				String titleOfDetailPage = rootNode.path("address").get(br).path("title").asText();//
				String description = rootNode.path("address").get(br).path("description").asText();// OK String
				String address = rootNode.path("address").get(br).path("address").asText();
				String floor = rootNode.path("address").get(br).path("floor").asText();
				String num_of_rooms = rootNode.path("address").get(br).path("num_of_rooms").asText();
				String cite = rootNode.path("address").get(br).path("city").asText();
				String state = rootNode.path("address").get(br).path("state").asText();
				String street = rootNode.path("address").get(br).path("street").asText();
				String price_per_m = rootNode.path("address").get(br).path("price_per_m").asText();

				String image1 = rootNode.path("address").get(br).path("image1").asText();
				String image2 = rootNode.path("address").get(br).path("image2").asText();

				br++;

				// Grad koji nema adresu a ima grad je validan!
				// Oglas koji nema grad ni adresu proveriti da li u name naslovu ima tih
				// podataka
				if (address.equals("") || address.contains(" m2") || address == null) {
					String grad = StringUtils.substringBefore(nameOfAd, " - ");
					grad = grad.replace("-", "");

					if (grad.contains("BEOGRAD DEDINJE") || grad.contains("Beograd Dedinje")) {
						grad = StringUtils.substringBefore(nameOfAd, " ");
					}
					cite = grad;
					address = nameOfAd;// Adresa za oglase kojima su podaci pomesani
					address = StringUtils.substringAfter(address, " - ");
				}

				// Priprema grada
				if (cite.contains(" m2")) {
					String grad = StringUtils.substringBefore(nameOfAd, " - ");
					cite = grad;
					System.out.println("Grad: " + cite);

				}
				System.out.println("Grad2: " + cite);

				// Priprema addresse
				float price_per_mFloat = 0;
				if (checkIsNumber(address) == true) { // adressa je samo broj
					String adresa = StringUtils.substringAfter(nameOfAd, ", ");

					street = adresa;

				} else {
					// Priprema zemlje
					state = "Srbija";

					// Price prepare for DB

					System.out.println("Cena pre: " + price);
					String price1 = price.replace(" €", "");// Replaced .
					price1 = price1.replace(".", "");
					if (price == null || price.equals("")) {
						price1 = "0";
					}
					if(price.equalsIgnoreCase("dogovor")) {
						price1="-3";
					}
					System.out.println("Cena posle: " + price1);
					price2 = Integer.parseInt(price1);// Parse String to Int
					// Price prepare for DB

					// Areas prepare for INT
					String areas1 = StringUtils.substringBetween(areas, "", "m2");
					areas1 = areas1.replace(" ", "");
					areas1 = areas1.replace(".", "");
					areasInt = Integer.parseInt(areas1);
					// Areas prepare for INT

					// Oglas nema cenu po metru tj rentira se
					if (price_per_m.equals("") || price_per_m == null) {

						if (price2 <= 6000) { // Za rentirane stanove koji imaju pravu cenu za rentu
							// areasInt - povrsina kao broj
							// price2 - cena kao broj
							// nameOfAd - naslov
							// urlToTheAd - url do oglasa
							// ad_pubished - datum oglasa
							System.out.println("Cena: " + price2);
							type_of_ad = "rent";
							int price_per_mInt = 0;

						} else {

							// cena po metru za stanove koji su na prodaju i nemaju price_per_m

							// areasInt - povrsina kao broj //price2 - cena kao broj //nameOfAd - naslov
							// urlToTheAd - url do oglasa //ad_pubished - datum oglasa
							type_of_ad = "sell";
							int price_per_mInt = price2 / areasInt;
						}
					} else { // Price per meter square format for DB
						String price_per_meter = price_per_m.replace("€/m2", "");
						price_per_meter = price_per_meter.replace("(", "");
						price_per_meter = price_per_meter.replace(")", "");
						price_per_meter = price_per_meter.replace(" €", "");
						price_per_meter = price_per_meter.replace(".", "");

						price_per_mFloat = Float.parseFloat(price_per_meter);

						// Za oglase gde nije data cena po kvadratu, a data je povrsina i ukupna cena
						if (price_per_mFloat == 0 && price2 != 0 && areasInt != 0) {
							price_per_mFloat = price2 / areasInt;
						}
						type_of_ad = "sell";

					}
				}
				System.out.println("Tip oglasa: " + type_of_ad);
				// Download image koje postoje
				if (image1 != null || image2 != null) {
					image11 = ImageDownloader.saveImage(image1, "image_1_" + 1);
				}
				System.out.println("Url: " + urlToTheAd);
				//System.out.println("Grad: " + cite);
				if (image2 != null) {
					image22 = ImageDownloader.saveImage(image2, "image_2_" + 2);
				}

				// UNOS U BAZU POCETAK!!!************************//
				float num_of_roomsFloat;

				String num_of_rooms1 = null;
				String fullAddress = state + " " + cite + " " + street;
				if (num_of_rooms.contains(">")) {
					num_of_rooms1 = num_of_rooms.replace(">", "");
				}
				if (num_of_rooms == null || num_of_rooms.equals("")) {
					num_of_rooms1 = "0";
				} else {
					if(num_of_rooms.equalsIgnoreCase("Hotelu")) {
						num_of_rooms1 = "-2";
					}
					if (num_of_rooms.equalsIgnoreCase("Garsonjera")) {
						num_of_rooms1 = "1";
					} else {
						if (num_of_rooms.equalsIgnoreCase("Privatnom smeštaju")) {
							num_of_rooms1 = "-1";
						}
					}

				}
				if (checkIsNumber(num_of_rooms)) {
					num_of_rooms1 = num_of_rooms;
				}
				System.out.println("Broj soba: " + num_of_rooms1);
				num_of_roomsFloat = Float.parseFloat(num_of_rooms1);

				SimpleDateFormat sdf1 = new SimpleDateFormat("dd.mm.YYYY");
				java.util.Date date = sdf1.parse(ad_published);
				sdf1.applyPattern("YYYY-mm-dd");
				java.sql.Date date1 = new java.sql.Date(date.getTime());
/*
				AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(nameOfAd, urlToTheAd, price2, areasInt, date1,
						titleOfDetailPage, description, address, fullAddress, floor, num_of_roomsFloat, cite, state,
						street, price_per_mFloat, image11, image22, type_of_ad);
*/
				//ls.add(adv);
				// insert.saveRecord(adv);

				// ************************************************//
				// Oglasi koji su cisti i spremni za unos
				// ************************************************//
				if (!nameOfAd.equals("") && !urlToTheAd.equals("") && !price.equals("") && !areas.equals("")
						&& !ad_published.equals("") && !titleOfDetailPage.equals("") && !description.equals("")
						&& !address.equals("") && !floor.equals("") && !num_of_rooms.equals("") && !cite.equals("")
						&& !state.equals("") && !street.equals("") && !price_per_m.equals("")) {

					br2++;

					// byte[] image11 = ImageDownloader.saveImage(image1, "image_1_" + 1);
					// byte[] image22 = ImageDownloader.saveImage(image1, "image_2_" + 2);

					// float num_of_roomsFloat;
					// String fullAddress = state + " " + cite + " " + street;
					// if(num_of_rooms.equalsIgnoreCase("Garsonjera")) {
					// num_of_roomsFloat = 1;
					// }else {
					// String num_of_rooms1 = num_of_rooms.replace(">", "");
					// num_of_roomsFloat = Float.parseFloat(num_of_rooms1);
					// }

					/*
					 * String startDate = "01-02-2013"; SimpleDateFormat sdf1 = new
					 * SimpleDateFormat("dd.mm.YYYY"); java.util.Date date =
					 * sdf1.parse(ad_published); sdf1.applyPattern("YYYY-mm-dd"); java.sql.Date
					 * date1 = new java.sql.Date(date.getTime());
					 */

//					AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(nameOfAd, urlToTheAd, price2, areasInt,
//							date1, titleOfDetailPage, description, address, fullAddress, floor, num_of_roomsFloat, cite,
//							state, street, price_per_mFloat, image11, image22, type_of_ad);
//
//					ls.add(adv);
//					insert.saveRecord(adv);
					// System.out.println(adv);
				}

			}

			// System.out.println("*********************************************************************");
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("Greska: ");
			e.printStackTrace();
		}

		System.out.println("Istih: " + br1);
		System.out.println("Razlicitih: " + br2);
		//// System.out.println("Do we have the same URL for ad? 0 no >0 yes : " +
		// validation.checkUniqueAd(list)); // System.out.println(br);
		// System.out.println("Number of ad: " + imgCounter);
		return true;
	}

	private static boolean checkIsNumber(String address) {
		try {
			float broj = Float.parseFloat(address);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
