package com.gcpfile.gcpdemo.services;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;



public interface FileService {
	    ByteArrayResource downloadFileGcpStorageBucket(String fileName);
	    boolean deleteFileFromGcpStorageBucket(String fileName);
	    void uploadFileOnGcpStorageBucket(MultipartFile file) throws IOException;
	
	}