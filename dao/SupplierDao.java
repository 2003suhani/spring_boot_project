package jsp.spring_boot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jsp.spring_boot.entity.Supplier;
import jsp.spring_boot.repository.SupplierRepository;

@Repository
public class SupplierDao {
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	public Supplier saveSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}
	
	public Optional<Supplier> getSupplierById(Integer id){
		return supplierRepository.findById(id);
	}
	
	public List<Supplier> getAllSupplier(){
		return supplierRepository.findAll();
	}
	
	public Optional<Supplier> updateSupplier(Supplier supplier){
		return supplierRepository.findById(supplier.getId());
	}
	
	public void deleteSupplier(int id){
		supplierRepository.deleteById(id);
	}
	
	public Optional<Supplier> getSupplierByProduct(int id) {
		return supplierRepository.findById(id);
	}
	
	public Page<Supplier> getSupplierByPaginationAndSorting(int pagenumber,int pagesize,String name)
	{
		return supplierRepository.findAll(PageRequest.of(pagenumber, pagesize,Sort.by(name).ascending()));
	}

}
