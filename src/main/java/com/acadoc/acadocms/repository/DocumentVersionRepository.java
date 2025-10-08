package com.acadoc.acadocms.repository;

import com.acadoc.acadocms.model.Document;
import com.acadoc.acadocms.model.DocumentVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentVersionRepository extends JpaRepository<DocumentVersion, Long> {
    List<DocumentVersion> findByDocumentId(Long documentId);

    @Query("SELECT MAX(dv.versionNumber) FROM DocumentVersion dv WHERE dv.document.id = :documentId")
    Optional<Integer> findVersionNumberByDocumentId(@Param("documentId") Long documentId);

    Optional<DocumentVersion> findByDocumentAndVersionNumber(Document document, Integer versionNumber);
}
