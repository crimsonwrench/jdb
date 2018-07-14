package com.mick.Dao;

import com.mick.Entity.Entry;
import com.mick.Entity.Project;
import com.mick.Entity.User;

import java.util.Collection;

public interface BugTrackingDao {

    Entry getEntryByProject(Project project);
    Entry getEntryByUser(User user);
    Collection<Entry> getAllEntries();
}
