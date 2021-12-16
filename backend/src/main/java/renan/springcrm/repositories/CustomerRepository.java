package renan.springcrm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import renan.springcrm.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	boolean existsByCpf(String cpf);

	@Query("SELECT c FROM Customer c WHERE (LOWER(c.firstName) LIKE LOWER(CONCAT('%',:search,'%')))"
			+ " OR (LOWER(c.lastName) LIKE LOWER(CONCAT('%',:search,'%'))) "
			+ " OR (LOWER(c.cpf) LIKE LOWER(CONCAT('%',:search,'%'))) "
			+ " OR (LOWER(c.email) LIKE LOWER(CONCAT('%',:search,'%')))")
	Page<Customer> searchCustomer(Pageable pageable, String search);
}
