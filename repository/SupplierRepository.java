package jsp.spring_boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.spring_boot.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

	@Query("select p.supplier from Product p where p.id=?1")
	Optional<Supplier> getSupplierByProduct(int id);
	
}
