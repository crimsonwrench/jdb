package com.mick.Repository;


import com.mick.Entity.Issue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Integer> {

    Set<Issue> findByUserId(int userId);

    Set<Issue> findByProjectId(int projectId);

    Set<Issue> findByProjectIdAndUserId(int projectId, int userId);

}
