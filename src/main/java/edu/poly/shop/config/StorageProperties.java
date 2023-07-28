package edu.poly.shop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("storage") //ánh xạ với storage trong application.properties
@Data
public class StorageProperties {
	private String location; //ánh xạ với storage trong application.properties

}
