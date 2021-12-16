package renan.springcrm.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import renan.springcrm.entities.Customer;
import renan.springcrm.testsHelpers.Factory;

@DataJpaTest
public class CustomerRepositoryTests {

	@Autowired
	private CustomerRepository customerRepository;

	private long existingId;
	private long nonExistingId;
	private long countTotalCustomers;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1259L;
		countTotalCustomers = 23L;
	}

	@Test
	public void shouldReturnCustomers() {
		Assertions.assertFalse(customerRepository.findAll().isEmpty());
		Assertions.assertTrue(customerRepository.findAll().size() > 0);
		Assertions.assertTrue(customerRepository.findAll().size() == countTotalCustomers);
	}

	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		Customer customer = Factory.createCustomer();
		customer.setId(null);

		customer = customerRepository.save(customer);
		Optional<Customer> result = customerRepository.findById(customer.getId());

		Assertions.assertNotNull(customer.getId());
		Assertions.assertEquals(countTotalCustomers + 1L, customer.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertEquals(customer.getId(), 24L);
	}

	@Test
	public void shouldDeleteWhenIdExists() {
		customerRepository.deleteById(existingId);
		Optional<Customer> result = customerRepository.findById(existingId);
		Assertions.assertFalse(result.isPresent());
	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			customerRepository.deleteById(nonExistingId);
		});
	}
}
