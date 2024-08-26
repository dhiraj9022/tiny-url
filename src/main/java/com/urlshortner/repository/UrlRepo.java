package com.urlshortner.repository;

import com.urlshortner.entity.UrlMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UrlRepo extends JpaRepository<UrlMap, UUID> {
}
