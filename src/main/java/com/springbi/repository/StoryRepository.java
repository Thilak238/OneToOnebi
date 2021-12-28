package com.springbi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springbi.entity.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

}
