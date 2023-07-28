package edu.poly.shop.model;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	
	@NotEmpty
	@Length(min = 6)
	private String username;
	
	@NotEmpty
	@Length(min = 6)
	private String password;
	
	private Boolean isEdit = false;
}
