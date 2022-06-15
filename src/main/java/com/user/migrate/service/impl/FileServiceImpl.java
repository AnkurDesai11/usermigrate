package com.user.migrate.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.migrate.service.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		
		String name = file.getOriginalFilename();
		
		String filepath = path + File.separator + name;
		
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		return name;
		
	}

	@Override
	public InputStream downloadImage(String path, String filename) throws FileNotFoundException {
		String fullpath = path+File.separator+filename;
		InputStream in = new FileInputStream(fullpath);
		return in;
	}
	
}
