package edu.poly.shop.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.poly.shop.config.StorageProperties;
import edu.poly.shop.exception.StorageException;
import edu.poly.shop.exception.StorageFileNotFoundException;
import edu.poly.shop.service.StorageService;

//khởi tạo file upload image
@Service
public final class FileSystemStorageServiceImpl implements StorageService{
	private final Path rootLocation;
	
	@Override
	public String getStoredFilename(MultipartFile file, String id) {
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		return "p" + id + "." + ext;
	}
	
	public FileSystemStorageServiceImpl(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	@Override
	public void store(MultipartFile file, String storedFilename) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("lỗi file");
			}
			
			Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename))
					.normalize().toAbsolutePath();
			
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Canot store file outside current directory");
			}
			try (InputStream inputStream = file.getInputStream() ) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new StorageException("failed to store file",e);
		}
	}
	
	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			
			throw new StorageFileNotFoundException("could not read file" + filename);
			
		} catch (Exception e) {
			throw new StorageFileNotFoundException("could not read file:"+ filename);
		}
	}
	
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
	@Override
	public void delete(String storedFilename) throws IOException {
		Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
		Files.delete(destinationFile);
	}
	
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
		} catch (Exception e) {
			throw new StorageException("could not initialize storage", e);
		}
	}
}




