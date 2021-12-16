package renan.springcrm.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import renan.springcrm.dtos.ProductDTO;
import renan.springcrm.entities.Product;
import renan.springcrm.repositories.ProductRepository;
import renan.springcrm.services.exceptions.DatabaseException;
import renan.springcrm.services.exceptions.ResourceBadRequestException;
import renan.springcrm.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional(readOnly = false)
	public ProductDTO insert(MultipartFile file, ProductDTO dto) throws IOException {

		Product entity = new Product();

		byte[] fileByte = file.getBytes();
		String fileExtension = file.getContentType().split("\\/")[1];
		String fileBase64 = "data:image/" + fileExtension + ";base64," + new Base64().encodeToString(fileByte);
		
		if (validateExtensionFile(fileExtension) == false) {
			throw new ResourceBadRequestException("A foto do prodoto deve ser: png, jpg, jpeg");
		}

		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgBase64(fileBase64);
		entity.setImgExtension(fileExtension);
		entity.setCategory(dto.getCategory());
		entity = productRepository.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Pageable pageable, Long categoryId) {
		
		if (categoryId == 0) {
			Page<Product> list = productRepository.findAll(pageable);
			return list.map(x -> new ProductDTO(x));
		}
		
		Page<Product> list = productRepository.findByCategoryId(pageable, categoryId);
		return list.map(x -> new ProductDTO(x));
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
		return new ProductDTO(entity);
	}
	
	@Transactional(readOnly = false)
	public ProductDTO update(Long id, MultipartFile file, ProductDTO dto) throws IOException {
		try {

			Product entity = productRepository.getOne(id);
			
			if (file != null) {
				
				byte[] fileByte = file.getBytes();
				String fileExtension = file.getContentType().split("\\/")[1];
				String fileBase64 = "data:image/" + fileExtension + ";base64," + new Base64().encodeToString(fileByte);
				
				if (validateExtensionFile(fileExtension) == false) {
					throw new ResourceBadRequestException("A foto do prodoto deve ser: png, jpg, jpeg");
				}
				
				entity.setName(dto.getName());
				entity.setDescription(dto.getDescription());
				entity.setPrice(dto.getPrice());
				entity.setImgBase64(fileBase64);
				entity.setImgExtension(fileExtension);
				entity.setCategory(dto.getCategory());
				
				entity = productRepository.save(entity);
				return new ProductDTO(entity);
			} else {
				entity.setName(dto.getName());
				entity.setDescription(dto.getDescription());
				entity.setPrice(dto.getPrice());
				entity.setImgBase64(entity.getImgBase64());
				entity.setImgExtension(entity.getImgExtension());
				entity.setCategory(dto.getCategory());
				
				entity = productRepository.save(entity);
				return new ProductDTO(entity);
			}
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	private boolean validateExtensionFile(String fileExtension) {
		
		final List<String> contentTypes = Arrays.asList("png", "jpeg", "png");
		
		if (!contentTypes.contains(fileExtension)) {
			return false;
		}
		
		return true;
	}
}
 