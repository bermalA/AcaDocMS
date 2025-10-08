package com.acadoc.acadocms.service;

import com.acadoc.acadocms.model.Document;
import com.acadoc.acadocms.model.DocumentVersion;
import com.acadoc.acadocms.model.User;
import com.acadoc.acadocms.repository.DocumentRepository;
import com.acadoc.acadocms.repository.DocumentVersionRepository;
import com.acadoc.acadocms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentVersionService {
    private final DocumentVersionRepository documentVersionRepository;
    private final DocumentRepository  documentRepository;
    private final UserRepository userRepository;

    public DocumentVersion createVersion(Long docId, Long ownerId, String s3ObjectKey,
                                         Long fileSize, String mimeType, String changeLog) {
        Document document = documentRepository.findById(docId)
                .orElseThrow(()-> new RuntimeException("Document not found"));

        User owner = userRepository.findById(ownerId)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Integer versionNumber = calculateNewVersion(docId);

        DocumentVersion version = new  DocumentVersion();
        version.setDocument(document);
        version.setUploadedBy(owner);
        version.setVersionNumber(versionNumber);
        version.setS3ObjectKey(s3ObjectKey);
        version.setFileSize(fileSize);
        version.setMimeType(mimeType);
        version.setChangeLog(changeLog);

        DocumentVersion savedVersion = documentVersionRepository.save(version);

        document.setLatestVersion(savedVersion);
        documentRepository.save(document);
        return savedVersion;
    }

    @Transactional
    public Integer calculateNewVersion(Long docId) {
        Integer maxVersion = documentVersionRepository.findVersionNumberByDocumentId(docId)
                .orElse(0);
        return maxVersion + 1;
    }

    @Transactional
    public List<DocumentVersion> getDocumentVersions(Long documentId) {
        return documentVersionRepository.findByDocumentId(documentId);
    }

    @Transactional
    public DocumentVersion getVersionByNumber(Long docId, Integer versionNumber) {
        Document document = documentRepository.findById(docId)
                .orElseThrow(()-> new RuntimeException("Document not found"));
        return documentVersionRepository.findByDocumentAndVersionNumber(document, versionNumber)
                .orElseThrow(()-> new RuntimeException("Version not found"));
    }

    @Transactional
    public DocumentVersion getLatestVersionByDocument(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(()-> new RuntimeException("Document not found"));
        return document.getLatestVersion();
    }

    public DocumentVersion updateVersionInfo(Long versionId, String changeLog){
        DocumentVersion version = documentVersionRepository.findById(versionId)
                .orElseThrow(()-> new RuntimeException("Version not found"));
        if(changeLog != null){
            version.setChangeLog(changeLog);
        }return documentVersionRepository.save(version);
    }
}