package com.mick.Entity;


import com.mick.Utility.CmdHandler;

import javax.persistence.*;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
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

    @JoinColumn(name = "user_id")
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
        return  CmdHandler.getSpaces(String.valueOf(id)) +
                CmdHandler.getSpaces(String.valueOf(project.getId())) +
                CmdHandler.getSpaces(String.valueOf(user.getId())) + reportText + "\n";
    }

    public Issue(User user, Project project, String reportText) {
        this.user = user;
        this.project = project;
        this.reportText = reportText;
    }
    public Issue() {}
}
