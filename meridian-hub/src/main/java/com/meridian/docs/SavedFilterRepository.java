package com.meridian.docs;

import com.meridian.docs.domain.SavedFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedFilterRepository extends JpaRepository<SavedFilter, Long> {
}
