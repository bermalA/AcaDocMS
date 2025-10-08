package com.acadoc.acadocms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document_versions")
public class DocumentVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_document_version_document"))
    private Document document;
    @Column(name = "version_number", nullable = false)
    private Integer versionNumber;
    @Version
    private Long version;
    @Column(name = "s3_object_key", nullable = false)
    private String s3ObjectKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false,
                foreignKey = @ForeignKey(name = "fk_document_uploaded_by"))
    private User uploadedBy;
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private Instant uploadedAt = Instant.now();
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "mime_type")
    private String mimeType;
    private String checksum;
    @Column(name = "change_log", columnDefinition = "TEXT")
    private String changeLog;
}
