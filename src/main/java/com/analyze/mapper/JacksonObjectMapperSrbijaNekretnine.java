package com.analyze.mapper;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.analyze.validation.ValidationImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonObjectMapperSrbijaNekretnine {
	public static String prefix = "bg";// PREFIX FOR CITE
	public static String DATE = "10_12_2019"; // DATE OF DOWNLOADING DATA
	public static String NAME_OF_FILE = "_srbija_nekretnine_";
	
	public static void main(String[] args) {
		ValidationImpl validation = new ValidationImpl();
		int imgCounter = 0;
		String BASE = "C:\\Users\\agordic\\Desktop\\DataTrziste\\Podaci\\";
		String EXTENSION = ".json";
		// create ObjectMapper instance

		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<String> list = new ArrayList<String>();
		int br=0;
		for (int i = 0; i < 91; i++) { // Name of cite for srbija nekrtnine site
			try {
				String FILE;// NAME OF FILE WITHOUT EXTENSION AND BASE
				

				FILE = prefix + NAME_OF_FILE + DATE + "_";
				FILE += 2;// Number of documents

				//System.out.println(BASE+FILE+EXTENSION);
				// read customer.json file into tree model
				JsonNode rootNode;
				rootNode = objectMapper.readTree(new File(BASE + FILE + EXTENSION));

				String image = rootNode.path("images").get(i).path("url").asText();
				String imageUrl = rootNode.path("images").get(i).path("image").asText();
				String price = rootNode.path("images").get(0).path("price").asText();// Price as a String
				String areas = rootNode.path("images").get(0).path("areas").asText();
				String num_of_rooms = rootNode.path("images").get(0).path("num_of_rooms").asText();
				String price_per_m = rootNode.path("images").get(i).path("price_per_m").asText();
				String address = rootNode.path("images").get(0).path("address2").asText();
				String heat = rootNode.path("images").get(0).path("grejanje").asText();
				String floor = rootNode.path("images").get(0).path("floor").asText();
				String ad_published = rootNode.path("images").get(0).path("ad_published").asText();
				
				list.add(image);
				
				
				
				//Prepare image url to ad
				//System.out.println(image);

				//*********Prepared IMG for download and save in DB****************
				imgCounter++;

				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date(System.currentTimeMillis());
				String nameForImage = formatter.format(date).toString() + "_img_" + imgCounter;
				//ImageDownloader.saveImage(imageUrl, nameForImage);
				
				//*********Prepared IMG for download and save in DB****************
				
				//Price per meter square format for DB
				String price_per_meter = price_per_m.replace("€ ", "");
				price_per_meter = price_per_meter.replace(".", "");
				float price_per_m2 = Float.parseFloat(price_per_meter);
				//Price per meter square format for DB
				
				// Price prepare for DB
				String price1 = price.replace(".", "");// Replaced . in price data
				int price2 = Integer.parseInt(price1);// Parse String to Int
				// Price prepare for DB
				
				
				System.out.println("*********************************************************************");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Do we have the same URL for ad? 0 no >0 yes : " + validation.checkUniqueAd(list));
		System.out.println(br);
		System.out.println("Number of ad: " + imgCounter);

	}

}
