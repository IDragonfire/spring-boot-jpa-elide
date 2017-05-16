package com.matthewcasperson.elidetest.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the parent database table.
 */
@Entity
@Table(name = "parent")
public class Parent implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String description;
  private String name;
  private List<Child> children;

  public Parent() {
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

  //bi-directional many-to-one association to Child
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
          name = "parents_childs",
          joinColumns = @JoinColumn(name = "ParentID", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "Child_ID", referencedColumnName = "id"))
  public List<Child> getChildren() {
    return this.children;
  }

  public void setChildren(List<Child> children) {
    this.children = children;
  }

/*
    // Traditional case

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    public Integer getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return this.name; }

    public void setName(String name) {
        this.name = name;
    }

    private Integer id;

    private String name;

    public Parent() {
    }*/

/*
     // Lombok Case

    @Id
    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Getter
    @Setter
    private String name;


*/

}
