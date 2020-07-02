package com.analyze.mapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

@Service
public class NekretnineMapper {
	private static AdvertiseManagerRepo advertiseManagerRepo;

	@Autowired
	public NekretnineMapper(AdvertiseManagerRepo advertiseManagerRepo) {
		this.advertiseManagerRepo = advertiseManagerRepo;
	}

	// ID AD POSLEDNJI DODAT ZA 4zida 87184
	// ID AD POSLEDNJI DODAT ZA 4zida 93071
	// POSLEDNJI ID IZ BAZE 96504
	public static void main(String[] args) {
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\Nekretnine\\nekretnine2.json";

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

	public static void readJson(String FULL_PATH) throws ParseException {
		JsonNode rootNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<AdvertiseWebNekretnine> ls = new ArrayList<AdvertiseWebNekretnine>();
		InsertRecordInDatabaseWithJdbcTemplate insert = new InsertRecordInDatabaseWithJdbcTemplate();

		String floorInt = "-1", type_of_property = null, fullAddress = null, state = "SRBIJA",
				num_of_roomsString = null, street = null, type_of_ad = null;
		int areasInt = 0, numOfRoom = -1, building_year;
		Long priceInt;
		Date date1 = null;
		int br = 0;
		float price_per_m = 0.0f;
		byte[] image11 = null, image22 = null, screenshot = null;

		try (FileReader reader = new FileReader(FULL_PATH)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray lang = (JSONArray) jsonObject.get("images");

			System.out.println("Broj key-value pair: " + lang.size());

			rootNode = objectMapper.readTree(new File(FULL_PATH));

			br = 0;
			int br1 = 0;
			int br2 = 0;
			while (rootNode.elements().hasNext() && br < lang.size()) {

				String name = rootNode.path("images").get(br).path("name").asText();// ok
				String url = rootNode.path("images").get(br).path("url").asText();// ok
				String price = rootNode.path("images").get(br).path("price").asText();// ok
				String areas = rootNode.path("images").get(br).path("areas").asText();// ok

				String title = rootNode.path("images").get(br).path("name").asText();
				
				String price_per_m_string = rootNode.path("images").get(br).path("price_per_m").asText();

				// ok
				String address = rootNode.path("images").get(br).path("address").asText();
				// ok
				String[] tmpCity = address.split(",");
				String city = null;
				if (tmpCity.length > 2) {
					city = StringUtils.substringBetween(address, ",");
				} else {
					city = StringUtils.substringBefore(address, ",");
				}
				// ok
				street = StringUtils.substringBefore(address, ",");
				// ok
				state = StringUtils.substringAfterLast(address, ",");
				// ok
				fullAddress = address;
				// ok

				// ok
				String floorJson = rootNode.path("images").get(br).path("floor").asText();
				floorInt = isNumeric(floorJson) ? floorJson : "-1";
				// ok
				
				// ok
				String ad_publishedTmp = rootNode.path("images").get(br).path("ad_published_type").asText();
				String ad_published = StringUtils.substringBefore(ad_publishedTmp, " |");
				// ok

				String description = rootNode.path("images").get(br).path("description").asText();
				String image = rootNode.path("images").get(br).path("image").asText();// ok

				building_year = 0;
				
				String type_of_propertyTmp = StringUtils.substringAfterLast(ad_publishedTmp, " | ");
				
				if(type_of_propertyTmp.contains("Trosoban")) {
					numOfRoom = 3;
				}else if(type_of_propertyTmp.contains("Jednosoban") || type_of_propertyTmp.contains("Gars")) {
					numOfRoom = 1;
				}else if(type_of_propertyTmp.contains("Četvorosoban")) {
					numOfRoom = 4;
				}else if(type_of_propertyTmp.contains("Dvosoban")) {
					numOfRoom = 2;
				}else if(type_of_propertyTmp.contains("Petosoban")) {
					numOfRoom = 5;
				}else{
					numOfRoom = -1;
				}
				
				if(type_of_propertyTmp.contains("stan") || type_of_propertyTmp.contains("Garsonjera")) {
					type_of_property = "apartment";
				}else{
					type_of_property = StringUtils.substringAfter(type_of_propertyTmp, " ");
				}

				// ok areas
				if (!Strings.isNullOrEmpty(areas)) {
					String ar = areas.replace(" m2", "");
					if (ar.contains(".")) {
						double d = Double.parseDouble(ar);
						areasInt = (int) d;
					} else {
						areasInt = Integer.parseInt(ar);
					}
				} else {
					areasInt = 1;
				}
				// ok areas

				// OK price
				if (!Strings.isNullOrEmpty(price)) {
					String pr = price.replace(" EUR", "");
					String tmp = pr.replace(" ", "");
					tmp = tmp.replace(".", "");
					priceInt = Long.parseLong(tmp);
				} else {
					priceInt = 0L;
				}
				// OK price

				// OK price_per_m
				if (!Strings.isNullOrEmpty(price_per_m_string) && !price_per_m_string.equals("Uknjiženo")) {
					String tmp1 = price_per_m_string.replace(" €/m²", "");
					String tmp = tmp1.replace(",", ".");
					String tmp2 = tmp.replace(" ", "");
					price_per_m = Float.parseFloat(tmp2);
				} else {
					price_per_m = priceInt / areasInt;
				}
				// OK price_per_m
				
				// Ok type_of_ad
				if (ad_publishedTmp.contains("Prodajem") || ad_publishedTmp.contains("prodajem")
						|| ad_publishedTmp.contains("prodajem") || ad_publishedTmp.contains("prodaju")
						|| ad_publishedTmp.contains("Prodajem") || ad_publishedTmp.contains("Prodaja")) {
					type_of_ad = "sell";
				} else {
					type_of_ad = "rent";
				}
				// Ok type_of_ad

				// ok image screenshot
				if (!Strings.isNullOrEmpty(image)) {
					 image11 = ImageDownloader.saveImage(image, "image_1_" + 1);
				}

				 screenshot = WebPageScreenShotTaker.screenShot(url);
				// ok image screenshot

				// Ok ad_published
				br++;
				if (!Strings.isNullOrEmpty(ad_published) && Character.isDigit(ad_published.charAt(0)) == true
						&& ad_published.length() > 4) {
					String dateSub = StringUtils.substring(ad_published, 0, 10);
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.YYYY");
					java.util.Date date = sdf1.parse(dateSub);
					sdf1.applyPattern("YYYY-mm-dd");
					date1 = new java.sql.Date(date.getTime());
				} else {
					long millis = System.currentTimeMillis();
					date1 = new java.sql.Date(millis);
				}
				// Ok ad_published
				
				 AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(name, url, priceInt,
				 areasInt, date1, title, description, address, fullAddress, floorInt, numOfRoom, city.toLowerCase(),
				 state.toUpperCase(), street, price_per_m, image11, image22, type_of_ad, type_of_property, building_year, screenshot);
				 //System.out.println(adv);

				 ls.add(adv);
				 //advertiseManagerRepo.save(adv);
				 InsertRecordInDatabaseWithJdbcTemplate.saveRecord(adv);

			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("Greska: ");
			e.printStackTrace();
		}

	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
