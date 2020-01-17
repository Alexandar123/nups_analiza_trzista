package com.analyze.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.analyze.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query(value = "SELECT * FROM users WHERE id=?1", nativeQuery = true)
	User getUserById(Long id);
	
	@Query(value = "SELECT points FROM users WHERE id=?1", nativeQuery = true)
	Long getUserPoints(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET points = points + ?1 WHERE id = ?2", nativeQuery = true )
	int increasePoints(int points, Long id);

	@Transactional
	@Modifying
	@Query(value = "UPDATE users SET points = points - ?1 WHERE id = ?2", nativeQuery = true)
	int decreasePoints(int points, Long id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM users WHERE id=?1 ", nativeQuery = true)
	void delete(Long id);
}
