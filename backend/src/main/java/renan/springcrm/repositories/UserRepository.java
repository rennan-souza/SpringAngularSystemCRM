package renan.springcrm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import renan.springcrm.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE (LOWER(u.firstName) LIKE LOWER(CONCAT('%',:search,'%')))"
			+ " OR (LOWER(u.lastName) LIKE LOWER(CONCAT('%',:search,'%'))) "
			+ " OR (LOWER(u.email) LIKE LOWER(CONCAT('%',:search,'%')))")
	Page<User> searchUser(Pageable pageable, String search);
}
