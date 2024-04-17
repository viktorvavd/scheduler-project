package com.qualifying.work.scheduler_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;

//    @OneToMany(fetch = FetchType.LAZY)
////    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<Event> events;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Catalog catalog;

}
