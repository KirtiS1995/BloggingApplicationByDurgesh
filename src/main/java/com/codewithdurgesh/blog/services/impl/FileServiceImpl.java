package com.codewithdurgesh.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codewithdurgesh.blog.services.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		log.info("Entering DAO call for uploading image  to post ");

		//fileName
				String name=file.getOriginalFilename();
				
				//Random nAME GENERATOR
				String randomId = UUID.randomUUID().toString();
				String fileName1 =randomId.concat(name.substring(name.lastIndexOf(".")));	
				
								//full path
				String filePath=path+ File.separator+ fileName1;
				
				//Create folder if not created
					File f= new File(path);
					if(!f.exists()) {
						f.mkdir();
					}
							
				//File copy
				Files.copy(file.getInputStream(), Paths.get(filePath));
				log.info("Completed DAO call for uploading image  to post ");
				return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		log.info("Entering DAO call for serving image  to post ");
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		log.info("Completed DAO call for serving image  to post ");
		return is;
	}

}
