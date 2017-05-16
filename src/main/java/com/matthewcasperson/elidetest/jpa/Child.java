package com.matthewcasperson.elidetest.jpa;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the child database table.
 */
@Entity
@Table(name = "child")
public class Child implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String description;
  private String name;
  private List<Parent> parent;

  public Child() {
  }

  @Id
  @Column(unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(length = 45)
  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Column(length = 45)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  //bi-directional many-to-one association to Parent
  @ManyToMany(mappedBy = "children", fetch = FetchType.EAGER)
  // @JoinColumn(name="parentId", nullable=false)
  @JsonBackReference
  public List<Parent> getParent() {
    return this.parent;
  }

  public void setParent(List<Parent> parent) {
    this.parent = parent;
  }
}
