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
import jsp.spring_boot.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Product>> addProduct(@RequestBody Product product){
		return productService.addProduct(product);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseStructure<Product>> getProductById(@PathVariable Integer id){
		return productService.getProductById(id);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts(){
		return productService.getAllProducts();
	}
	
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Product>> updateProduct(@RequestBody Product product){
		return productService.updateProduct(product);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteProduct(@PathVariable Integer id){
		return productService.deleteProduct(id);
	}
	
	@GetMapping("/bySupplier/{id}")
	public ResponseEntity<ResponseStructure<List<Product>>> getProductsBySupplier(@PathVariable Integer id){
		return productService.getProductsBySupplier(id);
	}
	
	@GetMapping("/stackQuantity/{quantity}")
	public ResponseEntity<ResponseStructure<List<Product>>> getProductsByStackQuantity(@PathVariable Integer quantity){
		return productService.getProductsByStackQuantity(quantity);
	}
	
	@GetMapping("pagination/{pageNumber}/{pageSize}/{name}")
	public ResponseEntity<ResponseStructure<Page<Product>>> getProductByPaginationAndSorting(@PathVariable int pageNumber,@PathVariable int pageSize,@PathVariable String name){
		return productService.getProductByPaginationAndSorting(pageNumber, pageSize, name);
	}

}
