package com.code4fun.storage.directory.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.code4fun.storage.directory.service.DirectoryService;

@RestController
@RequestMapping("/directory")
public class DirectoryController {

	private static final Logger logger = Logger.getLogger(DirectoryController.class.getName());

	private final DirectoryService directoryService;

	public DirectoryController(DirectoryService directoryService) {
		super();
		this.directoryService = directoryService;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("FILE") MultipartFile file)
			throws MissingServletRequestPartException {
		try {
			Map<?, ?> responseMap = directoryService.uploadFile(file);
			return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "FILE UPLOAD FAILED " + e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/download")
	public ResponseEntity<?> downloadFile(@RequestParam String fileName) {
		File downloadedFile = directoryService.downloadFile(fileName);
		if (downloadedFile != null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\"")
					.contentLength(downloadedFile.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new FileSystemResource(downloadedFile));
		} else {
			logger.log(Level.SEVERE, "FILE DOWNLOAD FAILED ");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
