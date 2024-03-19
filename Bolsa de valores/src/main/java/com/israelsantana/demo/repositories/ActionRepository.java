package com.israelsantana.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.israelsantana.demo.models.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    
}
