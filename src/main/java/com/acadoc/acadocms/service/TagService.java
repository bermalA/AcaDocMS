package com.acadoc.acadocms.service;

import com.acadoc.acadocms.model.Document;
import com.acadoc.acadocms.model.Tag;
import com.acadoc.acadocms.repository.DocumentRepository;
import com.acadoc.acadocms.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final DocumentRepository documentRepository;

    public Tag createOrGetTag(String tagName){
        return tagRepository.findByName(tagName)
                .orElseGet(()->{
                    Tag newTag = new Tag();
                    newTag.setName(tagName);
                    return tagRepository.save(newTag);
                });
    }

    public void addTagsToDocument(Long documentId, List<String> tagNames){
        Document document = documentRepository.findById(documentId)
                .orElseThrow(()->new RuntimeException("Document not found"));

        for(String tagName : tagNames){
            Tag tag = createOrGetTag(tagName);
            document.getTags().add(tag);
        }
        documentRepository.save(document);
    }

    @Transactional
    public List<Tag> searchTags(String keyword){
        return tagRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Transactional
    public List<Tag> getPopularTags(int limit){
        List<Tag> allTags = tagRepository.findAll();
        allTags.sort((a,b)-> Integer.compare(b.getDocuments().size(), a.getDocuments().size()));
        return allTags.stream().limit(limit).toList();
    }
}
