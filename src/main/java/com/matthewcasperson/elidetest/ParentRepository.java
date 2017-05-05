package com.matthewcasperson.elidetest;

import com.matthewcasperson.elidetest.jpa.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
}
