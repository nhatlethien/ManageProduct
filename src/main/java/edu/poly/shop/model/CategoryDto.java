package edu.poly.shop.model;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	private Long categoryId;
	
	@NotEmpty(message = "Name không được bỏ trống")
	@Length(min = 5)
	private String name;
	
	private Boolean isEdit  = false;
}
