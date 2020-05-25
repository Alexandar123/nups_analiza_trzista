package com.analyze.controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.mapper.AdsData;
import com.analyze.model.Administration;
import com.analyze.repositories.AdministrationRepo;

@RestController
@RequestMapping("/api/geo/admin")
public class AdministrationController {

	AdministrationRepo administrationRepo;

	@Autowired
	public AdministrationController(AdministrationRepo administrationRepo) {
		this.administrationRepo = administrationRepo;
	}

	@GetMapping("/showdata/{from}/{to}")
	public List<Administration> clearDB(@PathVariable("from") Date from, @PathVariable("to") Date to) {
		Iterable<Object> listO = administrationRepo.showData(from, to);
		List<Administration> ads = new ArrayList<Administration>();
		
		Iterator itr = listO.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			Long id_ad = Long.parseLong(String.valueOf(obj[0]));
			String name = String.valueOf(obj[1]);
			String url = String.valueOf(obj[2]);
			Long price = Long.parseLong(String.valueOf(3));
			int areas = Integer.parseInt(String.valueOf(obj[4]));
			java.sql.Date toD = java.sql.Date.valueOf(String.valueOf(obj[5]));
			java.sql.Date fromD = java.sql.Date.valueOf(String.valueOf(obj[6]));
			String title = String.valueOf(obj[7]);
			String city = String.valueOf(obj[8]);
			String state = String.valueOf(obj[9]);
			String street = String.valueOf(obj[10]);
			float price_per_m = Float.parseFloat(String.valueOf(obj[11]));
			String type_of_ad = String.valueOf(obj[12]);
			String type_of_property = String.valueOf(obj[13]);
			int active = Integer.parseInt(String.valueOf(obj[14]));

			Administration tmp = new Administration(id_ad, name, url, price, areas, toD, fromD, title, city, state, street, price_per_m, type_of_ad, type_of_property,active);
			ads.add(tmp);
		}
		
		return ads;
	}
	
	@DeleteMapping("/delete")
	public void clearData() {
		administrationRepo.clearData();
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		administrationRepo.deleteById(id);
	} 	
	
}
