package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerMapperImplTest {
    private static final Long ID = 1L;
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String URL = Customer.ROOT_URL + ID;

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    private Customer customer;

    @Before
    public void setUp() throws Exception {
        customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
    }

    @Test
    public void customerToCustomerDTO() throws Exception {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(customer.getFirstName(), customerDTO.getFirstName());
        assertEquals(customer.getLastName(), customerDTO.getLastName());
//        assertEquals(URL, customerDTO.getCustomerUrl());
    }
}