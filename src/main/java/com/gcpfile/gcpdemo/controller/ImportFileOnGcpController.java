package com.gcpfile.gcpdemo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gcpfile.gcpdemo.entity.FileData;
import com.gcpfile.gcpdemo.services.FileServiceImpl;

@RestController
@RequestMapping("/files")
public class ImportFileOnGcpController {

	@Autowired
	FileServiceImpl fileService;

	// http://localhost:8080/files/fileUpload
	@PostMapping("/fileUpload")
	public ResponseEntity<String> uploadFileOnGcpStorageBucket(@RequestParam MultipartFile file) throws IOException {
		fileService.uploadFileOnGcpStorageBucket(file);
		return ResponseEntity.ok("File uploaded on GCP Storage Bucket successfully");
	}

	// http://localhost:8080/files/downloadFile?fileName=sample.pdf on web
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFileGcpStorageBucket(@RequestParam String fileName) {
		ByteArrayResource resource = fileService.downloadFileGcpStorageBucket(fileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
	}

//http://localhost:8080/files/download/3 on web
	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
		FileData fileEntity = fileService.getFileById(id).get();
		ByteArrayResource resource = new ByteArrayResource(fileEntity.getFileData());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", fileEntity.getFileName());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		return ResponseEntity.ok().headers(headers).body(resource);
	}

	// http://localhost:8080/files/delete?fileName=sample.pdf
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteFileFromGcpStorageBucket(@RequestParam String fileName) {

		fileService.deleteFileFromGcpStorageBucket(fileName);

		return ResponseEntity.ok(" File deleted from GCP Storage Bucket successfully");
	}

}