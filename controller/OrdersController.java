package jsp.spring_boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.spring_boot.dto.ResponseStructure;
import jsp.spring_boot.entity.Orders;
import jsp.spring_boot.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Orders>> placeOrder(@RequestBody Orders order){
		return orderService.placeOrder(order);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Orders>> getOrdersById(@PathVariable Integer id) {
		return orderService.getOrderById(id);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllOrders() {
		return orderService.getAllOrders();
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Orders>> updateOrder(@RequestBody Orders order){
		return orderService.updateOrder(order);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteOrder(@PathVariable Integer id){
		return orderService.deleteOrder(id);
	}
	
	@GetMapping("/trackingNumber/{trackingNumber}")
	public ResponseEntity<ResponseStructure<Orders>> getOrderByTrakcingNumber(@PathVariable String trackingNumber){
		return orderService.findOrderByTrackingNumber(trackingNumber);
	}
	
	@GetMapping("/amount/{amount}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByAmountGreaterThan(@PathVariable Double amount){
		return orderService.getOrdersWithAmountGreaterThen(amount);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByStatus(@PathVariable String status){
		return orderService.getOrderByStatus(status);
	}
	
	@GetMapping("/byCustomerId/{customerId}")
	public ResponseEntity<ResponseStructure<List<Orders>>> getOrdersByCustomer(@PathVariable Integer customerId){
		return orderService.getOrderByCustomer(customerId);
	}
	
	@GetMapping("pagination/{pageNumber}/{pageSize}/{name}")
	public ResponseEntity<ResponseStructure<Page<Orders>>> getOrdersByPaginationAndSorting(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String name){
		return orderService.getOrdersByPaginationAndSorting(pageNumber, pageSize, name);
	}
	
	
	
	
}
