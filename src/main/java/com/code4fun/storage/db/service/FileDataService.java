package com.code4fun.storage.db.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileDataService {

	Map<?, ?> uploadFile(MultipartFile file) throws IOException;

	File downloadFile(String fileName);

}
