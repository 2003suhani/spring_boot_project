package jsp.spring_boot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jsp.spring_boot.entity.Customer;
import jsp.spring_boot.repository.CustomerRepository;

@Repository
public class CustomerDao {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Optional<Customer> getCustomerById(int id){
		return customerRepository.findById(id);
	}
	
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();
	}
	
	public Optional<Customer> updateCustomer(Customer customer){
		return customerRepository.findById(customer.getId());
	}
	
	public void deleteCustomer(int id) {
		customerRepository.deleteById(id);
	}
	
	public Optional<Customer> getCustomerByPhone(long phone) {
		return customerRepository.getCustomerByPhoneNumber(phone);
	}
	
	public Optional<Customer> getCustomerByOrder(int id){
		return customerRepository.getCustomerByOrder(id);
	}
	
	public Page<Customer> getCustomerByPaginationAndSorting(int pagenumber,int pagesize,String name)
	{
		return customerRepository.findAll(PageRequest.of(pagenumber, pagesize,Sort.by(name).ascending()));
	}
	
}
