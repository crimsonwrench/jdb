package com.mick.Entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    private int id;
    private String title;
    private Set<Entry> entries;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "project_title", length = 100, nullable = false)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "project")
    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return String.format("%d\t%s", getId(), getTitle());
    }
}
