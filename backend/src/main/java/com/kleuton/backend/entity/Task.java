package com.kleuton.backend.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Instant start_date;

    @Column(name = "end_date")
    private Instant end_date;

    @OneToOne
    @JoinColumn(name = "task_status_id", referencedColumnName = "id")
    private TaskStatus taskStatus;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    Project project;

    @OneToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    Resource resource;
}
