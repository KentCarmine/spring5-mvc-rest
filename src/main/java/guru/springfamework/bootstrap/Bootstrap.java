package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    @Autowired
    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        setupCategories();
        setupCustomers();
        setupVendors();
    }

    private void setupCategories() {
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

        System.out.println("Category Records Loaded = "+ categoryRepository.count());
    }

    private void setupCustomers() {
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

        System.out.println("Customer Records Loaded = "+ customerRepository.count());
    }

    private void setupVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("Test Vendor 1");

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Test Vendor 2");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        System.out.println("Vendor Records Loaded = "+ vendorRepository.count());
    }


}
