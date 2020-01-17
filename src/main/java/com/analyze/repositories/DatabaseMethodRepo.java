package com.analyze.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.analyze.model.Advertise;
import com.analyze.model.AdvertiseWebNekretnine;

public interface DatabaseMethodRepo extends CrudRepository<AdvertiseWebNekretnine, Long>{

	//public void insert(Advertise adv);
	//public List<Advertise> getAllAd();
}
