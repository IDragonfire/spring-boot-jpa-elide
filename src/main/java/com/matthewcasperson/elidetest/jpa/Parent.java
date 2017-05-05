package com.matthewcasperson.elidetest.jpa;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the parent database table.
 */
@Entity
@Table(name = "parent")
public class Parent implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;

  private String name;

  public Parent() {
  }

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }


  @Column
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }


}
