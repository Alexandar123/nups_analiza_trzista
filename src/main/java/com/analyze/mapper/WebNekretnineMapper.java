package com.analyze.mapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analyze.WebPageScreenShotTaker;
import com.analyze.database.InsertRecordInDatabaseWithJdbcTemplate;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.AdvertiseManagerRepo;
import com.analyze.validation.ValidationImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

@Service
public class WebNekretnineMapper {
	
	private static AdvertiseManagerRepo advertiseManagerRepo;

	@Autowired
	public WebNekretnineMapper(AdvertiseManagerRepo advertiseManagerRepo) {
		super();
		this.advertiseManagerRepo = advertiseManagerRepo;
	}

	private static String type_of_ad = "sell";
	private static String FULL_PATH;

	public static void main(String[] args) {
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\webnekretnine\\webnekretnine2";
		String EXTENSION = ".json";

		Instant start = Instant.now();

		System.out.println("***********************");
		FULL_PATH = BASE + EXTENSION;
		System.out.println(FULL_PATH);
		try {
			readJson(FULL_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }

		Instant end = Instant.now();
		System.out.println("Vreme : " + Duration.between(start, end));
		System.out.println("Success");

	}

	private static boolean readJson(String FULL_PATH2) throws ParseException {
		JsonNode rootNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<AdvertiseWebNekretnine> ls = new ArrayList<AdvertiseWebNekretnine>();
		InsertRecordInDatabaseWithJdbcTemplate insert = new InsertRecordInDatabaseWithJdbcTemplate();
		int br = 0, areasInt = 0, br1 = 0;
		Long price2 = 0L;
		byte[] image11 = null, image22 = null, screenshot;

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
				String titleOfDetailPage = rootNode.path("address").get(br).path("name").asText();//
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

				if(Strings.isNullOrEmpty(ad_published)) {
					ad_published = "01.02.2020. 12:59";
				}
				// Grad koji nema adresu a ima grad je validan!
				// Oglas koji nema grad ni adresu proveriti da li u name naslovu ima tih
				// podataka
				/*
				 * if (address.equals("") || address.contains(" m2") || address == null) {
				 * String grad = StringUtils.substringBefore(nameOfAd, " - "); grad =
				 * grad.replace("-", "");
				 * 
				 * if (grad.contains("BEOGRAD DEDINJE") || grad.contains("Beograd Dedinje")) {
				 * grad = StringUtils.substringBefore(nameOfAd, " "); } cite = grad; address =
				 * nameOfAd;// Adresa za oglase kojima su podaci pomesani address =
				 * StringUtils.substringAfter(address, " - "); }
				 */

				// Priprema grada
				if (cite.contains(" m2")) {
					String grad = StringUtils.substringBefore(nameOfAd, " - ");
					cite = grad;
					// System.out.println("Grad: " + cite);

				}
				if (cite.equalsIgnoreCase("Opština Novi Beograd") || cite.equalsIgnoreCase("Beograd Grad")
						|| cite.equalsIgnoreCase("Opština Zemun")) {
					cite = "Beograd";
				}

				// Priprema addresse
				float price_per_mFloat = 0;

				// Price prepare for DB
				if (price == null || price.equals("")) {
					price = "0";
				}
				if (price.equalsIgnoreCase("dogovor")) {
					price = "-3";
				}
				String price1 = price.replace(" €", "");// Replaced €
				price1 = price1.replace(".", "");

				price2 = Long.parseLong(price1);// Parse String to Int
				// Price prepare for DB

				// Areas prepare for INT
				String areas1 = StringUtils.substringBetween(areas, "", " m2");
				areas1 = areas1.replace(".", "");
				areasInt = Integer.parseInt(areas1);
				// Areas prepare for INT

				// Oglas nema cenu po metru tj rentira se
				int price_per_mInt = 0;
				if (Strings.isNullOrEmpty(price_per_m)) {

					if (price2 <= 6000) { // Za rentirane stanove koji imaju pravu cenu za rentu
						System.out.println("Cena: " + price2);
						type_of_ad = "rent";
					} else {
						// cena po metru za stanove koji su na prodaju i nemaju price_per_m
						type_of_ad = "sell";
						price_per_mInt = (int) (price2 / areasInt);
					}
				} else { // Price per meter square format for DB
					String price_per_meter = price_per_m.replace("€/m2", "");
					price_per_meter = price_per_meter.replace("(", "");
					price_per_meter = price_per_meter.replace(")", "");
					price_per_meter = price_per_meter.replace(" €", "");
					price_per_meter = price_per_meter.replace(".", "");

					price_per_mFloat = Float.parseFloat(price_per_meter);
					System.out.println("FLoat cena m2: " + price_per_mFloat);
					// Za oglase gde nije data cena po kvadratu, a data je povrsina i ukupna cena
					if (price_per_mFloat == 0 && price2 != 0 && areasInt != 0) {
						price_per_mFloat = price2 / areasInt;
					}
					type_of_ad = "sell";

				}

				// Download image koje postoje
				if (!Strings.isNullOrEmpty(image1)) {
					image11 = ImageDownloader.saveImage(image1, "image_1_" + 1);
				}
				System.out.println("Url: " + urlToTheAd);
				// System.out.println("Grad: " + cite);
				if (!Strings.isNullOrEmpty(image2)) {
					image22 = ImageDownloader.saveImage(image2, "image_2_" + 2);
				}

				screenshot = WebPageScreenShotTaker.screenShot(urlToTheAd);

				// UNOS U BAZU POCETAK!!!************************//
				float num_of_roomsFloat;

				String num_of_rooms1 = null;
				String fullAddress = state + " " + cite + " " + street + " " + address;

				if (num_of_rooms.contains(">")) {
					num_of_rooms1 = num_of_rooms.replace(">", "");
				}
				if (num_of_rooms == null || num_of_rooms.equals("")) {
					num_of_rooms1 = "0";
				} else {
					if (num_of_rooms.equalsIgnoreCase("Hotelu")) {
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
				if (num_of_rooms1.isEmpty()) {
					num_of_rooms1 = "0";
				}
				if (num_of_rooms1.contains(">")) {
					num_of_rooms1 = num_of_rooms.replace(">", "");
				}
				if (num_of_rooms1.equalsIgnoreCase("Garsonjera")) {
					num_of_rooms1 = "1";
				}
				num_of_roomsFloat = Float.parseFloat(num_of_rooms1);

				if (ad_published.equals("") || ad_published.equals(null)) {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY");
					LocalDateTime now = LocalDateTime.now();
					ad_published = dtf.format(now).toString();
				}
				String ad = StringUtils.substring(ad_published, 0, 10);
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd.mm.YYYY");
				java.util.Date date = sdf1.parse(ad);
				sdf1.applyPattern("YYYY-mm-dd");
				java.sql.Date date1 = new java.sql.Date(date.getTime());
				System.out.println(date1);
				String type_of_property = "missing";
				if (description.contains("Lokal") || description.contains("lokal")) {
					type_of_property = "lokal";
				} else if (description.contains("kuca") || description.contains("kuce") || description.contains("Kuca")
						|| description.contains("kuća") || description.contains("Kuća")) {
					type_of_property = "house";
				} else if (description.contains("stan") || description.contains("Stan")
						|| description.contains("apartman") || description.contains("Apartman")) {
					type_of_property = "apartment";
				} else if (description.contains("Poslovni prostor") || description.contains("poslovni prostor")) {
					type_of_property = "business place";
				}else if (description.contains("zemljiste") || description.contains("Zemljiste")
						|| description.contains("Zemljište") || description.contains("zemljište")
						|| description.contains("parcela") || description.contains("imanje")) {
					type_of_property = "land ";
				}

				int building_year = 0;

				
				  AdvertiseWebNekretnine advertise = new AdvertiseWebNekretnine(nameOfAd, urlToTheAd,
				  price2, areasInt, date1, titleOfDetailPage, description, address,
				  fullAddress, floor, num_of_roomsFloat, cite, state, street, price_per_mFloat,
				  image11, image22, type_of_ad, type_of_property, building_year,screenshot);
				 

				 ls.add(advertise);
				 
				 InsertRecordInDatabaseWithJdbcTemplate.saveRecord(advertise);

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
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
