package renan.springcrm.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import renan.springcrm.dtos.CustomerDTO;
import renan.springcrm.entities.Customer;
import renan.springcrm.repositories.CustomerRepository;
import renan.springcrm.services.exceptions.DatabaseException;
import renan.springcrm.services.exceptions.ResourceNotFoundException;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Transactional(readOnly = true)
	public Page<CustomerDTO> findAllPaged(Pageable pageable, String search) {

		if (search == null) {
			Page<Customer> list = customerRepository.findAll(pageable);
			return list.map(x -> new CustomerDTO(x));
		}

		Page<Customer> list = customerRepository.searchCustomer(pageable, search);
		return list.map(x -> new CustomerDTO(x));
	}

	@Transactional(readOnly = true)
	public CustomerDTO findById(Long id) {
		Optional<Customer> obj = customerRepository.findById(id);
		Customer entity = obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
		return new CustomerDTO(entity);
	}

	@Transactional(readOnly = false)
	public CustomerDTO insert(CustomerDTO dto) {

		boolean existsCpf = customerRepository.existsByCpf(dto.getCpf());

		if (existsCpf) {
			throw new DatabaseException("CPF Já cadastrado");
		}

		Customer entity = new Customer();
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setCpf(dto.getCpf());
		entity.setBirthDate(dto.getBirthDate());
		entity.setEmail(dto.getEmail());
		entity = customerRepository.save(entity);
		return new CustomerDTO(entity);
	}

	@Transactional(readOnly = false)
	public CustomerDTO update(Long id, CustomerDTO dto) {
		try {
			Customer entity = customerRepository.getOne(id);

			if (!entity.getCpf().equals(dto.getCpf())) {
				boolean existsCpf = customerRepository.existsByCpf(dto.getCpf());

				if (existsCpf) {
					throw new DatabaseException("CPF já cadastrado");
				}
			}

			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity.setCpf(dto.getCpf());
			entity.setBirthDate(dto.getBirthDate());
			entity.setEmail(dto.getEmail());
			entity = customerRepository.save(entity);
			return new CustomerDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		try {
			customerRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
