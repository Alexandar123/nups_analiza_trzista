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
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.analyze.WebPageScreenShotTaker;
import com.analyze.controllers.AddScreenShotToAdController;
import com.analyze.database.InsertRecordInDatabaseWithJdbcTemplate;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.AdvertiseManagerRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RealiticaMapper {
	

	public static void main(String[] args) {
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\realitica\\realitica_rent1.json";
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

	public static void readJson(String FULL_PATH) throws ParseException {
		JsonNode rootNode = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<AdvertiseWebNekretnine> ls = new ArrayList<AdvertiseWebNekretnine>();
		InsertRecordInDatabaseWithJdbcTemplate insert = new InsertRecordInDatabaseWithJdbcTemplate();

		String  floorInt = "-1", type_of_property = null, title = null, address = null, city = null, fullAddress = null,
				state = null, floor = null, num_of_roomsString = null, street = null, type_of_ad=null;
		int areasInt = 0, numOfRoom = -1, building_year;
		Long priceInt;
		Date date1 = null;
		int br = 0;
		float price_per_m = 0.0f;
		byte[] image11 = null, image22=null, screenshot=null;

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

				String price = rootNode.path("images").get(br).path("price").asText();//
				String name = rootNode.path("images").get(br).path("name").asText();//
				String url = rootNode.path("images").get(br).path("url_url").asText();
				String type = rootNode.path("images").get(br).path("type").asText();
				String areas = rootNode.path("images").get(br).path("areas").asText();
				String ad_published = rootNode.path("images").get(br).path("last_changes").asText();
				
				String description = rootNode.path("images").get(br).path("details").asText();
				//String description = descriptionOriginal;

				String image1 = rootNode.path("images").get(br).path("image1").asText();
				String image2 = rootNode.path("images").get(br).path("image2").asText();// OK String

				String buildingS = StringUtils.substringBetween(description, "Godina Gradnje: ", "\n");
				String buldin = buildingS == null ? "-1" : buildingS;
				building_year = Integer.parseInt(buldin);
				state="SRBIJA";
				
				if(type.contains("Prodajem") || type.contains("prodajem") || name.contains("prodajem") || name.contains("Prodajem") ) {
					type_of_ad = "sell";
				}else {
					type_of_ad = "rent";
				}
				
				if (description != null) {

					if(type.contains("Prodajem Stan-Apartman") || type.contains("Stan") || type.contains("Apartman") 
							|| type.contains("Apartment") || type.contains("garsonjera") || type.contains("Garsonjera")) {
						type_of_property = "apartment";
					}else if(type.contains("Prodajem Zemljište") || type.contains("Prodajem Građevinsko Zemljište") ||
							type.contains("Prodajem Gradjevinsko Zemljište") || type.contains("prodajem plac") || type.contains("zemljiste") || type.contains("Zemljiste") || type.contains("Zemljište") || type.contains("zemljište")){
						type_of_property = "land";
					}else if(type.contains("Prodajem Kuću") || type.contains("Kucu") || type.contains("Kuca") || type.contains("Kuću")
							|| type.contains("Kuća") || type.contains("kucu") || type.contains("kuću") || type.contains("Kuće")) {
						type_of_property = "house";
					}else if(type.contains("Prodajem Poslovni Prostor") || type.contains("Hotel")|| type.contains("poslovni")
							|| type.contains("Poslovni Prostor")) {
						type_of_property = "business place";
					}
				
					
					if (price.equals("") || price == null)
						price = "-1";
					price = price.replace(".", "");
					price = price.replace("€", "");
					priceInt = Long.parseLong(price);

					if (image1 != null || image2 != null) {
						 image11 = ImageDownloader.saveImage(image1, "image_1_" + 1);
					}
					if (image2 != null) {
						 image22 = ImageDownloader.saveImage(image2, "image_2_" + 2);
					}
					 screenshot = WebPageScreenShotTaker.screenShot(url);

					// Varijable koje nemaju ali odgovoaraju postojecim
					String ttitle = StringUtils.substringBetween(description, "Opis:", "\n");
					System.out.println(url);
					if(ttitle != null) {
					if(ttitle.length() > 254) {
						title = StringUtils.substring(ttitle, 0,254);
					}else {
						title = ttitle;
					}
				}else {
						title = "Undefind";
					}
					city = StringUtils.substringBetween(description, "Područje: ", "\n");
					String aaddress = StringUtils.substringBetween(description, "Lokacija: ", "\n");
					address = (aaddress == null) ? city : aaddress;
					fullAddress = city + " " + address;
					street = address;

					String num = StringUtils.substringBetween(description, "Spavaćih Soba: ", "\n");
					
					
					if (num == null)
						num = "-1";
					
					if(num.equals("0,5")) {
						num = "1";
					}
					numOfRoom = Integer.parseInt(num.equals("") ? "-1" : num);
					
					// Exit from loop
					br++;
					if(areas.equals(null) || !areas.equals("")) {
						String ar = areas.replace(" m2", "");
						ar = ar.replace(",", "");
						ar = ar.replace(".", "");
						System.out.println("Areas: " + areas);
						areasInt = Integer.parseInt(ar);
						
					}else {
					if (description.contains("Stambena Površina:")) {
						int i = description.indexOf("Stambena Površina:") + 1;
						String result = StringUtils.substringBetween(description, "Stambena Površina:", "\n");
						String aresRemoveSpace = result.replace(" ", "");
						aresRemoveSpace = aresRemoveSpace.replace("m2", "");
						aresRemoveSpace = aresRemoveSpace.replace(".", "");
						areasInt = Integer.parseInt(aresRemoveSpace);
					} else if (description.contains("m2")) {
						// Dvosoban stan 61.60m2 uzmi poziciju od m2 -6 i filtriraj samo brojeve
						String areasToPrepare = description.substring(description.indexOf("m2") - 6,
								description.indexOf("m2"));

						// areasToPrepare = areasToPrepare.replace(",", ".");
						if (areasToPrepare.contains(",")) {
							int ch = areasToPrepare.indexOf(",");
							if (!Character.isDigit(areasToPrepare.charAt(ch + 1))) {
								areasToPrepare = areasToPrepare.replace(",", "");
							}
						}
						areasToPrepare = areasToPrepare.replace(",", ".");
						areasToPrepare = areasToPrepare.replaceAll("[^0-9.]", "");
						if(areasToPrepare.equals(""))
							areasToPrepare = "0";

						float f = Float.parseFloat(areasToPrepare);
						areasInt = (int) f;
					}
					}
					if(areasInt == 0)
						price_per_m=-1;
					else
					price_per_m = priceInt / areasInt;

					if(!ad_published.equals(null) || !ad_published.equals("")) {
						SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM,YYYY");
						java.util.Date date = sdf1.parse(ad_published);
						sdf1.applyPattern("YYYY-mm-dd");
						date1 = new java.sql.Date(date.getTime());
					}else {
					if (description.contains("Zadnja Promjena: ")) {
						String result = StringUtils.substringBetween(description, "Zadnja Promjena: ", "\n");

						SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM,YYYY");
						java.util.Date date = sdf1.parse(result);
						sdf1.applyPattern("YYYY-mm-dd");
						date1 = new java.sql.Date(date.getTime());
					}
					}

					if (description.contains("sprat")) {
						String floorToPreparee = description.substring(description.indexOf("sprat") - 4,
								description.indexOf("sprat"));
						
						String floorToPrepare = (floorToPreparee == null) ? "-1" : floorToPreparee;
						floorToPrepare = floorToPrepare.replaceAll("[^0-9]", "");

						floorInt = floorToPrepare;
					}

					if (description.contains("Drugi sprat")) {
						floorInt = "2";
					} else if (description.contains("Prvi sprat")) {
						floorInt = "1";
					} else if (description.contains("Treci sprat") || description.contains("Treći sprat")) {
						floorInt = "3";
					} else if (description.contains("Cetvrti sprat") || description.contains("Četvrti sprat")) {
						floorInt = "4";
					} else if (description.contains("Peti sprat")) {
						floorInt = "5";
					}
					
					AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(name, url, priceInt, areasInt, date1,
							title, description, address, fullAddress, floorInt, numOfRoom, city, state,
							street, price_per_m, image11, image22, type_of_ad, type_of_property, building_year,screenshot);

					ls.add(adv);
					//advertiseManagerRepo.save(adv);
					 InsertRecordInDatabaseWithJdbcTemplate.saveRecord(adv);
					
					// System.out.println("Broj: " + br);
					//System.out.println("Broj koji nemaju datum: " + br1);
					//System.out.println("Broj koji nemaju areas: " + br2);
				}
			}
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("Greska: ");
			e.printStackTrace();
		}

	}
}
