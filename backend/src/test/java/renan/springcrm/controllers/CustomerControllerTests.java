package renan.springcrm.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import renan.springcrm.dtos.CustomerDTO;
import renan.springcrm.services.CustomerService;
import renan.springcrm.services.exceptions.ResourceNotFoundException;
import renan.springcrm.testsHelpers.Factory;
import renan.springcrm.testsHelpers.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private ObjectMapper objectMapper;

	private String usernameUserAdminAndOperator;
	private String usernameUserAdmin;
	private String password;

	private Long existingId;
	private Long nonExistingId;
	private CustomerDTO customerDTO;
	private PageImpl<CustomerDTO> page;

	@BeforeEach
	void setUp() throws Exception {

		usernameUserAdminAndOperator = "joe_admin_operator@systemcrm.com.br";
		usernameUserAdmin = "stephanie_admin@systemcrm.com.br";
		password = "123456";

		existingId = 1L;
		nonExistingId = 2L;
		customerDTO = Factory.createCustomerDTO();

		when(customerService.findAllPaged(any(), any())).thenReturn(page);
		doNothing().when(customerService).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(customerService).delete(nonExistingId);
		when(customerService.insert(any())).thenReturn(customerDTO);
		when(customerService.findById(existingId)).thenReturn(customerDTO);
		when(customerService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		when(customerService.update(eq(existingId), any())).thenReturn(customerDTO);
		when(customerService.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		doNothing().when(customerService).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(customerService).delete(nonExistingId);
	}

	@Test
	public void findAllPagedShouldReturnStatus401UnauthorizedWhenUserNonLogged() throws Exception {
		ResultActions result = mockMvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	@Test
	public void findAllShouldReturnPageWhenUserOperatorLogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		ResultActions result = mockMvc.perform(get("/customers").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findAllShouldReturnAccessDeniedWithStatus403WhenUserLoggedNonOperator() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdmin, password);

		ResultActions result = mockMvc.perform(get("/customers").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(403));
	}

	@Test
	public void deleteShouldReturnNoContentWhenIdExistsAndUseLoggedOperator() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		ResultActions result = mockMvc.perform(delete("/customers/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExistAndUserLoggedOperator() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		ResultActions result = mockMvc.perform(delete("/customers/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void deleteShouldReturnAccessDeniedStatus403WhenUserLoggedNonOperator() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdmin, password);

		ResultActions result = mockMvc.perform(delete("/customers/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(403));
	}

	@Test
	public void insertShouldReturnCustomerDTOCreatedWhenUserOperatorlogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		String jsonBody = objectMapper.writeValueAsString(customerDTO);

		ResultActions result = mockMvc.perform(post("/customers").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.firstName").exists());
		result.andExpect(jsonPath("$.lastName").exists());
		result.andExpect(jsonPath("$.cpf").exists());
	}

	@Test
	public void insertShouldReturnStatus403WhenUserNonOperatorlogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdmin, password);

		String jsonBody = objectMapper.writeValueAsString(customerDTO);

		ResultActions result = mockMvc.perform(post("/customers").header("Authorization", "Bearer " + accessToken)
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(403));
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExistsAndUserOperatorLogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		CustomerDTO productDTO = Factory.createCustomerDTO();
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		String expectedFirstName = productDTO.getFirstName();
		String expectedLastName = productDTO.getLastName();
		String expectedCPf = productDTO.getCpf();

		ResultActions result = mockMvc
				.perform(put("/customers/{id}", existingId).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.firstName").value(expectedFirstName));
		result.andExpect(jsonPath("$.lastName").value(expectedLastName));
		result.andExpect(jsonPath("$.cpf").value(expectedCPf));
	}

	@Test
	public void updateShouldReturnStatus403WhenUserNonOperatorLogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdmin, password);

		CustomerDTO productDTO = Factory.createCustomerDTO();
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions result = mockMvc
				.perform(put("/customers/{id}", existingId).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(403));
	}

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExistAndUserOperatorLogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		CustomerDTO productDTO = Factory.createCustomerDTO();
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions result = mockMvc
				.perform(put("/customers/{id}", nonExistingId).header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void findByIdShouldReturnStatus200OkWhenIdExistsAndUserOperatorLogged() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		ResultActions result = mockMvc
				.perform(get("/customers/{id}", existingId).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
	}

	@Test
	public void findByIdShouldReturnStatus404NotFoundWhenIdNonExistsAndUserOperatorLogged() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdminAndOperator, password);

		ResultActions result = mockMvc
				.perform(get("/customers/{id}", nonExistingId).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void findByIdShouldReturnStatus403WhenIdExistsAndUserNonOperatorLogged() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, usernameUserAdmin, password);

		ResultActions result = mockMvc
				.perform(get("/customers/{id}", existingId).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().is(403));
	}
}
