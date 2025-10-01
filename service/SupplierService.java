package jsp.spring_boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jsp.spring_boot.dao.SupplierDao;
import jsp.spring_boot.dto.ResponseStructure;
import jsp.spring_boot.entity.Product;
import jsp.spring_boot.entity.Supplier;
import jsp.spring_boot.exception.IdNotFoundException;
import jsp.spring_boot.exception.NoRecordAvailableException;

@Service
public class SupplierService {

	@Autowired
	private SupplierDao supplierDao;
	
	public ResponseEntity<ResponseStructure<Supplier>> saveSupplier( Supplier supplier){
		ResponseStructure<Supplier> response = new ResponseStructure<Supplier>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Record saved successfully");
		response.setData(supplierDao.saveSupplier(supplier));
		return new ResponseEntity<ResponseStructure<Supplier>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<Supplier>> getSupplierById( Integer id){
		ResponseStructure<Supplier> response = new ResponseStructure<Supplier>();
		Optional<Supplier> opt = supplierDao.getSupplierById(id);
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record Fetched successfully");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Supplier>>(response,HttpStatus.OK);
		}
		else
			throw new IdNotFoundException("No Record Found since id is invalid...!!");		
	}
	
	public ResponseEntity<ResponseStructure<List<Supplier>>> getAllSupplier(){
		ResponseStructure<List<Supplier>> response = new ResponseStructure<List<Supplier>>();
		List<Supplier> suppliers = supplierDao.getAllSupplier();
		if(suppliers.isEmpty())
			throw new NoRecordAvailableException("No Records found...!");
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Records fetched successfully");
		response.setData(suppliers);
		return new ResponseEntity<ResponseStructure<List<Supplier>>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Supplier>> updateSupplier( Supplier supplier){
		ResponseStructure<Supplier> response = new ResponseStructure<Supplier>();
		
		Optional<Supplier> existingSupplier = supplierDao.getSupplierById(supplier.getId());
		
		if(existingSupplier.isPresent()) {
			Supplier newSupplier = existingSupplier.get();
			newSupplier.setName(supplier.getName());
			newSupplier.setContact(supplier.getContact());
			newSupplier.setEmail(supplier.getEmail());
			newSupplier.setCompanyName(supplier.getCompanyName());
			
			response.setStatusCode(HttpStatus.CREATED.value());
			response.setMessage("Record Updated successfully");
			response.setData(supplierDao.saveSupplier(newSupplier));
			return new ResponseEntity<ResponseStructure<Supplier>>(response,HttpStatus.CREATED);
		}
		else
			throw new IdNotFoundException("Couldn't update the record since id is invalid");
	}
	
	
	public ResponseEntity<ResponseStructure<String>> deleteSupplier( Integer id){
		ResponseStructure<String> response = new ResponseStructure<>();
		Optional<Supplier> opt = supplierDao.getSupplierById(id);
		if(opt.isPresent()) {
			supplierDao.deleteSupplier(id);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record Deleted successfully");
			response.setData(null);
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
			throw new NoRecordAvailableException("No record Found since id is invalid....!");
	}
	
	public ResponseEntity<ResponseStructure<Supplier>> getSupplierByProduct( Product product){
		ResponseStructure<Supplier> response = new ResponseStructure<Supplier>();
		Optional<Supplier> opt = supplierDao.getSupplierByProduct(product.getId());
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Record Fetched successfully");
			response.setData(opt.get());
			return new ResponseEntity<ResponseStructure<Supplier>>(response,HttpStatus.OK);
		}
		else
			throw new NoRecordAvailableException("No Supplier Found.. Product id may be invalid..!!");		
	}
	
	public ResponseEntity<ResponseStructure<Page<Supplier>>> getSupplierByPaginationAndSorting( int pageNumber, int pageSize, String name){
		ResponseStructure<Page<Supplier>> response = new ResponseStructure<>();
		Page<Supplier> supplier = supplierDao.getSupplierByPaginationAndSorting(pageNumber, pageSize, name);
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Suppliers fetched successfully with pagination and sorting");
		response.setData(supplier);
		
		return new ResponseEntity<ResponseStructure<Page<Supplier>>>(response,HttpStatus.OK);

	}	
	
}
