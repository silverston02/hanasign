package com.hanasign.project.repository;

import com.hanasign.project.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    //파일 이름과 타입으로 고유하게 조회
    Optional<Attachment> findByFileNameAndFileType(String fileName, String fileType);

    //파일 저장 경로로 조회
    Optional<Attachment> findByFilePath(String filePath);
}