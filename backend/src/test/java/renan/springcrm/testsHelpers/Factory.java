package renan.springcrm.testsHelpers;

import java.time.LocalDate;

import renan.springcrm.dtos.CustomerDTO;
import renan.springcrm.entities.Customer;

public class Factory {

	public static Customer createCustomer() {
		Customer customer = new Customer(1L, "Name", "Last Name", "853.454.270-89", LocalDate.parse("1988-02-08"),
				"namelastname@email.com.br");
		return customer;
	}

	public static CustomerDTO createCustomerDTO() {
		Customer customer = createCustomer();
		return new CustomerDTO(customer);
	}

}
