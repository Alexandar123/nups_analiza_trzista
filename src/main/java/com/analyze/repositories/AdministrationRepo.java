package com.analyze.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.analyze.model.Administration;
import com.analyze.model.AdvertiseWebNekretnine;

public interface AdministrationRepo extends CrudRepository<Administration, Long>  {

	@Query(value="SELECT id_ad, name, url, price, areas, ad_published, ad_removed, title, city, state, street, price_per_m, type_of_ad, type_of_property, active FROM test_data5_no_image WHERE date_of_inserting >= :from AND date_of_inserting <= :to ", nativeQuery = true)
	public List<Object> showData(@Param("from") Date from,@Param("to") Date to);
	
	@Query(value="DELETE from test_data5_no_image WHERE price < 1 and areas < 1 and date_of_inserting > '2020-05-05'", nativeQuery = true)
	public void clearData();
}
