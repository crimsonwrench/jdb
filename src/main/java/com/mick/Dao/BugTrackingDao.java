package com.mick.Dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BugTrackingDao {
    @PersistenceContext
    private EntityManager entityManager;
}
