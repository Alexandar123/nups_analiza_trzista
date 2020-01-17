package com.analyze.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/application")
public class PrijavavaZaKolokvijumController {
//
//	private TerminiRepository terminiRrepo;
//	private KorisniciRepository korisniciRepo;
//	private GrupeRepository grupeRepo;
//	static Logger log = LogManager.getLogger(PrijavavaZaKolokvijumController.class);
//
//	@Autowired
//	public PrijavavaZaKolokvijumController(TerminiRepository terminiRrepo, KorisniciRepository korisniciRepo,
//			GrupeRepository grupeRepo) {
//		super();
//		this.terminiRrepo = terminiRrepo;
//		this.korisniciRepo = korisniciRepo;
//		this.grupeRepo = grupeRepo;
//	}
//
//	@PostMapping("/createNew")
//	@ResponseStatus(code = HttpStatus.FOUND)
//	@ResponseBody
//	public int applicationForExam(@RequestBody Termini termin) {
//		log.info("Argument: " + termin);
//		Iterable<Termini> allTermins = terminiRrepo.findAll();
//		int response = 0;
//
//		for (Termini t : allTermins) {
//			if (t.getId_user().getkorisnik_id() == termin.getId_user().getkorisnik_id() 
//					&& t.getId_grupe().getKolokvijum().getId_kolokvijuma() == termin.getId_grupe().getKolokvijum().getId_kolokvijuma()) {
//				response = -1;
//				break;
//			} else {
//
//				log.info("Prijava: " + this.terminiRrepo.save(termin));
//				response = 1; // korisnik nije registrovan za kolokvijum i i uspesno je uradio
//
//			}
//
//		}
//		if (response == 1)
//			this.terminiRrepo.save(termin);
//		return response;
//
//	}
//

}
