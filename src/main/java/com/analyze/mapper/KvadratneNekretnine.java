package com.analyze.mapper;

import java.io.File;
import java.io.FileNotFoundException;
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

@Service
public class KvadratneNekretnine {
	
	private static AdvertiseManagerRepo advertiseManagerRepo;
	
	@Autowired
	public KvadratneNekretnine(AdvertiseManagerRepo advertiseManagerRepo) {
		super();
		this.advertiseManagerRepo = advertiseManagerRepo;
	}

	public static void main(String[] args) {
		int br = 0;
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\kvadratneNekretnine\\kvadratneNekretnine6.json";
		String EXTENSION = ".json";

		Instant start = Instant.now();

		System.out.println("***********************");
		System.out.println(BASE);
		try {
			 readJson(BASE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Instant end = Instant.now();
		System.out.println("Vreme : " + Duration.between(start, end));
		System.out.println("Success");

	}

	private static boolean readJson(String FULL_PATH2) throws ParseException {
		JsonNode rootNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<AdvertiseWebNekretnine> ls = new ArrayList<AdvertiseWebNekretnine>();
		int br = 0, areasInt = 0, br1 = 0;
		Long priceLong = 0L;
		byte[] image11 = null, image22 = null, screenshot;
		String titleOfDetailPage = null;
		
		/// POTREBNO URADITI DA IZ SVAKOG FAJLA CITA PODATKE!!!!!!
		int br2 = 0;

		try (FileReader reader = new FileReader(FULL_PATH2)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray lang = (JSONArray) jsonObject.get("images");

			System.out.println("Broj key-value pair: " + lang.size());

			rootNode = objectMapper.readTree(new File(FULL_PATH2));

			br = 0;
			while (rootNode.elements().hasNext() && br < lang.size()) {

				String nameOfAd = rootNode.path("images").get(br).path("name").asText();// OK
				String urlToTheAd = rootNode.path("images").get(br).path("url").asText();//
				String price = rootNode.path("images").get(br).path("price").asText();//
				String areas = rootNode.path("images").get(br).path("areas").asText();//
				String ad_published = rootNode.path("images").get(br).path("ad_pubished").asText();// OK String
				String description = rootNode.path("images").get(br).path("description").asText();// OK String
				String addressS = rootNode.path("images").get(br).path("address").asText();
				String num_of_rooms = rootNode.path("images").get(br).path("num_of_rooms").asText();
				String type_of_adS = rootNode.path("images").get(br).path("type_of_ad").asText();
				String state = "SRBIJA";
				String floor = rootNode.path("images").get(br).path("floor").asText();
				String city = rootNode.path("images").get(br).path("location").asText();// OK
				String buildingYearS = rootNode.path("images").get(br).path("building_year").asText();

				// String street = rootNode.path("address").get(br).path("street").asText();
				// String price_per_m =
				// rootNode.path("address").get(br).path("price_per_m").asText();

				// String image1 = rootNode.path("address").get(br).path("image1_url").asText();
				// String image2 = rootNode.path("address").get(br).path("image2_url").asText();

				br++;
				
				String type_of_ad = null;
				if(type_of_adS.equalsIgnoreCase("Izdavanje")) {
					type_of_ad = "rent";
				}else if(type_of_adS.equalsIgnoreCase("prodaja")) {
					type_of_ad = "sell";
				}
				
				titleOfDetailPage = nameOfAd;

				// Priprema grada
				if (city.contains(" m2")) {
					String grad = StringUtils.substringBefore(nameOfAd, " - ");
					city = grad;
					// System.out.println("Grad: " + cite);

				}
				if (city.equalsIgnoreCase("Opština Novi Beograd") || city.equalsIgnoreCase("Beograd Grad")
						|| city.equalsIgnoreCase("Opština Zemun")) {
					city = "Beograd";
				}

				// Priprema addresse
				float price_per_mFloat = 0;
				// Price prepare for DB
				String price1 = price.replace(" €", "");// Replaced €
				price1 = price1.replace(".", "");
				if (price == null || price.equals("")) {
					price1 = "0";
				}
				if (price.equalsIgnoreCase("dogovor")) {
					price1 = "-3";
				}
				System.out.println("Cena posle: " + price1);
				priceLong = Long.parseLong(price1);// Parse String to Int
				// Price prepare for DB

				// Areas prepare for INT
				String areas1 = StringUtils.substringBetween(areas, "", " m2");
				areas1 = StringUtils.substringBetween(areas, "", " a");
				System.out.println(areas1);
				if(areas1.contains(".")) {
					areas1 = areas1.replace(".", "");	
				}
				
				areasInt = Integer.parseInt(areas1);
				// Areas prepare for INT

				// Oglas nema cenu po metru tj rentira se
				// if (price_per_m.equals("") || price_per_m == null) {

				// if (price2 <= 6000) { // Za rentirane stanove koji imaju pravu cenu za rentu
				// System.out.println("Cena: " + priceLong);
				// type_of_ad = "rent";
				int price_per_mInt = 0;

				// } else {

				// cena po metru za stanove koji su na prodaju i nemaju price_per_m
				// type_of_ad = "sell";
				// int price_per_mInt = (int) (price2 / areasInt);
				// Price per meter square format for DB
//						String price_per_meter = price_per_m.replace("€/m2", "");
//						price_per_meter = price_per_meter.replace("(", "");
//						price_per_meter = price_per_meter.replace(")", "");
//						price_per_meter = price_per_meter.replace(" €", "");
//						price_per_meter = price_per_meter.replace(".", "");
//
//						price_per_mFloat = Float.parseFloat(price_per_meter);

				// Za oglase gde nije data cena po kvadratu, a data je povrsina i ukupna cena
				if (priceLong != null && !priceLong.equals("") && areasInt != 0)
					price_per_mFloat = priceLong / areasInt;

				// type_of_ad = "sell";

				// }
				// }
				System.out.println("Tip oglasa: " + urlToTheAd);
				// Download image koje postoje
				// if (image1 != null || image2 != null) {
				// image11 = ImageDownloader.saveImage(image1, "image_1_" + 1);
				// }
				// System.out.println("Url: " + urlToTheAd);
				// System.out.println("Grad: " + cite);
				// if (image2 != null) {
				// image22 = ImageDownloader.saveImage(image2, "image_2_" + 2);
				// }

				screenshot = WebPageScreenShotTaker.screenShot(urlToTheAd);

				// UNOS U BAZU POCETAK!!!************************//
				float num_of_roomsFloat;

				String num_of_rooms1 = null;
				String address = addressS.substring(addressS.indexOf("-")+2);
				String fullAddress = state + " " + address;

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
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
					LocalDateTime now = LocalDateTime.now();
					ad_published = dtf.format(now).toString();
				}

				String ad = StringUtils.substring(ad_published, 0, 10);
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/mm/YYYY");
				java.util.Date date = sdf1.parse(ad);
				sdf1.applyPattern("YYYY-mm-dd");
				java.sql.Date date1 = new java.sql.Date(date.getTime());
				System.out.println(date1);
				String type_of_property = "missing";
				if (addressS.contains("Lokal") || addressS.contains("lokal")) {
					type_of_property = "business place";
				} else if (addressS.contains("kuca") || addressS.contains("kuce") || addressS.contains("Kuca")
						|| addressS.contains("kuća") || addressS.contains("Kuća")) {
					type_of_property = "house";
				} else if (addressS.contains("stan") || addressS.contains("Stan") || addressS.contains("apartman")
						|| addressS.contains("Apartman")) {
					type_of_property = "apartment";
				} else if (addressS.contains("Poslovni prostor") || addressS.contains("poslovni prostor")) {
					type_of_property = "business place";
				} else if (addressS.contains("zemljiste") || addressS.contains("Zemljište")
						|| addressS.contains("Zemljiste")) {
					type_of_property = "Land";
				}

				int building_year = 0;
				if(buildingYearS.contains("pr") || buildingYearS.equals("")) {
					buildingYearS = "0";
				}
				if (!buildingYearS.equals("") || buildingYearS != null) {
					building_year = Integer.parseInt(buildingYearS);
				}

				AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(nameOfAd, urlToTheAd, priceLong, areasInt,
						date1, titleOfDetailPage, description, address, fullAddress, floor, num_of_roomsFloat, city,
						state, city, price_per_mFloat, image11, image22, type_of_ad, type_of_property, building_year,
						screenshot);

				 ls.add(adv);
				 //advertiseManagerRepo.save(adv);
				 InsertRecordInDatabaseWithJdbcTemplate.saveRecord(adv);
			}
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
