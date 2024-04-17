package com.qualifying.work.scheduler_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserCatalog> userCatalogList;
}
