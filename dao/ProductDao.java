package jsp.spring_boot.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jsp.spring_boot.entity.Product;
import jsp.spring_boot.repository.ProductRepository;


@Repository
public class ProductDao {
	
	@Autowired
	private ProductRepository productRepository;
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Optional<Product> getProductById(Integer id){
		return productRepository.findById(id);
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Optional<Product> updateProduct(Product product){
		return productRepository.findById(product.getId());
	}
	
	public void deleteProduct(int id){
		productRepository.deleteById(id);
	}
	
	public List<Product> getProductByStackQuantity(int qty) {
		return productRepository.findByStackQuantity(qty);
	}
	
	public List<Product> getProductsBySupplier(int id) {
		return productRepository.getProductsBySupplier(id);
	}
	
	public Page<Product> getProductByPaginationAndSorting(int pagenumber,int pagesize,String name)
	{
		return productRepository.findAll(PageRequest.of(pagenumber, pagesize,Sort.by(name).ascending()));
	}

}
