package com.code4fun.storage.directory.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Objects;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.code4fun.exception.FileNotFoundException;
import com.code4fun.storage.directory.service.DirectoryService;

@Service
public class DirectoryServiceImpl implements DirectoryService {

	// directory path
	private final String DIRECTORY_PATH = "D:\\projects\\download";

	private static final Long maxFileSize = 1073741824L;

	public Map<?, ?> uploadFile(MultipartFile file) throws IOException, MissingServletRequestPartException {

		// check if file is NULL
		// this exception handled globally - no need to explicitly handle here
		// if I don't handle exception here it will still handle it globally
		// functionality of the app will be unaffected
		if (file == null) {
			throw new MissingServletRequestPartException("FILE IS NULL");
		}

		// check if the file size is too large
		// this exception handled globally - no need to explicitly handle here
		// if I don't handle exception here it will still handle it globally
		// functionality of the app will be unaffected
		if (file.getSize() > maxFileSize) {
			throw new MaxUploadSizeExceededException(-1);
		}

		// generate FILE_PATH
		File fileToUpload = new File(DIRECTORY_PATH + File.separator + file.getOriginalFilename());

		// check the security concerns like storage directory is the same
		if (!Objects.equals(DIRECTORY_PATH, fileToUpload.getParent()))
			throw new SecurityException("UNAUTHORISED DIRECTORY ACCESS!");

		// finally save the file to DIRECTORY_PATH
		long fileSize = Files.copy(file.getInputStream(), fileToUpload.toPath(), StandardCopyOption.REPLACE_EXISTING);

		return Map.of("status", file.getOriginalFilename() + " uploaded", "file-size", (fileSize / 1024) + " KB");

//		return "file uploaded : " + (fileSize / 1024) + " KB";

	}

	public File downloadFile(String fileName) throws FileNotFoundException {

		// check if file name is null or empty
		if (fileName == null || fileName.isEmpty()) {
			throw new InvalidFileNameException(fileName, "file name is missing or invalid");
		}

		// generate download path
		File fileToDownload = new File(DIRECTORY_PATH + File.separator + fileName);

		// check the security concerns
		if (!Objects.equals(DIRECTORY_PATH, fileToDownload.getParent()))
			throw new SecurityException("UNAUTHORISED DIRECTORY ACCESS!");

		// check if file exist or not
		if (!fileToDownload.exists())
			throw new FileNotFoundException(fileName + " NOT FOUND!");

		return fileToDownload;

	}

}
