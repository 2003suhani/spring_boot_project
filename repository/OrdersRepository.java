package jsp.spring_boot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.spring_boot.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	Optional<Orders> findByTrackingNumber(String trackingNumber);
	
	// get orders with amount greater than the given value
//	@Query("select o from Orders o where o.amount>?1 ")
	List<Orders> findByTotalAmountGreaterThan(double amount);
	
	@Query("select o from Orders o where o.status=?1")
	List<Orders> getOrdersByStatus(String status);

	@Query("select c.orders from Customer c where c.id=?1")
	List<Orders> getOrdersByCustomer(int id);

//	@Query("select c.orders from Customer c where c.id=?1")
//	List<Orders> getOrdersByCustomer(int id);
}
