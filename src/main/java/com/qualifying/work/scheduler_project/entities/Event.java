package com.qualifying.work.scheduler_project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Calendar;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Calendar startTime;
    @Column(nullable = false)
    private Calendar endTime;

//    @ManyToOne
//    @JoinColumn(name = "group_id",nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private GroupEntity group;
}
