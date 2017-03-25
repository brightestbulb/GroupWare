package com.study.groupware.util;

import java.io.File;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.util.FileCopyUtils;

public class FileUpload {
	
	public String uploadfile(String originalName, byte[] fileData, String uploadPath) throws Exception {
		
		UUID uid = UUID.randomUUID();
		
		String savedName = uid.toString() + "_" + originalName;
		
		System.out.println("uploadPath : " + uploadPath);
		System.out.println("savedName : " + savedName);
		
		File target = new File(uploadPath, savedName);
		
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
		
	}
}
