package jsp.spring_boot.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jsp.spring_boot.dao.OrdersDao;
import jsp.spring_boot.dto.ResponseStructure;
import jsp.spring_boot.entity.Customer;
import jsp.spring_boot.entity.Orders;
import jsp.spring_boot.entity.Product;
import jsp.spring_boot.exception.IdNotFoundException;
import jsp.spring_boot.exception.NoRecordAvailableException;
import jsp.spring_boot.repository.CustomerRepository;
import jsp.spring_boot.repository.ProductRepository;

@Service
public class OrderService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrdersDao ordersDao;
	
	public ResponseEntity<ResponseStructure<Orders>> placeOrder(Orders order){
		ResponseStructure<Orders> response = new ResponseStructure<Orders>();
		Optional<Customer> customeropt = customerRepository.findById(order.getCustomer().getId());
		
		if(!customeropt.isPresent()) {
			throw new IdNotFoundException("Customer Id is invalid");
		}
		Customer customer = customeropt.get();
		List<Integer> productIds = new ArrayList<Integer>();
		for (Product p : order.getProducts()) {
			Optional<Product> productopt= productRepository.findById(p.getId());
			if(!productopt.isPresent())
				throw new IdNotFoundException("product id not valid ");
			else
			productIds.add(p.getId());
			
			
		}
		
//		List<Integer> productIds = order.getProducts()
//						                .stream()
//						                .map(Product::getId)
//						                .toList();
		
		List<Product> products = productRepository.findAllById(productIds);
		if(products.isEmpty()) {
			throw new NoRecordAvailableException("Products not available........!");
		}
		
//		Orders newOrder = new Orders();
		order.setCustomer(customer);
		order.setProducts(products);
		order.setOrderDate(LocalDate.now());
		order.setStatus(order.getStatus());
		
		//Total Amount
		double totalAmount = 0;
		for (Product p : products) {
			totalAmount+= p.getPrice();
		}
		
		order.setTotalAmount(totalAmount);
		
		String trackingNumber = "ORD-"+String.valueOf(100000 + new Random().nextInt(900000));
		order.setTrackingNumber(trackingNumber);
		
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Order Placed successfully");
		response.setData(ordersDao.placeOrders(order));
		
		return new ResponseEntity<ResponseStructure<Orders>>(response,HttpStatus.CREATED);
		
	}
	
	public ResponseEntity<ResponseStructure<Orders>> getOrderById(Integer id){
		ResponseStructure<Orders> response = new ResponseStructure<Orders>();

		Optional<Orders> opt = ordersDao.getOrderById(id);
		if(!opt.isPresent()) {
			throw new IdNotFoundException("Order Id invalid......!");
		} 
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Record fetched successfully");
		response.setData(opt.get());
		
		return new ResponseEntity<ResponseStructure<Orders>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrders(){
		ResponseStructure<List<Orders>> response = new ResponseStructure<>();
		List<Orders> orders = ordersDao.getAllOrders();
		
		if(orders.isEmpty())
			throw new NoRecordAvailableException("No Orders Found...!");
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Records fetched Successfully");
		response.setData(orders);
		
		return new ResponseEntity<ResponseStructure<List<Orders>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Orders>> updateOrder(Orders order){
		ResponseStructure<Orders> response = new ResponseStructure<Orders>();
		
		Optional<Orders> existingOrder = ordersDao.getOrderById(order.getId());
		
		if(!existingOrder.isPresent()) {
			throw new IdNotFoundException("No order found since id is invalid....!");
		}
			Orders newOrder = existingOrder.get();
//			newOrder.setTrackingNumber(order.getTrackingNumber());
			newOrder.setOrderDate(order.getOrderDate());
			newOrder.setStatus(order.getStatus());
			
			
			Optional<Customer> customer = customerRepository.findById(order.getCustomer().getId());
			newOrder.setCustomer(customer.get());
			
//			List<Integer> productIds = order.getProducts()
//							                .stream()
//							                .map(Product::getId)
//							                .toList();

			List<Integer> productIds = new ArrayList<Integer>();
			for (Product p : order.getProducts()) {
				productIds.add(p.getId());
			}
			List<Product> products = productRepository.findAllById(productIds);					
			newOrder.setProducts(products);
//			newOrder.setTotalAmount(products.stream()
//							                .mapToDouble(Product::getPrice)
//							                .sum());

			double totalAmount = 0;
			for (Product p : products) {
				totalAmount+= p.getPrice();
			}
			
			ordersDao.placeOrders(newOrder);
			
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Record Updated successfully");
			response.setData(newOrder);
			
			return new ResponseEntity<ResponseStructure<Orders>>(response,HttpStatus.CREATED);
	
	}
	
	public ResponseEntity<ResponseStructure<String>> deleteOrder(Integer id){
		ResponseStructure<String> response = new ResponseStructure<String>();
		Optional<Orders> opt = ordersDao.getOrderById(id);
		
		if(!opt.isPresent())
			throw new IdNotFoundException("Canot delete the order since id is invalid...!");
		
		ordersDao.deleteOrder(id);
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Record Deleted Successfully");
		response.setData("Order with id: "+id+" has been deleted");
		
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Orders>> findOrderByTrackingNumber(String trackingNumber){
		ResponseStructure<Orders> response = new ResponseStructure<>();
		Optional<Orders> opt = ordersDao.findOrderByTrackingNumber(trackingNumber);
		
		if(!opt.isPresent())
			throw new NoRecordAvailableException("No Order found for the tracking number....!");
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Record fetched successfully");
		response.setData(opt.get());
		
		return new ResponseEntity<ResponseStructure<Orders>>(response,HttpStatus.OK);

	}
	
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersWithAmountGreaterThen(Double amount){
		ResponseStructure<List<Orders>> response = new ResponseStructure<>();
		List<Orders> orders = ordersDao.getOrdersWithAmountGreaterThen(amount);
		if(orders.isEmpty())
			throw new NoRecordAvailableException("No Orders Found...!");
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Record fetched successfully");
		response.setData(orders);
		
		return new ResponseEntity<ResponseStructure<List<Orders>>>(response,HttpStatus.OK);	
	}
	
	
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrderByStatus(String status){
		ResponseStructure<List<Orders>> response = new ResponseStructure<>();
		List<Orders> orders = ordersDao.getOrderByStatus(status);
		
		if(orders.isEmpty())
			throw new NoRecordAvailableException("No Orders Found...!");
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Record fetched successfully");
		response.setData(orders);
		
		return new ResponseEntity<ResponseStructure<List<Orders>>>(response,HttpStatus.OK);	

	}
	
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrderByCustomer(Integer customerId){
		ResponseStructure<List<Orders>> response = new ResponseStructure<>();
		
		List<Orders> orders = ordersDao.getOrderByCustomer(customerId);
		
		if(orders.isEmpty()) {
			throw new IdNotFoundException("No Orders found for the Customer id: ");
		}
		
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record fetched successfully");
			response.setData(orders);
			return new ResponseEntity<ResponseStructure<List<Orders>>>(response,HttpStatus.OK);
			
	}
	
	public ResponseEntity<ResponseStructure<Page<Orders>>> getOrdersByPaginationAndSorting(int pageNumber, int pageSize, String name){
		ResponseStructure<Page<Orders>> response = new ResponseStructure<>();
		Page<Orders> order = ordersDao.getOrdersByPaginationAndSorting(pageNumber, pageSize, name);
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Products fetched successfully with pagination and sorting");
		response.setData(order);
		
		return new ResponseEntity<ResponseStructure<Page<Orders>>>(response,HttpStatus.OK);
	}
 
}
