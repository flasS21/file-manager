package com.code4fun.storage.db.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.code4fun.storage.db.service.FileDataService;

@RestController
@RequestMapping("/db")
public class FileDataController {

	private final FileDataService fileDataService;

	public FileDataController(FileDataService fileDataService) {
		super();
		this.fileDataService = fileDataService;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadToDB(@RequestParam MultipartFile file) throws IOException {

		Map<?, ?> uploadStatus = fileDataService.uploadFile(file);

		if (uploadStatus.containsValue(true)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(uploadStatus);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uploadStatus);
		}

	}

	@GetMapping("/download")
	public ResponseEntity<?> downloadFromDB(@RequestParam String fileName) {

		File downloadedFile = fileDataService.downloadFile(fileName);
		
		if (downloadedFile != null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\"")
					.contentLength(downloadedFile.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new FileSystemResource(downloadedFile));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fileName + " NOT FOUND");
		}
		
	}

}
