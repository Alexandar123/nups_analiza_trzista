package com.analyze.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.analyze.WebPageScreenShotTaker;
import com.analyze.configuration.InsertRecordInDatabaseWithJdbcTemplate;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.OglasiRepository;

@Component
public class AddScreenShotToAdController {
	public static void main(String[] args) throws IOException {

		List<AdvertiseWebNekretnine> listOfAd = InsertRecordInDatabaseWithJdbcTemplate.getData("test_data5");
		
		insertScreenShotForAd(listOfAd);
		//checkAndUpdateIfAdExist();
	}

	public static void insertScreenShotForAd(List<AdvertiseWebNekretnine> listOfAd) throws IOException {
		WebPageScreenShotTaker screenShot = new WebPageScreenShotTaker();
		byte[] bImg = null;
		int br = 0;
		
		for (int i = 5108; i < listOfAd.size(); i++) {
			if (checkIfAdExist(listOfAd.get(i).getUrl())) {
				bImg = screenShot.screenShot(listOfAd.get(i).getUrl());
				InsertRecordInDatabaseWithJdbcTemplate.updateData(listOfAd.get(i).getId(), "test_data5", "screenshot", bImg);
				br++;
				System.out.println(listOfAd.get(i).getUrl());
			}else {
				br++;
			}
			System.out.println("Broj oglasa: " + i);
System.out.println("Oglasi koji su istekli: " + br);
		}
	}

	public static boolean checkIfAdExist(String url) throws IOException {
		URL u = new URL(url);
		HttpURLConnection huc = (HttpURLConnection) u.openConnection();
		huc.setFollowRedirects(false);
		huc.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
		huc.setRequestMethod("HEAD");
		if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
			System.out.println("OK stauts: ");
		}
		huc.connect();
		int code = huc.getResponseCode();
		System.out.println("Kod: " + code);
		if (code == 200) {
			return true;
		} else if (code == 404) {
			return false;
		}
		return false;
	}
}
