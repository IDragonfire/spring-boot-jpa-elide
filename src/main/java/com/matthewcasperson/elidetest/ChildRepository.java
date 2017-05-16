package com.matthewcasperson.elidetest;

import com.matthewcasperson.elidetest.jpa.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<Child, Integer> {
}
