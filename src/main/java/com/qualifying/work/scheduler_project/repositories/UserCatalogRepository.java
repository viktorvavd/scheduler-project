package com.qualifying.work.scheduler_project.repositories;

import com.qualifying.work.scheduler_project.entities.UserCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCatalogRepository extends JpaRepository<UserCatalog, UUID> {
}
