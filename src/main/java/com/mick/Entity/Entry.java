package com.mick.Entity;


import javax.persistence.*;

@Entity
@Table(name = "entries")
public class Entry {

    private int id;
    private User user;
    private Project project;
    private String reportText;

    @Id
    @GeneratedValue
    @Column(name = "entry_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getReportText() { return reportText; }

    public void setReportText(String reportText) { this.reportText = reportText; }
}
