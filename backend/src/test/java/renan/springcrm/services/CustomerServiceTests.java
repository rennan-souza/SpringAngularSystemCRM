package renan.springcrm.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import renan.springcrm.dtos.CustomerDTO;
import renan.springcrm.entities.Customer;
import renan.springcrm.repositories.CustomerRepository;
import renan.springcrm.services.exceptions.ResourceNotFoundException;
import renan.springcrm.testsHelpers.Factory;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTests {

	@InjectMocks
	private CustomerService service;

	@Mock
	private CustomerRepository repository;

	private long existingId;
	private long nonExistingId;
	private Customer customer;
	private PageImpl<Customer> page;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 2222L;
		customer = Factory.createCustomer();
		page = new PageImpl<>(List.of(customer));

		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {
		Pageable pageable = PageRequest.of(0, 5);
		Page<CustomerDTO> result = service.findAllPaged(pageable, any());
		Assertions.assertNotNull(result);
		Assertions.assertTrue(result.getTotalPages() == result.getTotalElements());

		Mockito.verify(repository).findAll(pageable);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		Mockito.verify(repository, times(1)).deleteById(nonExistingId);
	}
}
