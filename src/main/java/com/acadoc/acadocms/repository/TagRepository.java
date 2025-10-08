package com.acadoc.acadocms.repository;

import com.acadoc.acadocms.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository  extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    List<Tag> findByNameContainingIgnoreCase(String name);
}
