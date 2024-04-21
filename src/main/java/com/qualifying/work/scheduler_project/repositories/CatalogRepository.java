package com.qualifying.work.scheduler_project.repositories;

import com.qualifying.work.scheduler_project.entities.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog, UUID> {
    Catalog findByCode(String code);
    List<Catalog> findAllByParentCatalogId(UUID parentCatalog_id);
}
