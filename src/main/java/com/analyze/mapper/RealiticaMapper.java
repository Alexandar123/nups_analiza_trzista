package com.analyze.mapper;

import java.io.File;
import java.io.FileNotFoundException;
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

import com.analyze.configuration.InsertRecordInDatabaseWithJdbcTemplate;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.validation.ValidationImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RealiticaMapper {

	public static String prefix = "";// PREFIX FOR CITE
	public static String DATE = ""; // DATE OF DOWNLOADING DATA
	public static String NAME_OF_FILE = "bg_realitica_1_10_2020";
	public static String DIRECTORY = "realitica\\";
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

		for (int i = 1; i <= 1; i++) {
			FILE = prefix + NAME_OF_FILE + DATE + "_";
			FILE += i;// Number of documents

			FULL_PATH = BASE + FILE + EXTENSION;
			System.out.println(FULL_PATH);
			try {

				readJson(FULL_PATH);
			} catch (Exception e) {
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
		int br = 0, price2 = 0, areasInt = 0, br1 = 0, br3 = 0, building_yer =0;
		byte[] image11 = null, image22 = null;
		String url, title = null, description1 = null,address = null, podrucje = null,addressa, state, city, street,type_of_property;
		Long priceLong;
		java.sql.Date date1;
		float num_of_rooms = 0, price_per_m=0;

		// JsonArray groupObject = jsonObject.getAsJsonArray("group");
		/// POTREBNO URADITI DA IZ SVAKOG FAJLA CITA PODATKE!!!!!!
		int br2 = 0;

		try (FileReader reader = new FileReader(FULL_PATH2)) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

			JSONArray lang = (JSONArray) jsonObject.get("images");

			System.out.println("Broj key-value pair: " + lang.size());
			String id = (String) jsonObject.get("price");

			rootNode = objectMapper.readTree(new File(FULL_PATH2));

			br = 0;
			while (rootNode.elements().hasNext() && br < lang.size()) {

				String price = rootNode.path("images").get(br).path("price").asText();//
				String name = rootNode.path("images").get(br).path("name").asText();//
				String description = rootNode.path("images").get(br).path("description").asText();// OK String

				String image1 = rootNode.path("images").get(br).path("image1").asText();
				String image2 = rootNode.path("images").get(br).path("image2").asText();

				br++;
				// ************************************************//
				// Oglasi koji su cisti i spremni za unos
				// ************************************************//
				if(price != null && name != null && description != null) {
					//Skip the empty json object(ad) {},{}...
				if (!price.equals("") && !name.equals("") && !description.equals("")) {
					//If price, name and description isn't empty field, than collect data
					
					//*********PRICE spremna za bazu************//
					String pric = price.replace("€", "");
					pric = pric.replace(".", "");
					priceLong = Long.parseLong(pric);
					//*********PRICE spremna za bazu************//
					
					//*********URL spremna za bazu************//
						url = image1;
					//*********URL spremna za bazu************//
					
					//*********type_of_ad spremna za bazu************//
					if(!name.contains("Prodaj") && !name.contains("Prodaja") && !name.contains("Na prodaju")) {
						if(!description.contains("Prodajem") && !description.contains("Prodaja") && !description.contains("Na prodaju")) {
							type_of_ad="rent";
						}else {
							type_of_ad="sell";
						}
					}
					//*********type_of_ad spremna za bazu************//
					
					//*********areas spremna za bazu************//
						if(description.contains("Površ") || description.contains("m2") || description.contains("Povrsi") || description.contains("useljiv") ) {
							String[] s = StringUtils.substringsBetween(description, "Površina:", "m2");
							
							String replace = s[0].replace(" ", "");
							replace = replace.replace(".", "");
							areasInt = Integer.parseInt(replace);
						}
					//*********areas spremna za bazu************//
						
					//*********ad_published spremna za bazu************//
							String[] s = StringUtils.substringsBetween(description, "Zadnja Promena:", "\n");
							
						
							String ad_published = s[0];
							ad_published = ad_published.replace(" ", "");
							SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMM,YYYY");
							java.util.Date date = sdf1.parse(ad_published);
							sdf1.applyPattern("YYYY-mm-dd");
							date1 = new java.sql.Date(date.getTime());
					//*********ad_published spremna za bazu************//
					
					//*********title spremna za bazu************//
							title = name;
					//*********title spremna za bazu************//
							
					//*********description spremna za bazu************//
							description1 = StringUtils.substringBefore(description, "Tags:");
					//*********description spremna za bazu************//
							
					//*********address spremna za bazu************//
							description1 = StringUtils.substringBefore(description, "\n");
							String[] split = description1.split(",");
							//Stan / kuca
							type_of_property = StringUtils.substringAfter(split[0], " ");
							
							if(description.contains("Adresa:")) {
								String[] addresse = StringUtils.substringsBetween(description, "Adresa:", "\n");
								address = addresse[0];
								br3++;
							}else {
								if(description.contains("Lokacija:") && description.contains("Područje:")) {
									String[] lokacija = StringUtils.substringsBetween(description, "Lokacija:", "\n");
									String[] podrucje1 = StringUtils.substringsBetween(description, "Područje: ", "\n");
									address = lokacija[0] + ", " + podrucje1[0];
									
								}else {
									String[] podrucje1 = StringUtils.substringsBetween(description, "Područje: ", "\n");
									address = podrucje1[0];
									System.out.println("Oglasi bez adrese lokacija podrucje: " + image1);
								}
								
							}
					//*********address spremna za bazu************//
							
					//*********state spremna za bazu************//
							state = "Srbija";
					//*********state spremna za bazu************//
							
					//*********state spremna za bazu************//
							city = "Beograd";
					//*********state spremna za bazu************//
					
					//*********num_of_rooms spremna za bazu************//
							if(description.contains("Spavaćih Soba:")) {
								String[] num_of_rooms1 = StringUtils.substringsBetween(description, "Spavaćih Soba:", "\n");
								num_of_rooms1[0] = num_of_rooms1[0].replace(",", ".");
								num_of_rooms = Float.parseFloat(num_of_rooms1[0]);
							}else {
								String[] polja = StringUtils.substringsBetween(description, "Opis: ", ",");
								
								if(checkIsNumber(polja[0])) {
									num_of_rooms = Float.parseFloat(polja[0]);
								}else if(description.contains("osoban")){
									String [] words = description.split(" ");
									for(int i = 0; i < words.length; i++) {
										if(words[i].contains("osoban")) {
											if(words[i].equalsIgnoreCase("jednosoban")) {
												num_of_rooms = 1;
											}else if(words[i].equalsIgnoreCase("jednoiposoban")) {
												num_of_rooms = 1.5f;
											}
											else if(words[i].equalsIgnoreCase("dvosoban")) {
												num_of_rooms = 2;
											}else if(words[i].equalsIgnoreCase("dvoiposoban")) {
												num_of_rooms = 2.5f;
											}else if(words[i].equalsIgnoreCase("trosoban")) {
												num_of_rooms = 3;
											}else if(words[i].equalsIgnoreCase("troiposoban")) {
												num_of_rooms = 3.5f;
											}else if(words[i].equalsIgnoreCase("cetvorosoban") || words[i].contains("četvorosoban")) {
												num_of_rooms = 4;
											}else if(words[i].equalsIgnoreCase("cetvoroiposoban") || words[i].contains("četvoroiposoban")) {
												num_of_rooms = 4.5f;
											}else if(words[i].equalsIgnoreCase("petosoban")) {
												num_of_rooms = 5;
											}else if(words[i].equalsIgnoreCase("petoiposoban")) {
												num_of_rooms = 5.5f;
											}else if(words[i].equalsIgnoreCase("sestosoban") || words[i].equalsIgnoreCase("šestosoban")) {
												num_of_rooms = 6;
											}else if(words[i].equalsIgnoreCase("sestoiposoban") || words[i].equalsIgnoreCase("šestoiposoban")) {
												num_of_rooms = 6.5f;
											}else if(words[i].equalsIgnoreCase("sedmosoban")) {
												num_of_rooms = 7;
											}
											
											
										}
									}
									
								}else if(description.contains("arsonjer")){
									String [] words = description.split(" ");
									for(int i = 0; i < words.length; i++) {
										if(words[i].equalsIgnoreCase("garsonjera") || words[i].equalsIgnoreCase("garsonjere")) {
											num_of_rooms = 0.5f;
										}
									}
								}
								
								
							}
							
					//*********num_of_rooms spremna za bazu************//
							
					//*********image1, image2 spremna za bazu************//
							//image11 = ImageDownloader.saveImage(image1, "image_1_" + 1);
							//image22 = ImageDownloader.saveImage(image2, "image_2_" + 2);
					//*********image1, image2 spremna za bazu************//
							
					//*********price_per_m spremna za bazu************//
							if(priceLong != null ) {
								price_per_m = priceLong / areasInt;
							}
							
					//*********price_per_m spremna za bazu************//
							
							
					//*********floor spremna za bazu************//
							if(description.contains("prvom ") || description.contains("prvi sprat ") || description.contains("Opis: 1.0,")) {
								System.out.println("prvom");
							}
							if(description.contains("Opis: 2.0,")) {
								String[] floor= StringUtils.substringsBetween(description, " ", "sprat");
									System.out.println("Test: " + floor[0]);
							}
							if(description.contains("treci ") || description.contains("Opis: 3.0,") || description.contains("treci sprat ") || description.contains("treći ") || description.contains("treći sprat")) {
								System.out.println("treci");
							}
							if(description.contains("cetvrt ") || description.contains("četvrt ") || description.contains("Opis: 4.0,")) {
								System.out.println("cetvrt");
							}
					//*********floor spremna za bazu************//
							
					//*********godina izgradnje spremna za bazu**********//
							if(description.contains("Godina Gradnje:")) {
								String[] godinaIzgradnje = StringUtils.substringsBetween(description, "Godina Gradnje: ", "\n");
								building_yer = Integer.parseInt(godinaIzgradnje[0]);
								br2++;
							}
					//*********godina izgradnje spremna za bazu**********//
						
					//*********type_of_property izgradnje spremna za bazu**********//
							if(description.contains("Prodajem Stan")) {
								type_of_property="house";
							}else if(description.contains("Prodajem Kuću")) {
								type_of_property = "apartment";
							}
					//*********type_of_property izgradnje spremna za bazu**********//
							
				}
				
				//AdvertiseWebNekretnine adv = new AdvertiseWebNekretnine(name, url, priceLong, areasInt,
				//		date1, title, description, address, address, floor, num_of_rooms, city,
				//		state, street, price_per_m, image11, image22, type_of_ad, type_of_property, building_yer);

//				ls.add(adv);
//				insert.saveRecord(adv);
				

			}
			}
			// System.out.println("*********************************************************************");
		} catch (IOException | org.json.simple.parser.ParseException e) {
			System.out.println("Greska: ");
			e.printStackTrace();
		}

		System.out.println("Istih: " + br3);
		System.out.println("Broj oglasa: " + br2);
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
