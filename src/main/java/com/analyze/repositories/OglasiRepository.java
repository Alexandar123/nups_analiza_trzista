package com.analyze.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.analyze.domain.Oglas;
import com.analyze.model.AdvertiseForGraph;
import com.analyze.model.AdvertiseWebNekretnine;

@Repository
public interface OglasiRepository extends CrudRepository<AdvertiseWebNekretnine, Long>{
	@Query(value = "SELECT * FROM test_data5 LIMIT 50 ", nativeQuery = true)
	List<AdvertiseWebNekretnine> getLimitAd();
	
	@Query(value = "SELECT count(*) FROM test_data5 ", nativeQuery = true)
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
	
	@Query(value = "SELECT * FROM test_data5 WHERE  UPPER(full_address) LIKE %:address% AND type_of_property = :type_of_property AND areas BETWEEN :areasmin AND :areasmax AND LOWER(city) LIKE :city AND UPPER(state) LIKE :state AND type_of_ad = :type_of_ad AND ad_published BETWEEN :from AND :to", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdByAllParamsForMap(@Param("address") String address,@Param("type_of_property") String type_of_property, @Param("type_of_ad") String type_of_ad,@Param("from") Date from,@Param("to") Date to,@Param("areasmin") int areasmin, @Param("areasmax") int areasmax, @Param("city") String city,@Param("state")  String state);
	
	@Query(value = "SELECT * FROM test_data5 WHERE  UPPER(full_address) LIKE %:address% AND type_of_property = :type_of_property  AND LOWER(city) LIKE :city AND UPPER(state) LIKE :state AND type_of_ad = :type_of_ad AND ad_published BETWEEN :from AND :to", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdByAllParamsForMapNoAreas(@Param("address") String address,@Param("type_of_property") String type_of_property, @Param("type_of_ad") String type_of_ad,@Param("from") Date from,@Param("to") Date to,@Param("city") String city,@Param("state")  String state);
	
	@Query(value = "SELECT * FROM test_data5 WHERE  UPPER(full_address) LIKE %:address% LIMIT 50", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdS(@Param("address") String address);
	
	@Query(value = "SELECT * FROM test_data5 WHERE id_ad=?1", nativeQuery = true)
	AdvertiseWebNekretnine getAdById(int id);
	
	@Query(value = "SELECT id_ad, price_per_m, price, areas FROM test_data5 WHERE type_of_property = ?1 AND ad_published BETWEEN ?2 AND ?3", nativeQuery = true)
	List<Object> getAdsForGraphicMedian(String type_of_property, Date ad_published_from, Date ad_published_to);
}
