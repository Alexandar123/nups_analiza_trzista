package com.analyze.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/types")
public class TipoviKorisnikaController {

//	private TipoviKorisnikaRepository tipoviKorisnikaRepository;
//
//	static Logger log = LogManager.getLogger(TipoviKorisnikaController.class);
//
//	@Autowired
//	public TipoviKorisnikaController(TipoviKorisnikaRepository tipoviKorisnikaRepository) {
//		super();
//		this.tipoviKorisnikaRepository = tipoviKorisnikaRepository;
//	}
//
//	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
//	public Iterable<TipoviKorisnika> getAllTypesOfUsers() {
//		return tipoviKorisnikaRepository.findAll();
//	}
//
//	@GetMapping(value = "/{id}")
//	@ResponseStatus(code = HttpStatus.FOUND)
//	public Optional<TipoviKorisnika> getTypeById(@PathVariable("id") Integer id) {
//		return tipoviKorisnikaRepository.findById(id);
//	}
//
//	@RequestMapping(value = "/new", method = RequestMethod.POST)
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public TipoviKorisnika createNewType(@RequestBody TipoviKorisnika tipKorisnika) {
//		log.info("Naziv grupe: " + tipKorisnika);
//		TipoviKorisnika newtipKorisnika = tipoviKorisnikaRepository.save(tipKorisnika);
//
//		return newtipKorisnika;
//	}
//
//	@RequestMapping(value = "{id}/delete", method = RequestMethod.DELETE)
//	public void deleteGroupById(@PathVariable("id") int id) {
//		tipoviKorisnikaRepository.deleteById(id);
//	}
//
//	@PutMapping("/update/{id}")
//	
//	public Optional<Object> updatePost(@PathVariable Integer id, @Valid @RequestBody TipoviKorisnika postRequest) {
//		return tipoviKorisnikaRepository.findById(id).map(post -> {
//
//			post.setTip(post.getTip());
//			return tipoviKorisnikaRepository.save(post);
//		});
//	}

}
