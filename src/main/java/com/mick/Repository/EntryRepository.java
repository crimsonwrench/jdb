package com.mick.Repository;


import com.mick.Entity.Entry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EntryRepository extends CrudRepository<Entry, Integer> {
    Set<Entry> findByUserName(String userName);
    Set<Entry> findByProjectTitle(String projectTitle);
}
