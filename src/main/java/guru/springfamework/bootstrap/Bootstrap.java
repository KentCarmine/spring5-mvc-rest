package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Records Loaded = "+ categoryRepository.count());

        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("Jane");
        customer1.setLastName("Doe");
//        customer1.setCustomerUrl(Customer.ROOT_URL + customer1.getId());

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("John");
        customer2.setLastName("Doe");
//        customer2.setCustomerUrl(Customer.ROOT_URL + customer2.getId());

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }


}
