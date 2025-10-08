package com.acadoc.acadocms.service;

import com.acadoc.acadocms.model.Document;
import com.acadoc.acadocms.model.DocumentVisibility;
import com.acadoc.acadocms.model.User;
import com.acadoc.acadocms.repository.DocumentRepository;
import com.acadoc.acadocms.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public Document createDocument(String title, Long ownerId, String description, DocumentVisibility visibility) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        Document document = new Document();
        document.setName(title);
        document.setOwner(owner);
        document.setDescription(description);
        document.setVisibility(visibility);

        return documentRepository.save(document);
    }

    @Transactional
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Document not found"));
    }

    @Transactional
    public List<Document> getUserDocuments(Long userId) {
        return documentRepository.findByOwnerId(userId);
    }

    public Document updateDocument(Long id, String title, Long ownerId, String description, DocumentVisibility visibility) {
        Document document = getDocumentById(id);

        if(title != null) {
            document.setName(title);
        }
        if(description != null) {
            document.setDescription(description);
        }
        if(visibility != null) {
            document.setVisibility(visibility);
        }

        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        if(!documentRepository.existsById(id)) {
            throw new RuntimeException("Document not found");
        } documentRepository.deleteById(id);
    }

    public Document updateDocumentVisibility(Long id, DocumentVisibility visibility) {
        Document document = getDocumentById(id);
        document.setVisibility(visibility);
        return documentRepository.save(document);
    }

    @Transactional
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
}
