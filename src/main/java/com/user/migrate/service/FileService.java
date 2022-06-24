package com.user.migrate.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String uploadImageFileSystem(String path, MultipartFile file) throws IOException;
	
	String uploadFileCloud(String username, MultipartFile file);
	
	String uploadImageDatabase(MultipartFile file) throws IOException;
	
	InputStream downloadImage(String path, String filename) throws FileNotFoundException;
	
}
