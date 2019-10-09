package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);

        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Jane");
        customer1.setLastName("Doe");
//        customer1.setCustomerUrl(Customer.ROOT_URL + customer1.getId());

        customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("John");
        customer2.setLastName("Doe");
//        customer2.setCustomerUrl(Customer.ROOT_URL + customer2.getId());
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customerList = Arrays.asList(customer1, customer1);

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
        assertEquals(2, customerDTOs.size());
    }

    @Test
    public void getCustomerById() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));

        CustomerDTO customerDTO = customerService.getCustomerById(customer1.getId());
//        assertEquals(customer1.getId(), customerDTO.getId());
        assertEquals(customer1.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer1.getLastName(), customerDTO.getLastName());
//        assertEquals(customer1.getCustomerUrl(), customerDTO.getCustomerUrl());
        assertEquals(Customer.ROOT_URL + customer1.getId(), customerDTO.getCustomerUrl());
    }
}