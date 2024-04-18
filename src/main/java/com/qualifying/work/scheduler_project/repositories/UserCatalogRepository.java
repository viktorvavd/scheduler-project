package com.qualifying.work.scheduler_project.repositories;

import com.qualifying.work.scheduler_project.entities.UserCatalog;
import com.qualifying.work.scheduler_project.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserCatalogRepository extends JpaRepository<UserCatalog, UUID> {
    List<UserCatalog> findAllByUser(UserEntity userEntity);
    List<UserCatalog> findAllByCatalog_Id(UUID catalog_id);
    UserCatalog findByUserIdAndCatalogId(UUID user_id, UUID catalog_id);

    void deleteByUserIdAndCatalogId(UUID user_id, UUID catalog_id);
}
