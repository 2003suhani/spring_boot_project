package jsp.spring_boot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jsp.spring_boot.entity.Orders;
import jsp.spring_boot.repository.OrdersRepository;

@Repository
public class OrdersDao {

	@Autowired
	private OrdersRepository ordersRepository;
	
	public Orders placeOrders(Orders order) {
		return ordersRepository.save(order);
	}
	
	public Optional<Orders> getOrderById(int id) {
		return ordersRepository.findById(id);
	}
	
	public List<Orders> getAllOrders(){
		return ordersRepository.findAll();
	}
	
	public Optional<Orders> updateOrder(Orders order) {
		return ordersRepository.findById(order.getId());
	}
	
	public void deleteOrder(int id){
		ordersRepository.deleteById(id);
	}
	
	public Optional<Orders> findOrderByTrackingNumber(String num) {
		return ordersRepository.findByTrackingNumber(num);
	}
	
	public List<Orders> getOrdersWithAmountGreaterThen(double amount) {
		return ordersRepository.findByTotalAmountGreaterThan(amount);		
	}
	
	public List<Orders> getOrderByStatus(String status){
		return ordersRepository.getOrdersByStatus(status);
	}
	
	public List<Orders> getOrderByCustomer(int id) {
		return ordersRepository.getOrdersByCustomer(id);
	}

	public Page<Orders> getOrdersByPaginationAndSorting(int pagenumber,int pagesize,String name)
	{
		return ordersRepository.findAll(PageRequest.of(pagenumber, pagesize,Sort.by(name).ascending()));
	}

}
