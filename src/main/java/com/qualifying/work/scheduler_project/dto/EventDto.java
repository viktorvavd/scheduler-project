package com.qualifying.work.scheduler_project.dto;

import com.qualifying.work.scheduler_project.entities.GroupEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto {
    private UUID id;
    @NotNull
    private String name;

    @NotNull
    private Date startTime;
    @NotNull
    private Date endTime;
//
//    private GroupDto group;
}
