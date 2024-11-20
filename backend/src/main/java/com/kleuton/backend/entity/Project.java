package com.kleuton.backend.entity;

import java.time.Instant;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Instant start_date;

    @Column(name = "end_date")
    private Instant end_date;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

    @ManyToMany
    @JoinTable(name = "project_manager", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resource> managers;

}
