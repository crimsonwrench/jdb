package com.mick.Entity;


import javax.persistence.*;

@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private String reportText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getReportText() { return reportText; }

    public void setReportText(String reportText) { this.reportText = reportText; }

    @Override
    public String toString() {
        return String.format("%d\t%d\t%d\t%s", id, project.getId(), user.getId(), reportText);
    }

    public Entry(User user, Project project, String reportText) {
        this.user = user;
        this.project = project;
        this.reportText = reportText;
    }
    public Entry() {}
}
