package com.analyze.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.analyze.domain.Oglas;
import com.analyze.model.AdvertiseWebNekretnine;

@Repository
public interface OglasiRepository extends CrudRepository<AdvertiseWebNekretnine, Long>{
	
	@Query(value = "SELECT count(*) FROM test_data3", nativeQuery = true)
	int getNumberOfOglasa();
	
	@Query(value = "SELECT * FROM test_data3 WHERE state = ?1 AND city=?2 ORDER BY date_of_inserting", nativeQuery = true)
	Iterable<AdvertiseWebNekretnine> getAllAdByStateAndCity(String state, String city);
	
	@Query(value = "SELECT count(*) FROM test_data3 WHERE state = ?1 AND city=?2", nativeQuery = true)
	int getNumberOfOglasaByCity(String state, String city);
	
	@Query(value = "SELECT * FROM test_data3 WHERE id_ad = ?1 AND description LIKE %?2%", nativeQuery = true)
	AdvertiseWebNekretnine searchWordInAd(int id, String word);
	
	@Query(value = "SELECT * FROM test_data3 WHERE city = ?1 AND state = ?2 AND description LIKE %?3% LIMIT 30", nativeQuery = true)
	List<AdvertiseWebNekretnine> searchAdByCityAndWord(String city, String state, String word);
	
	@Query(value = "SELECT DISTINCT city FROM test_data3 WHERE state=?1", nativeQuery = true)
	List<String> getAllCitiesByCountry(String country);

	@Query(value = "SELECT * FROM test_data3 WHERE id_ad=?1", nativeQuery = true)
	AdvertiseWebNekretnine getAdById(int id);
}
