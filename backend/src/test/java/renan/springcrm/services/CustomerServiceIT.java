package renan.springcrm.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import renan.springcrm.dtos.CustomerDTO;
import renan.springcrm.repositories.CustomerRepository;
import renan.springcrm.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class CustomerServiceIT {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	private Long existingId;
	private Long nonExistingId;
	private Long countTotalCustomers;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalCustomers = 23L;
	}

	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {

		PageRequest pageRequest = PageRequest.of(0, 10);

		Page<CustomerDTO> result = customerService.findAllPaged(pageRequest, "");

		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalCustomers, result.getTotalElements());
	}

	@Test
	public void deleteShouldDeleteWhenIdExists() {
		customerService.delete(existingId);
		Assertions.assertEquals(countTotalCustomers - 1, customerRepository.count());
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			customerService.delete(nonExistingId);
		});
	}
}
