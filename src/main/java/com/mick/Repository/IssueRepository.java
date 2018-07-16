package com.mick.Repository;


import com.mick.Entity.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer> {
    Set<Issue> findByUserName(String userName);

    Set<Issue> findByProjectTitle(String projectTitle);

    Set<Issue> findByUserId(int userId);
}
