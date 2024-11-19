package com.likelion.bizup.module.community.repository;

import com.likelion.bizup.module.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
}
