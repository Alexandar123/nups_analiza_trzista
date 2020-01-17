package com.analyze.controllers;

import java.util.List;

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
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.OglasiRepository;

@RestController
@RequestMapping("api/advertisement")
public class OglasController {

	private OglasiRepository oglasRepo;
	static Logger log = LogManager.getLogger(OglasController.class);
	
	@Autowired
	public OglasController(OglasiRepository oglasRepo) {
		this.oglasRepo = oglasRepo;
	}
	
	///@Produces({ MediaType.APPLICATION_JSON })
	//@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	//public Iterable<Oglas> getAllOglas() {
	//	Iterable<Oglas> sve = oglasRepo.findAll();
	//	for (Oglas g : sve)
	//		log.info("SVE: " + g);
	//	return this.oglasRepo.findAll();
	//}
	
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public Iterable<AdvertiseWebNekretnine> getAllOglasWeb() {
		Iterable<AdvertiseWebNekretnine> sve = oglasRepo.findAll();
		//for (AdvertiseWebNekretnine g : sve)
			//log.info("SVE: " + g);
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
	public Iterable<AdvertiseWebNekretnine> getAllAdByCountryAndCity(@PathVariable("state") String state, @PathVariable("city") String city){
		Iterable<AdvertiseWebNekretnine> getAll = oglasRepo.getAllAdByStateAndCity(state, city);
		//for(AdvertiseWebNekretnine ad : getAll) {
		//	log.info("Podaci: " + ad);
		//}
		
		return getAll;
	}
	
	@Produces({ MediaType.APPLICATION_JSON} )
	@RequestMapping(value="/search/{id}/{word}")
	public AdvertiseWebNekretnine searchWordInAd(@PathVariable("id") int id, @PathVariable("word") String word) {
		return oglasRepo.searchWordInAd(id, word);
	}
	
	@Produces({ MediaType.APPLICATION_JSON} )
	@RequestMapping(value="/search/{city}/{state}/{word}")
	public List<AdvertiseWebNekretnine> searchAdByCityAndWord(@PathVariable("city") String city, @PathVariable("state") String state, @PathVariable("word") String word) {
		String cityL = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
		String stateL = state.substring(0, 1).toUpperCase() + state.substring(1).toLowerCase();
		return oglasRepo.searchAdByCityAndWord(cityL,stateL, word);
	}
	
	@Produces({ MediaType.APPLICATION_JSON} )
	@RequestMapping(value="/allCities/{country}")
	public List<String> getAllCitiesByCountry(@PathVariable("country") String country) {
		return oglasRepo.getAllCitiesByCountry(country);
	}
	
	@Produces({ MediaType.APPLICATION_JSON })
	@RequestMapping(value="/search/{id}", method = RequestMethod.GET)
	public AdvertiseWebNekretnine getAdById(@PathVariable("id") int id) {
		return oglasRepo.getAdById(id);
	}
}

