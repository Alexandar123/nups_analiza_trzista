package com.analyze.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.repositories.TerminiRepository;

@RestController
@RequestMapping("api/termins")
public class TerminiController {
//
//	private TerminiRepository terminiRepo;
//	static Logger log = LogManager.getLogger(TerminiController.class);
//
//	@Autowired
//	public TerminiController(TerminiRepository terminiRepo) {
//		super();
//		this.terminiRepo = terminiRepo;
//	}
//
//	@GetMapping("/{idGrupe}")
//	public Iterable<Korisnik> getAllUsersByGroup(@PathVariable("idGrupe") Integer idGrupe) {
//		log.info("ID GRUPE: " + idGrupe);
//		Iterable<Termini> termini = terminiRepo.findAll();
//		List<Korisnik> korisniciPoGrupama = new ArrayList<Korisnik>();
//
//		for (Termini termin : termini) {
//			if (termin.getId_grupe().getId_grupe() == idGrupe)
//				log.info("Termini: " + termin.toString());
//			// korisniciPoGrupama.add(termin.getId_user());
//		}
//		return korisniciPoGrupama;
//	}
//
//	@GetMapping("")
//	public Iterable<TempTermin> getAll() {
//		Iterable<Termini> svi = this.terminiRepo.findAll();
//		List<TempTermin> tempSvi = new ArrayList<TempTermin>();
//		TempTermin temp;
//		for (Termini t : svi) {
//			temp = new TempTermin(t);
//			tempSvi.add(temp);
//			log.info("Prikaz u ter: " + t);
//		}
//		return tempSvi;
//	}
//
//	@GetMapping("/userByExam/{idExam}")
//	public Iterable<TempTermin> getAllUsersByExam(@PathVariable("idExam") Integer idExam) {
//
//		Korisnik k1 = new Korisnik(1, "Marko", "1234", "12343", 0);
//		Korisnik k2 = new Korisnik(2, "Marko", "1234", "12343", 0);
//		Korisnik k3 = new Korisnik(3, "Marko", "1234", "12343", 0);
//		Korisnik k4 = new Korisnik(3, "Marko", "1234", "12343", 0);
//
//		Iterable<Termini> termini = terminiRepo.findAll();
//		List<TempTermin> korisniciPoKolokvijumimaTermini = new ArrayList<TempTermin>();
//
//		for (Termini termin : termini) {
//			if (termin.getId_grupe().getKolokvijum().getId_kolokvijuma() == idExam
//					&& termin.getId_user().getFlag().equals(0)) {
//				log.info("IF POTVRDA" + termin);
//				TempTermin tmp = new TempTermin(termin);
//				korisniciPoKolokvijumimaTermini.add(tmp);
//			}
//
//		}
//		return korisniciPoKolokvijumimaTermini;
//	}
//
//	@GetMapping("/order/{idExam}/{criteria}")
//	public @ResponseStatus Iterable<TempTermin> getOrderedUserByCriteria(@PathVariable("idExam") Integer idExam,
//			@PathVariable("criteria") String criteria) {
//
//		Iterable<Termini> termini = terminiRepo.findAll();
//		List<TempTermin> korisniciPoKolokvijumimaTermini = new ArrayList<TempTermin>();
//
//		for (Termini termin : termini) {
//			if (termin.getId_grupe().getKolokvijum().getId_kolokvijuma() == idExam
//					&& termin.getId_user().getKriterijum().getNaziv().equalsIgnoreCase(criteria)) {
//				log.info("IF POTVRDA" + termin);
//				TempTermin tmp = new TempTermin(termin);
//				korisniciPoKolokvijumimaTermini.add(tmp);
//			}
//
//		}
//		Collections.sort(korisniciPoKolokvijumimaTermini);
//
//		return korisniciPoKolokvijumimaTermini;
//	}
//
//	@RequestMapping(value = "/new", method = RequestMethod.POST)
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public Termini userToGroup(@RequestBody Korisnik kor, Grupe grupa) {
//		Iterable<Termini> sviTermini = this.terminiRepo.findAll();
//		Termini t = null;
//		for (Termini termin : sviTermini) {
//			if (termin.getId_user().getkorisnik_id() == kor.getkorisnik_id()) {
//				return null;
//			}
//			else {
//				t = new Termini(grupa, kor);
//				this.terminiRepo.save(t);
//			}
//
//		}
//
//		log.info("Create new termin" + this.terminiRepo.save(t));
//		return t;
//	}

}
