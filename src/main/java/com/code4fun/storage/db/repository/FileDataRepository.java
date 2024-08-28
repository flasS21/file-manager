package com.code4fun.storage.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code4fun.storage.db.entity.FileData;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

	Optional<FileData> findByFileName(String fileName);

}
