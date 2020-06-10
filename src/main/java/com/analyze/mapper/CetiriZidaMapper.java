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
import java.util.Calendar;

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
public class CetiriZidaMapper {
	private static AdvertiseManagerRepo advertiseManagerRepo;

	@Autowired
	public CetiriZidaMapper(AdvertiseManagerRepo advertiseManagerRepo) {
		this.advertiseManagerRepo = advertiseManagerRepo;
	}
//ID AD POSLEDNJI DODAT ZA 4zida 87184
	public static void main(String[] args) {
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\4zida\\4zida2.json";

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

		String floorInt = "-1", type_of_property = null, address = null, fullAddress = null,
				state = "SRBIJA", num_of_roomsString = null, street = null, type_of_ad = null;
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

				String name = rootNode.path("images").get(br).path("title").asText();//
				String url = rootNode.path("images").get(br).path("url").asText();
				String price = rootNode.path("images").get(br).path("price").asText();
				String title = rootNode.path("images").get(br).path("title").asText();
				String type = rootNode.path("images").get(br).path("type_of_property").asText();
				type_of_property="apartment";
				String areas = rootNode.path("images").get(br).path("areas").asText();
				String price_per_m_string  = rootNode.path("images").get(br).path("price_per_m").asText();
				
				String tmpAddress = rootNode.path("images").get(br).path("address").asText();
				String addressString = tmpAddress.replace("\n", " ");
				address = addressString;
				fullAddress = address;
				
				String city = StringUtils.substringAfterLast(addressString, ",");
				street = StringUtils.substringBeforeLast(tmpAddress, ",");
				
				String floorJson = rootNode.path("images").get(br).path("floor").asText();
				String floor = (floorJson).contains(".") ? StringUtils.substringBefore(floorJson, ".")
						: StringUtils.substringBefore(floorJson, "/");
				floorInt = floor;
				//floorInt = (floor.equalsIgnoreCase("visoko prizemlje")) ? "0" : floor;
				
				String num_of_rooms = rootNode.path("images").get(br).path("num_of_rooms").asText();
				System.out.println(url);
				if(num_of_rooms.equalsIgnoreCase("Centralno")){
					numOfRoom = 0;
				}else {
					numOfRoom =(Strings.isNullOrEmpty(num_of_rooms)) ? 0 : Integer.parseInt(num_of_rooms.substring(0, 1));

				}

				
				String ad_published = rootNode.path("images").get(br).path("ad_published").asText();

				String description = rootNode.path("images").get(br).path("description").asText();
				String image = rootNode.path("images").get(br).path("image").asText();

				building_year = 0;
				
				if (!Strings.isNullOrEmpty(areas)) {
					String ar = areas.replace("m2", "");
					areasInt = Integer.parseInt(ar);
				}else {
					areasInt = 1;
				}
				
				if (!Strings.isNullOrEmpty(price)) {
					String pr = price.replace(" €", "");
					String tmp = pr.replace(".", "");
					priceInt = Long.parseLong(tmp);
				}else {
					priceInt = 0L;
				}
				if( price_per_m_string != null || Character.isDigit(price_per_m_string.charAt(0)) == false || price_per_m_string.equalsIgnoreCase("centralno") || price_per_m_string.equalsIgnoreCase("struja") || price_per_m_string.equalsIgnoreCase("Etažno")){
					price_per_m = priceInt / areasInt;
				}else if (!Strings.isNullOrEmpty(price_per_m_string) && !price_per_m_string.equals("Uknjiženo")) {
					String tmp1 = price_per_m_string.replace(" €/m2", "");
					String tmp = tmp1.replace(".", "");
					price_per_m = Float.parseFloat(tmp);
				}else {
					price_per_m = priceInt / areasInt;
				}

				if (title.contains("Prodajem") || title.contains("prodajem") || title.contains("prodajem") || title.contains("prodaju")
						|| name.contains("Prodajem")) {
					type_of_ad = "sell";
				} else {
					type_of_ad = "rent";
				}

				if (description != null) {
					if (!Strings.isNullOrEmpty(image)) {
						 image11 = ImageDownloader.saveImage(image, "image_1_" + 1);
					}

					 screenshot = WebPageScreenShotTaker.screenShot(url);
					// Exit from loop
					br++;
					if (!Strings.isNullOrEmpty(ad_published) && Character.isDigit(ad_published.charAt(0)) == true) {
						System.out.println("Datum: " + Character.isDigit(ad_published.charAt(0)) );
						String dateSub = StringUtils.substring(ad_published, 0, 10);
						SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.YYYY");
						java.util.Date date = sdf1.parse(dateSub);
						sdf1.applyPattern("YYYY-mm-dd");
						date1 = new java.sql.Date(date.getTime());
					} else {
						long millis = System.currentTimeMillis();
						date1 = new java.sql.Date(millis);
					}
					
					AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(name, url, priceInt, areasInt, date1, title,
							description, address, fullAddress, floorInt, numOfRoom, city, state, street, price_per_m,
							image11, image22, type_of_ad, type_of_property, building_year, screenshot);
					//System.out.println(adv);

					ls.add(adv);
					 //advertiseManagerRepo.save(adv);
					 InsertRecordInDatabaseWithJdbcTemplate.saveRecord(adv);

				}
			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("Greska: ");
			e.printStackTrace();
		}

	}
}
