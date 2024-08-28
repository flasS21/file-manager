package com.code4fun.storage.directory.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.code4fun.exception.FileNotFoundException;

public interface DirectoryService {

	Map<?, ?> uploadFile(MultipartFile file) throws IOException, MissingServletRequestPartException;

	File downloadFile(String fileName) throws FileNotFoundException;

}
