package com.mick.Entity;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private int id;

    @Column(name = "project_title", length = 100, nullable = false)
    private String title;

    @OneToMany(mappedBy = "project")
    private ArrayList<Entry> entries;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
