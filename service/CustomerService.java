package jsp.spring_boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jsp.spring_boot.dao.CustomerDao;
import jsp.spring_boot.dto.ResponseStructure;
import jsp.spring_boot.entity.Customer;
import jsp.spring_boot.exception.IdNotFoundException;
import jsp.spring_boot.exception.NoRecordAvailableException;

@Service
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	public ResponseEntity<ResponseStructure<Customer>> addCustomer(Customer customer){
		ResponseStructure<Customer> response = new ResponseStructure<Customer>();
		
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Customer Added Successfully");
		response.setData(customerDao.addCustomer(customer));
		
		return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<Customer>> getCustomerById( Integer id){
		ResponseStructure<Customer> response = new ResponseStructure<Customer>();
		Optional<Customer> opt = customerDao.getCustomerById(id);
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record Fetched successfully");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.OK);
		}
		else
			throw new IdNotFoundException("No Record Found since id is invalid...!!");		
	}
	
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomer(){
		ResponseStructure<List<Customer>> response = new ResponseStructure<List<Customer>>();
		List<Customer> customers = customerDao.getAllCustomer();
		if(customers.isEmpty()) {
			throw new NoRecordAvailableException("No Customers Available");
		}
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Records Fetched Successfully......!");
		response.setData(customers);
		return new ResponseEntity<ResponseStructure<List<Customer>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer( Customer customer){
		ResponseStructure<Customer> response = new ResponseStructure<Customer>();
		
		Optional<Customer> existingCustomer = customerDao.getCustomerById(customer.getId());
		
		if(existingCustomer.isPresent()) {
			Customer newCustomer = existingCustomer.get();
			newCustomer.setName(customer.getName());
			newCustomer.setPhone(customer.getPhone());
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setAddress(customer.getAddress());
			
			customerDao.addCustomer(newCustomer);
			
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Record Updated successfully");
			response.setData(newCustomer);
			return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.CREATED);
		}
		else
			throw new IdNotFoundException("Couldn't update the record since id is invalid");
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteCustomer( Integer id){
		ResponseStructure<String> response = new ResponseStructure<>();
		Optional<Customer> opt = customerDao.getCustomerById(id);
		if(opt.isPresent()) {
			customerDao.deleteCustomer(id);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record Deleted successfully");
			response.setData("deleted id: " +id);
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
			throw new NoRecordAvailableException("No record Found since id is invalid....!");
	}
	
	
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByPhoneNumber(Long phone){
		ResponseStructure<Customer> response = new ResponseStructure<Customer>();
		Optional<Customer> opt = customerDao.getCustomerByPhone(phone);
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Record Updated successfully");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.CREATED);
		}
		else
			throw new NoRecordAvailableException("Couldn't fetch the record since phone number is invalid");
	}
	
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByOrder(Integer id){
		ResponseStructure<Customer> response = new ResponseStructure<Customer>();
		Optional<Customer> opt = customerDao.getCustomerByOrder(id);
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record Fetched successfully");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.OK);
		}
		else
			throw new IdNotFoundException("No Customer Found.. Order id may be invalid..!!");	
	}
	
	
	public ResponseEntity<ResponseStructure<Page<Customer>>> getProductByPaginationAndSorting( int pageNumber, int pageSize, String name){
		ResponseStructure<Page<Customer>> response = new ResponseStructure<>();
		Page<Customer> product = customerDao.getCustomerByPaginationAndSorting(pageNumber, pageSize, name);
		
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Customers fetched successfully with pagination and sorting");
		response.setData(product);
		
		return new ResponseEntity<ResponseStructure<Page<Customer>>>(response,HttpStatus.OK);

	}	
	
	
}
