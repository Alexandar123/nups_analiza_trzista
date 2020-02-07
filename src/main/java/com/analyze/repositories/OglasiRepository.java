package com.analyze.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.analyze.domain.Oglas;
import com.analyze.model.AdvertiseWebNekretnine;

@Repository
public interface OglasiRepository extends CrudRepository<AdvertiseWebNekretnine, Long>{
	
	@Query(value = "SELECT count(*) FROM test_data5", nativeQuery = true)
	int getNumberOfOglasa();
	
	@Query(value = "SELECT * FROM test_data5 WHERE UPPER(state) LIKE UPPER(?1) AND LOWER(city) LIKE LOWER(?2) AND LOWER(street) LIKE LOWER('%?3%')  ORDER BY date_of_inserting", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdsByStreet(String state, String city,String street);
	
	@Query(value = "SELECT * FROM test_data5 WHERE UPPER(state) LIKE UPPER(?1) AND LOWER(city) LIKE LOWER(?2) ORDER BY date_of_inserting", nativeQuery = true)
	Iterable<AdvertiseWebNekretnine> getAllAdByStateAndCity(String state, String city);
	
	@Query(value = "SELECT count(*) FROM test_data5 WHERE UPPER(state) LIKE UPPER(?1) AND LOWER(city) LIKE LOWER(?2) LIMIT 30", nativeQuery = true)
	int getNumberOfOglasaByCity(String state, String city);
	
	@Query(value = "SELECT * FROM test_data5 WHERE id_ad = ?1 AND LOWER(description) LIKE LOWER(%?2%)", nativeQuery = true)
	AdvertiseWebNekretnine searchWordInAd(int id, String word);
	
	@Query(value = "SELECT * FROM test_data5 WHERE LOWER(city) LIKE LOWER(?1) AND UPPER(state) LIKE UPPER(?2) AND LOWER(description) LIKE LOWER(%?3%) LIMIT 30", nativeQuery = true)
	List<AdvertiseWebNekretnine> searchAdByCityAndWord(String city, String state, String word);
	
	@Query(value = "SELECT DISTINCT city FROM test_data5 WHERE LOWER(city) LIKE LOWER(?1)", nativeQuery = true)
	List<String> getAllCitiesByCountry(String country);
	
	@Query(value = "SELECT * FROM test_data5 WHERE  UPPER(full_address) LIKE UPPER(?1) AND type_of_property = ?2 AND type_of_ad = ?3 AND ad_published BETWEEN ?4 AND ?5 AND areas BETWEEN ?6 AND ?7 AND LOWER(city) LIKE LOWER(?8) AND UPPER(state) LIKE UPPER(?9)", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdByAllParamsForMap(String address, String type_of_property,String type_of_ad, Date from, Date to,
			int areasMin, int areasMax,String city, String state);
	
	@Query(value = "SELECT * FROM test_data5 WHERE  UPPER(full_address) LIKE UPPER(?1) AND type_of_property = ?2 AND type_of_ad = ?3 AND ad_published BETWEEN ?4 AND ?5 AND LOWER(city) LIKE LOWER(?6) AND UPPER(state) LIKE UPPER(?7)", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdByAllParamsForMapNoAreas(String address, String type_of_property,String type_of_ad, Date from, Date to, String city, String state);
	
	@Query(value = "SELECT * FROM test_data5 WHERE id_ad=?1", nativeQuery = true)
	AdvertiseWebNekretnine getAdById(int id);
}
