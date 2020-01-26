package com.analyze.configuration;


import java.sql.Types;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.analyze.model.AdvertiseWebNekretnine;
import com.analyze.repositories.DatabaseMethodRepo;

public class InsertRecordInDatabaseWithJdbcTemplate implements DatabaseMethodRepo{

	private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost/trzistenekretninedb?useUnicode=yes&characterEncoding=UTF-8";
	private static final String dbUsername = "root";
	private static final String dbPassword = "";

	private static final String selectSql = "SELECT * FROM ";
	private static final String insertSql =

			"INSERT INTO test_data5 (" +

					" name, " +

					" url, " +

					" price, " +
					" areas, " +
					" ad_published, " +
					" title, " +
					
					" description, " +
					" address, " +
					" full_address, " +
					" floor, " +
					" num_of_rooms, " +
					" city, " +
					" state, " +
					" street, " +
					" price_per_m, " +
					" image1, " +
					" image2, " +
					" type_of_ad, " +
					" type_of_property, " +
					" building_year) " +

					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	

	private static DataSource dataSource;
	@SuppressWarnings("unchecked")
	public static List<AdvertiseWebNekretnine> getData(String table) {
		dataSource = getDataSource();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		// define query arguments
		String sql = "SELECT * FROM " + table;
		System.out.println(sql);
		return (List<AdvertiseWebNekretnine>) template.query(sql, (rs, rowNum) -> new AdvertiseWebNekretnine(
				rs.getLong("id_ad"),
				rs.getString("name"),
				rs.getString("url"),
				rs.getLong("price"),
				rs.getInt("areas"),
				rs.getDate("ad_published"),
				rs.getString("title"),
				rs.getString("description"),
				rs.getString("address"),
				rs.getString("full_address"),
				rs.getString("floor"),
				rs.getFloat("num_of_rooms"),
				rs.getString("city"),
				rs.getString("state"),
				rs.getString("street"),
				rs.getFloat("price_per_m"),
				rs.getString("type_of_ad"),
				rs.getString("type_of_property"),
				rs.getInt("building_year")
				));

	}
	public static void updateData(Long id, String table, String column, byte[] bImg) {
		dataSource = getDataSource();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		// define query arguments
		String sql = "UPDATE " + table + " set " + column + " = ? WHERE id_ad = ?"; 
		
		Object[] params = { bImg, id};
		   
        // define SQL types of the arguments
        int[] types = {Types.BLOB, Types.BIGINT};
        int rows = template.update(sql, params, types);
        System.out.println(rows + " row(s) updated.");

	}
	public static void saveRecord(AdvertiseWebNekretnine adv) {
		dataSource = getDataSource();
		JdbcTemplate template = new JdbcTemplate(dataSource);

		// define query arguments
		Object[] params = new Object[] { adv.getName(), adv.getUrl(), adv.getPrice(), adv.getAreas(), adv.getAd_published(), adv.getTitle(), adv.getDescription(),
				adv.getAddress(), adv.getFull_address(), adv.getFloor(), adv.getNum_of_rooms(), adv.getCity(), adv.getState(),
				adv.getStreet(), adv.getPrice_per_m(), adv.getImage1(), adv.getImage2(), adv.getType_of_ad(), adv.getDate_of_inserting(), adv.getType_of_property(), adv.getBuilding_yer()
				,adv.getScreenshot()};

		// define SQL types of the arguments
		int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.DATE, Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.DECIMAL, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.DECIMAL, Types.BLOB, Types.BLOB, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER, Types.BLOB };

		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = template.update(insertSql, params, types);
		System.out.println(row + " row inserted.");

	}

	public static DriverManagerDataSource getDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(driverClassName);

		dataSource.setUrl(url);

		dataSource.setUsername(dbUsername);

		dataSource.setPassword(dbPassword);

		return dataSource;
	}

	@Override
	public <S extends AdvertiseWebNekretnine> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends AdvertiseWebNekretnine> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AdvertiseWebNekretnine> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<AdvertiseWebNekretnine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<AdvertiseWebNekretnine> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(AdvertiseWebNekretnine entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends AdvertiseWebNekretnine> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	

}
