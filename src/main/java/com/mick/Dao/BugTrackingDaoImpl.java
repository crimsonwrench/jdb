package com.mick.Dao;

import com.mick.Entity.Entry;
import com.mick.Entity.Project;
import com.mick.Entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class BugTrackingDaoImpl implements BugTrackingDao {

    @Override
    public Entry getEntryByProject(Project project) { return new Entry(); }

    @Override
    public Entry getEntryByUser(User user) { return new Entry(); }

    @Override
    public Collection<Entry> getAllEntries() { return new ArrayList<>(0); }
}
