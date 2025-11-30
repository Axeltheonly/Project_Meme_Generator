package com.meme.repository;

import com.meme.model.Meme;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemeRepository extends JpaRepository<Meme, Long> {
}
