package com.helix.repo;

import com.helix.model.SavedFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedFilterRepository extends JpaRepository<SavedFilter, Long> {
    List<SavedFilter> findByOwnerUserId(Long ownerUserId);
}
