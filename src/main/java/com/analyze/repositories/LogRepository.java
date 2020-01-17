package com.analyze.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.analyze.model.Log;

@Repository
public interface LogRepository extends CrudRepository<Log, Integer> {

	@Query(value = "SELECT * FROM logs", nativeQuery = true)
	List<Log> getAllLogs();
	
	@Query(value = "SELECT * FROM logs WHERE user_id=?1", nativeQuery = true)
	List<Log> getAllLogsByUser(int user_id);
}
