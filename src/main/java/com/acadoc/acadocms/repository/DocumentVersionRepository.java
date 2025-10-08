package com.acadoc.acadocms.repository;

import com.acadoc.acadocms.model.Document;
import com.acadoc.acadocms.model.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    List<DocumentVersion> findByDocumentId(Long documentId);

    Optional<DocumentVersion> findByDocumentAndVersionNumber(Document document, Integer versionNumber);
}
