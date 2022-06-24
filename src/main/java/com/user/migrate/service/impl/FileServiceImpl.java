package com.user.migrate.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.user.migrate.service.FileService;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;

	@Override
	public String uploadImageFileSystem(String path, MultipartFile file) throws IOException {
		
		//upload to server filesystem
		String name = file.getOriginalFilename();
		
		String filepath = path + File.separator + name;
		
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
		
		Files.copy(file.getInputStream(), Paths.get(filepath));
		
		return name;
		
	}
	
	@Override
	public String uploadFileCloud(String username, MultipartFile file) {
		
		String fileName = ("8v74tq34ht4q3tq5q4T45Gw45gW"+username).hashCode()+file.getOriginalFilename();
		try {
			amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fileName = "Error while uploading file";
		}
		return fileName;
	}

	@Override
	public InputStream downloadImage(String path, String filename) throws FileNotFoundException {
		String fullpath = path+File.separator+filename;
		InputStream in = new FileInputStream(fullpath);
		return in;
	}

	@Override
	public String uploadImageDatabase(MultipartFile file) throws IOException {
		
		//returns base64 encoded string of image file data
		try{
			byte[] encodedProfile = Base64.encodeBase64(file.getBytes());
			String result = new String(encodedProfile);
			return result;
		}catch(Exception e) {
	        e.printStackTrace();
	        return "Error while encoding file";
	    }
		
	}
	
}
