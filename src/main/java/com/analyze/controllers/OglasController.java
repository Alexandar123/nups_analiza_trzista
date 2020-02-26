package com.analyze.controllers;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.domain.Oglas;
import com.analyze.model.AdvertiseForGraph;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.OglasiRepository;

@RestController
@RequestMapping("api/geo")
public class OglasController {

	private OglasiRepository oglasRepo;
	static Logger log = LogManager.getLogger(OglasController.class);

	@Autowired
	public OglasController(OglasiRepository oglasRepo) {
		this.oglasRepo = oglasRepo;
	}

	/// @Produces({ MediaType.APPLICATION_JSON })
	// @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	// public Iterable<Oglas> getAllOglas() {
	// Iterable<Oglas> sve = oglasRepo.findAll();
	// for (Oglas g : sve)
	// log.info("SVE: " + g);
	// return this.oglasRepo.findAll();
	// }

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllOglasWeb() {
		Iterable<AdvertiseWebNekretnine> sve = oglasRepo.findAll();
		// for (AdvertiseWebNekretnine g : sve)
		// log.info("SVE: " + g);
		return this.oglasRepo.findAll();
	}
	
	@GetMapping("/number")
	public int getNumberOfOglas() {
		return oglasRepo.getNumberOfOglasa();
	}

	@GetMapping("/number/{state}/{city}")
	public int getNumberOfAdByCity(@PathVariable("state") String state, @PathVariable("city") String city) {
		return oglasRepo.getNumberOfOglasaByCity(state, city);
	}
	
	
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/city/{state}/{city}", method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllAdByCountryAndCity(@PathVariable("state") String state,
			@PathVariable("city") String city) {
		Iterable<AdvertiseWebNekretnine> getAll = oglasRepo.getAllAdByStateAndCity(state, city);
		// for(AdvertiseWebNekretnine ad : getAll) {
		// log.info("Podaci: " + ad);
		// }

		return getAll;
	}
	
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/limitAd", method = RequestMethod.GET)
	public List<AdvertiseWebNekretnine> getLimitAd() {
		List<AdvertiseWebNekretnine> getAll = oglasRepo.getLimitAd();

		return getAll;
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{address}/{type_of_property}/{type_of_ad}/{date_from}/{date_to}/{areasmin}/{areasmax}/{city}/{state}", method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllAdByAllParamsForMap(@PathVariable("address") String address,
			@PathVariable("type_of_property") String type_of_property, @PathVariable("type_of_ad") String type_of_ad,
			@PathVariable("date_from") String from, @PathVariable("date_to") String to, @PathVariable("areasmin") int areasMin,@PathVariable("areasmax") int areasMax,
			@PathVariable("city") String city, @PathVariable("state") String state) {
		log.info("USao");
		
		java.sql.Date toD = java.sql.Date.valueOf(to);
		java.sql.Date fromD = java.sql.Date.valueOf(from);
		return oglasRepo.getAllAdByAllParamsForMap(address, type_of_property, type_of_ad, fromD, toD, areasMin, areasMax, city, state);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{address}/{type_of_property}/{type_of_ad}/{date_from}/{date_to}/{city}/{state}", method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllAdByAllParamsForMapNoAreas(@PathVariable("address") String address,
			@PathVariable("type_of_property") String type_of_property, @PathVariable("type_of_ad") String type_of_ad,
			@PathVariable("date_from") String from, @PathVariable("date_to") String to, @PathVariable("city") String city,
			@PathVariable("state") String state) {
		
		
		java.sql.Date toD = java.sql.Date.valueOf(to);
		java.sql.Date fromD = java.sql.Date.valueOf(from);
		return oglasRepo.getAllAdByAllParamsForMapNoAreas(address.toUpperCase(), type_of_property, type_of_ad, fromD, toD, city.toLowerCase(), state.toUpperCase());
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{id}/{word}")
	public AdvertiseWebNekretnine searchWordInAd(@PathVariable("id") int id, @PathVariable("word") String word) {
		return oglasRepo.searchWordInAd(id, word);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{city}/{state}/{word}")
	public List<AdvertiseWebNekretnine> searchAdByCityAndWord(@PathVariable("city") String city,
			@PathVariable("state") String state, @PathVariable("word") String word) {
		String cityL = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
		String stateL = state.substring(0, 1).toUpperCase() + state.substring(1).toLowerCase();
		return oglasRepo.searchAdByCityAndWord(cityL, stateL, word);
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/allCities/{country}")
	public List<String> getAllCitiesByCountry(@PathVariable("country") String country) {
		return oglasRepo.getAllCitiesByCountry(country);
	}
	
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/allAds/{address}")
	public List<AdvertiseWebNekretnine> allAds(@PathVariable("address") String address) {
		return oglasRepo.getAllAdS(address.toUpperCase());
	}

	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/search/{id}", method = RequestMethod.GET)
	public AdvertiseWebNekretnine getAdById(@PathVariable("id") int id) {
		return oglasRepo.getAdById(id);
	}
	
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = "/graphMedian/{type_of_property}/{date_from}/{date_to}", method = RequestMethod.GET)
	public List<AdvertiseForGraph> getAdsForGraphicMedian(@PathVariable("type_of_property") String type_of_property,@PathVariable("date_from") Date date_from,@PathVariable("date_to") Date date_to) {
		List<Object> adv = oglasRepo.getAdsForGraphicMedian(type_of_property, date_from, date_to);
		List<AdvertiseForGraph> adv1 = new ArrayList<AdvertiseForGraph>();
		Iterator itr = adv.iterator();
		while(itr.hasNext()){
			Object[] obj = (Object[]) itr.next();
			int id_ad = Integer.parseInt(String.valueOf(obj[0]));
			double price_per_m = Double.parseDouble(String.valueOf(obj[1]));
			int price = Integer.parseInt(String.valueOf(obj[2]));
			int areas = Integer.parseInt(String.valueOf(obj[3]));
			
			AdvertiseForGraph tmp = new AdvertiseForGraph(id_ad, price_per_m, price,areas);
			adv1.add(tmp);
		}
		/*for(Object t : adv) {		
			AdvertiseForGraph tmp = new AdvertiseForGraph(((AdvertiseForGraph)t).getId_ad(),((AdvertiseForGraph)t).getPrice_per_m(),((AdvertiseForGraph)t).getPrice(),((AdvertiseForGraph)t).getAreas());
			adv1.add(tmp);
		}*/
		return adv1;
	}
}
