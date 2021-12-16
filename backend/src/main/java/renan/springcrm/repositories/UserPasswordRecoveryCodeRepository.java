package renan.springcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import renan.springcrm.entities.UserPasswordRecoveryCode;

@Repository
public interface UserPasswordRecoveryCodeRepository extends JpaRepository<UserPasswordRecoveryCode, Long> {

	UserPasswordRecoveryCode findByCode(String code);
	
	void deleteByCode(String code);
}
