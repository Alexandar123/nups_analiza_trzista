package com.analyze.controllers;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.model.AdvertiseForGraph;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.OglasiRepository;

@RestController
@RequestMapping("/api/geo")
public class OglasController {

	private OglasiRepository oglasRepo;
	static Logger log = LogManager.getLogger(OglasController.class);

	@Autowired
	public OglasController(OglasiRepository oglasRepo) {
		this.oglasRepo = oglasRepo;
	}

	@GetMapping("/number")
	public int getNumberOfOglas() {
		return oglasRepo.getNumberOfOglasa();
	}

	@GetMapping("/number/{state}/{city}")
	public int getNumberOfAdByCity(@PathVariable("state") String state, @PathVariable("city") String city) {
		return oglasRepo.countByStateAndCity(state.toUpperCase(), city.toLowerCase());
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/city/{state}/{city}", method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllAdByCountryAndCity(@PathVariable("state") String state,
			@PathVariable("city") String city) {
		return oglasRepo.getAllAdByStateAndCity(state, city);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{id}/{word}")
	public AdvertiseWebNekretnine searchWordInAd(@PathVariable("id") int id, @PathVariable("word") String word) {
		return oglasRepo.searchWordInAd(id, word);
	}

	@GetMapping("/countByParams/{date_from}/{date_to}")
	public int countOfCityByParams(
			@PathVariable("date_from") String from, @PathVariable("date_to") String to) {

		java.sql.Date toD = java.sql.Date.valueOf(to);
		java.sql.Date fromD = java.sql.Date.valueOf(from);
		if(fromD.after(toD)) return -1;
		return oglasRepo.countOfCityByParams(fromD, toD);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{state}/{city}/{type_of_property}/{type_of_ad}/{date_from}/{date_to}", method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllAdByAllParamsForMapNoAreas(@PathVariable("state") String state,
			@PathVariable("city") String city, @PathVariable("type_of_property") String type_of_property,
			@PathVariable("type_of_ad") String type_of_ad, @PathVariable("date_from") String from,
			@PathVariable("date_to") String to) {

		java.sql.Date toD = java.sql.Date.valueOf(to);
		java.sql.Date fromD = java.sql.Date.valueOf(from);
		if(fromD.after(toD)) return null;
		return oglasRepo.getAllAdByAllParamsForMapNoAreas(state.toUpperCase(), city.toLowerCase(),
				type_of_property.toLowerCase(), type_of_ad.toLowerCase(), fromD, toD);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{state}/{city}/{areasmin}/{areasmax}/{type_of_property}/{type_of_ad}/{date_from}/{date_to}", method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllAdByAllParamsForMap(@PathVariable("state") String state,
			@PathVariable("city") String city, @PathVariable("areasmin") int areasMin,
			@PathVariable("areasmax") int areasMax, @PathVariable("type_of_property") String type_of_property,
			@PathVariable("type_of_ad") String type_of_ad, @PathVariable("date_from") String from,
			@PathVariable("date_to") String to) {
		log.info("Request sa areas");

		java.sql.Date toD = java.sql.Date.valueOf(to);
		java.sql.Date fromD = java.sql.Date.valueOf(from);
		if(fromD.after(toD)) return null;
		return oglasRepo.getAllAdByAllParamsForMap(state.toUpperCase(), city.toLowerCase(), areasMin, areasMax,
				type_of_property.toLowerCase(), type_of_ad.toLowerCase(), fromD, toD);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/allCities/{country}")
	public List<String> getAllCitiesByCountry(@PathVariable("country") String country) {
		return oglasRepo.getAllCitiesByCountry(country);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	public AdvertiseWebNekretnine getAdById(@PathVariable("id") int id) {
		return oglasRepo.getAdById(id);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/graphMedian/{type_of_property}/{date_from}/{date_to}", method = RequestMethod.GET)
	public List<AdvertiseForGraph> getAdsForGraphicMedian(@PathVariable("type_of_property") String type_of_property,
			@PathVariable("date_from") Date date_from, @PathVariable("date_to") Date date_to) {
		List<Object> adv = oglasRepo.getAdsForGraphicMedian(type_of_property, date_from, date_to);
		List<AdvertiseForGraph> adv1 = new ArrayList<AdvertiseForGraph>();
		Iterator itr = adv.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			int id_ad = Integer.parseInt(String.valueOf(obj[0]));
			double price_per_m = Double.parseDouble(String.valueOf(obj[1]));
			int price = Integer.parseInt(String.valueOf(obj[2]));
			int areas = Integer.parseInt(String.valueOf(obj[3]));

			AdvertiseForGraph tmp = new AdvertiseForGraph(id_ad, price_per_m, price, areas);
			adv1.add(tmp);
		}
		return adv1;
	}

	@RequestMapping(value = "/lat/{city}")
	public List<Object[]> getByCity(@PathVariable("city") String city) {
		return oglasRepo.getLatLonAdsByCity(city);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/get/{type_of_property}/{type_of_ad}", method = RequestMethod.GET)
	public Map<String, HashMap<Integer, Float>> graphAverageByTypeOfProperty(
			@PathVariable("type_of_property") String type_of_property, @PathVariable("type_of_ad") String type_of_ad) {
		List<AdvertiseWebNekretnine> house = oglasRepo.getAllAdsByTypeOfPropert(type_of_property, type_of_ad);

		TreeSet<Integer> uniqueYear = new TreeSet<>();
		List<Float> ls1 = null;
		List<Float> ls2 = new ArrayList<>();
		Map<Integer, List<Float>> prices1 = new HashMap<Integer, List<Float>>();
		Map<Integer, List<Float>> prices2 = new HashMap<Integer, List<Float>>();
		HashMap<Integer, Float> pricePerYear1 = new HashMap<Integer, Float>();
		HashMap<Integer, Float> pricePerYear2 = new HashMap<Integer, Float>();

		int year, br = 0;
		int months;
		// Logika za kucu
		for (AdvertiseWebNekretnine advH : house) {
			year = Integer.parseInt(advH.getAd_published().toString().substring(0, 4));
			uniqueYear.add(year);// Unikatne godine kako bi pokupili podatke za sve godine
		}

		Iterator iterator = uniqueYear.iterator();
		while (iterator.hasNext()) {
			int i = (int) iterator.next();
			ls1 = new ArrayList<>();
			ls2 = new ArrayList<>();
			ls1.clear();
			ls2.clear();
			for (AdvertiseWebNekretnine advH : house) {
				months = Integer.parseInt(advH.getAd_published().toString().substring(5, 7));
				year = Integer.parseInt(advH.getAd_published().toString().substring(0, 4));
				if (type_of_ad.equalsIgnoreCase("rent")) {
					if ((months >= 1 && months <= 6) && year == i) {
						ls1.add(advH.getPrice().floatValue());
					}
					if ((months >= 6 && months <= 12) && year == i) {
						ls2.add(advH.getPrice().floatValue());
					}
				} else {
					if ((months >= 1 && months <= 6) && year == i) {
						ls1.add(advH.getPrice_per_m());
					}
					if ((months >= 6 && months <= 12) && year == i) {
						ls2.add(advH.getPrice_per_m());
					}
				}
				if (year > i)
					break;

			}
			prices1.put(i, ls1);// Prva polovina godine
			prices2.put(i, ls2);// Druga polovina godine

		}

		ArrayList<Float> avgList = new ArrayList<Float>();
		float prosek, sum = 0;
		int br1 = 0;
		// **************PRVA POLOVINA GODINE*****************
		for (Map.Entry<Integer, List<Float>> e : prices1.entrySet()) {
			for (Float e1 : e.getValue()) {
				avgList.add(e1.floatValue());
				// log.info("Podaci: " + e.getKey() + " = "+ e1.floatValue());
				sum += e1.floatValue();
				br1++;
			}
			prosek = (float) sum / br1;
			sum = 0;
			br1 = 0;
			avgList.clear();
			pricePerYear1.put(e.getKey(), prosek);
		}

		// ******************PRVA POLOVINA GODINE ****************************

		// ******************DRUGA POLOVINA GODINE ****************************
		// PROVERITI ISPIS NIJE DOBAR FOR PETLJA NE RADI!!!!
		for (Map.Entry<Integer, List<Float>> e : prices2.entrySet()) {
			for (Float e1 : e.getValue()) {
				avgList.add(e1.floatValue());
				// log.info("Podaci: " + e.getKey() + " = "+ e1.floatValue());
				sum += e1.floatValue();
				br1++;
			}
			prosek = (float) sum / br1;
			sum = 0;
			br1 = 0;
			pricePerYear2.put(e.getKey(), prosek);
			avgList.clear();
		}

		// ******************DRUGA POLOVINA GODINE ****************************

		Map<String, HashMap<Integer, Float>> map = new HashMap();
		map.put("firstHalfOfYear", pricePerYear1);
		map.put("secondHalfOfYear", pricePerYear2);
		return map;
	}

}
