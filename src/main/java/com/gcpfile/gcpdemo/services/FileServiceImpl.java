package com.gcpfile.gcpdemo.services;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gcpfile.gcpdemo.entity.FileData;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.secretmanager.v1.AccessSecretVersionRequest;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretVersionName;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
@Service
public class FileServiceImpl implements FileService {

	@Value("${gcp.bucket.name}")
	private String bucketName;
	
	@Value("${spring.cloud.gcp.project-id}")
	private String projectId;
	
//    @Value("${my.secret.property}")
//	private String secretValue;

	@Autowired
	Storage storage;


	@Autowired
    private FileRepository fileRepository;
	

	
	
	
	@Override
	public void uploadFileOnGcpStorageBucket(MultipartFile file) throws IOException {
		FileData fileDataObj = new FileData();
		String filename = file.getOriginalFilename();
		String fileType = file.getContentType();
		byte[] fileDta = file.getBytes();
		
//		System.out.println(secretValue);
		
		BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
		Blob blob = storage.create(blobInfo, file.getBytes());

		GoogleCredentials credentials = ServiceAccountCredentials
				.fromStream(FileServiceImpl.class.getResourceAsStream("/my-project-file-410710-f6557f4b5872.json"))
				.createScoped("https://www.googleapis.com/auth/cloud-platform");
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

		Blob blob1 = storage.get(BlobId.of(bucketName, file.getOriginalFilename()));
		String fileUrl = blob1.getSelfLink();
		fileDataObj.setFileName(filename);
		fileDataObj.setFileType(fileType);
		fileDataObj.setFileUrl(fileUrl);
		fileDataObj.setFileData(fileDta);
		fileRepository.save(fileDataObj);
		 
	}
	// Download file from database table 
	 public Optional<FileData> getFileById(Long id) {
	        return fileRepository.findById(id);
	    }
	
	// Download file from Cloud storage 
	@Override
	public ByteArrayResource downloadFileGcpStorageBucket(String fileName) {

		Blob blob = storage.get(bucketName, fileName);
		ByteArrayResource resource = new ByteArrayResource(blob.getContent());

		return resource;
	}
	
	@Override
	public boolean deleteFileFromGcpStorageBucket(String fileName) {
		Blob blob = storage.get(bucketName, fileName);
		return blob.delete();
	}
	

	
}