package jsp.spring_boot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.spring_boot.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByStackQuantity(int qty);

	@Query("select p from Product p where p.supplier.id=?1")
	List<Product> getProductsBySupplier(int id);

}
