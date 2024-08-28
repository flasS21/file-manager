package com.code4fun.storage.db.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.code4fun.exception.FileNotFoundException;
import com.code4fun.storage.db.entity.FileData;
import com.code4fun.storage.db.repository.FileDataRepository;
import com.code4fun.storage.db.service.FileDataService;

@Service
public class FileDataServiceImpl implements FileDataService {

	private static final String DIRECTORY_PATH = "D:\\projects\\download";

	private final FileDataRepository repository;

	public FileDataServiceImpl(FileDataRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Map<?, ?> uploadFile(MultipartFile file) throws IOException {

		// check if file is null
		if (file == null)
			throw new NullPointerException("FILE IS NULL");

		File fileToUpload = new File(DIRECTORY_PATH + File.separator + file.getOriginalFilename());

		// check security concerns
		if (!Objects.equals(DIRECTORY_PATH, fileToUpload.getParent()))
			throw new SecurityException("UNAUTHORISED ACCESS");

		// create filePath
		String filePath = DIRECTORY_PATH + File.separator + file.getOriginalFilename();
		
		// copy file to DIRECTORY_PATH
		file.transferTo(new File(filePath).toPath());

		// build FileData object to persist into DB
		FileData fileData = FileData.builder()
				.fileName(file.getOriginalFilename())
				.fileType(file.getContentType())
				.filePath(filePath).build();
		
		// persist file to DB if not null
		if (fileData != null) {
			repository.save(fileData);
			return Map.of(
					"status", true, 
					"file-name", file.getOriginalFilename(), 
					"file-size", file.getSize() / 1024 + " KB");
		} else {
			return Map.of("status", false);
		}

	}

	@Override
	public File downloadFile(String fileName) {

		// check if fileName is null or empty
		if (fileName == null || fileName.isEmpty())
			throw new InvalidFileNameException(fileName, "file name is missing or invalid");

		// retrieve the FileData object
		Optional<FileData> optionalFileData = repository.findByFileName(fileName);

		// check if FileData object is not null
		if (!optionalFileData.isPresent())
			throw new FileNotFoundException(fileName + " NOT FOUND");

		// extract filePath
		String filePath = optionalFileData.get().getFilePath();
		
		// generate file to return
		File fileToDownload = new File(filePath);
		
		// check if fileToDownload exist
		if (!fileToDownload.exists())
			throw new FileNotFoundException(fileName + " NOT FOUND!");

		return fileToDownload;
		
	}

}
