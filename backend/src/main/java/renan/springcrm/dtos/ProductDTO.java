package renan.springcrm.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import renan.springcrm.entities.Category;
import renan.springcrm.entities.Product;

public class ProductDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Campo nome requerido")
	private String name;
	
	@NotEmpty(message = "Campo descrição requerido")
	private String description;
	
	@NotNull(message = "Campo preço requerido")
	private Double price;
	
	private String imgBase64;
	
	private String imgExtension;
	
	@NotNull(message = "Campo categoria requerido")
	private Category category;
	
	public ProductDTO() {
	}
	
	public ProductDTO(Long id, String name, String description, Double price, String imgBase64, String imgExtension,
			Category category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgBase64 = imgBase64;
		this.imgExtension = imgExtension;
		this.category = category;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgBase64 = entity.getImgBase64();
		imgExtension = entity.getImgExtension();
		category = entity.getCategory();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgBase64() {
		return imgBase64;
	}

	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}

	public String getImgExtension() {
		return imgExtension;
	}

	public void setImgExtension(String imgExtension) {
		this.imgExtension = imgExtension;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
