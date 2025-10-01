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
import jsp.spring_boot.entity.Product;
import jsp.spring_boot.entity.Supplier;
import jsp.spring_boot.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	private SupplierService service;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Supplier>> saveSupplier(@RequestBody Supplier supplier){
		return service.saveSupplier(supplier);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Supplier>> getSupplierById(@PathVariable Integer id){
		return service.getSupplierById(id);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Supplier>>> getAllSupplier(){
		return service.getAllSupplier();
	}
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Supplier>> updateSupplier(@RequestBody Supplier supplier){
		return service.updateSupplier(supplier);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteSupplier(@PathVariable Integer id){
		return service.deleteSupplier(id);
	} 
	
	@GetMapping("/byProduct")
	public ResponseEntity<ResponseStructure<Supplier>> getSupplierByProduct(@RequestBody Product product){
		return service.getSupplierByProduct(product);
	}
	
	@GetMapping("/pagination/{pageNumber}/{pageSize}/{name}")
	public ResponseEntity<ResponseStructure<Page<Supplier>>> getSupplierByPaginationAndSorting(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String name){
		return service.getSupplierByPaginationAndSorting(pageNumber, pageSize, name);
	}

	
}
