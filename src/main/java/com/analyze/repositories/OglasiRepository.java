package com.analyze.repositories;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.analyze.model.AdvForMainGraphs;
import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.model.TableAds;

@Repository
public interface OglasiRepository extends CrudRepository<AdvertiseWebNekretnine, Long>{

	@Query(value = "SELECT count(id_ad) FROM test_data5_no_image ", nativeQuery = true)
	int getNumberOfOglasa();
	
	int countByStateAndCity(String state, String city);
	
	@Query(value = "SELECT * FROM test_data5_no_image WHERE UPPER(state) LIKE UPPER(?1) AND LOWER(city) LIKE LOWER(?2) AND LOWER(street) LIKE LOWER('%?3%')  ORDER BY date_of_inserting", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdsByStreet(String state, String city,String street);
	
	@Query(value = "SELECT * FROM test_data5_no_image WHERE UPPER(state) LIKE UPPER(?1) AND LOWER(city) LIKE LOWER(?2) ORDER BY date_of_inserting", nativeQuery = true)
	Iterable<AdvertiseWebNekretnine> getAllAdByStateAndCity(String state, String city);
	
	@Query(value = "SELECT count(*) FROM test_data5_no_image WHERE UPPER(state) LIKE UPPER(?1) AND LOWER(city) LIKE LOWER(?2) LIMIT 30", nativeQuery = true)
	int getNumberOfOglasaByCity(String state, String city);
	
	@Query(value = "SELECT * FROM test_data5_no_image WHERE id_ad = ?1 AND LOWER(description) LIKE LOWER(%?2%)", nativeQuery = true)
	AdvertiseWebNekretnine searchWordInAd(int id, String word);

	@Query(value = "SELECT DISTINCT city FROM test_data5_no_image WHERE LOWER(city) LIKE LOWER(?1)", nativeQuery = true)
	List<String> getAllCitiesByCountry(String country);
	
	/*@Query(value = "SELECT * FROM test_data5_no_image WHERE  state LIKE :state AND city LIKE :city AND type_of_property = :type_of_property AND type_of_ad = :type_of_ad AND areas BETWEEN :areasmin AND :areasmax AND ad_removed >= :from AND ad_published <= :to", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdByAllParamsForMap(@Param("state") String state, @Param("city") String city,@Param("areasmin") int areasmin, @Param("areasmax") int areasmax, @Param("type_of_property") String type_of_property, @Param("type_of_ad") String type_of_ad,@Param("from") Date from,@Param("to") Date to);
	
	@Query(value = "SELECT * FROM test_data5_no_image WHERE state LIKE :state AND city = :city AND type_of_property = :type_of_property  AND type_of_ad = :type_of_ad AND ad_removed >= :from AND ad_published <= :to", nativeQuery = true)
	List<AdvertiseWebNekretnine> getAllAdByAllParamsForMapNoAreas(@Param("state") String state,@Param("city") String city,@Param("type_of_property") String type_of_property, @Param("type_of_ad") String type_of_ad,@Param("from") Date from,@Param("to") Date to);
	*/						
	@Query(value = "SELECT count(*) FROM test_data5_no_image WHERE ad_removed >= :from AND ad_published <= :to", nativeQuery = true)
	int countOfCityByParams(@Param("from") Date from,@Param("to") Date to);
	
	@Query(value = "SELECT * FROM test_data5_no_image WHERE id_ad=?1", nativeQuery = true)
	AdvertiseWebNekretnine getAdById(int id);
	
	@Query(value = "SELECT id_ad, price_per_m, price, areas FROM test_data5_no_image WHERE type_of_property = ?1 AND ad_removed >= ?2 AND ad_published <= ?3", nativeQuery = true)
	List<Object> getAdsForGraphicMedian(String type_of_property, Date ad_published_from, Date ad_published_to);
	
	@Modifying
	@Query(value = "SELECT id_ad, price_per_m, price, ad_published FROM test_data5_no_image WHERE type_of_property = ?1 AND type_of_ad = ?2 AND ad_published > '2018-12-12' ORDER BY ad_published", nativeQuery = true)
	List<Object> getAllAdsByTypeOfPropert(String type_of_property, String type_of_ad);

	@Query(value="SELECT id_ad, lat, lon FROM test_data5_no_image WHERE city =?1", nativeQuery = true)
	List<Object[]> getLatLonAdsByCity(String city);
	
	//TEST
	@Query(value = "SELECT id_ad, price_per_m, areas,type_of_ad, type_of_property, lat,lon FROM test_data5_no_image WHERE  state LIKE :state AND city LIKE :city AND type_of_property = :type_of_property AND type_of_ad = :type_of_ad AND areas BETWEEN :areasmin AND :areasmax AND ad_removed >= :from AND ad_published <= :to", nativeQuery = true)
	List<Object> getAllAdByAllParamsForMap(@Param("state") String state, @Param("city") String city,@Param("areasmin") int areasmin, @Param("areasmax") int areasmax, @Param("type_of_property") String type_of_property, @Param("type_of_ad") String type_of_ad,@Param("from") Date from,@Param("to") Date to);
	
	@Query(value = "SELECT id_ad, price_per_m, areas, type_of_ad, type_of_property, lat,lon FROM test_data5_no_image WHERE state LIKE :state AND city = :city AND type_of_property = :type_of_property  AND type_of_ad = :type_of_ad AND ad_removed >= :from AND ad_published <= :to", nativeQuery = true)
	List<Object> getAllAdByAllParamsForMapNoAreas(@Param("state") String state,@Param("city") String city,@Param("type_of_property") String type_of_property, @Param("type_of_ad") String type_of_ad,@Param("from") Date from,@Param("to") Date to);
	
	@Query(value = "SELECT t.id_ad, t.url,t.full_address, t.price_per_m, t.ad_published, t.price, t.areas, t.type_of_ad, t.type_of_property, t.lat, t.lon, t.active FROM test_data5_no_image t WHERE id_ad IN ?1", nativeQuery = true)
	List<Object> getAllIdsForTable(List<Long> ids);
	
	@Modifying
	@Query(value = "SELECT count(*) from test_data5_no_image WHERE url = ?1", nativeQuery = true)
	int findAdByUrl(String url);
	
}

