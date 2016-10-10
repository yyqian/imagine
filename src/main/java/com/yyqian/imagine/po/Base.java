package com.yyqian.imagine.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by yyqian on 12/10/15.
 * A base model
 * Details about how to map an application's domain model to a relational database:
 * http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/
 * JPA 2.1 requirements:
 * 1. The entity class must be annotated with the javax.persistence.Entity annotation
 * 2. The entity class must have a public or protected no-argument constructor.
 * 3. The entity class must be a top-level class.
 * 4. An enum or interface may not be designated as an entity
 * 5. The entity class must not be final. No methods or persistent instance variables of the
 * entity class may be final.
 * 6. If an entity instance is to be used remotely as a detached object, the entity class must
 * implement the Serializable interface.
 * 7. Both abstract and concrete classes can be entities. Entities may extend non-entity classes
 * as well as entity classes,
 * and non-entity classes may extend entity classes.
 * Difference in Hibernate requirements:
 * 1. The entity class must have a no-argument constructor, which may be public, protected or
 * package visibility.
 * 2. The entity class need not be a top-level class.
 * 3. Technically Hibernate can persist final classes or classes with final persistent state
 * accessor (getter/setter) methods.
 * Best practice in Hibernate:
 * 1. non-final classes, non-final getters and setters
 * 2. have a no-argument constructor, defined as public or protected
 * 3. Declare getters and setters for persistent attributes. Attributes (whether fields or
 * getters/setters) need not be declared public.
 * 4. Provide identifier attribute
 *
 * @Embeddable and @Embedded
 * @Transient: transient property, ignored by the Hibernate, not a column in the table
 * @Basic is set by Default: persistent property
 * @Formula can let SQL Database do some computation
 * @Type: If you do not specify a type, Hibernate will use reflection upon the named property and
 * guess the correct Hibernate type.
 * If a property is not annotated, the following rules apply:
 * 1. If the property is of a single type, it is mapped as @Basic
 * 2. if the type of the property is annotated as @Embeddable, it is mapped as @Embedded
 * 3. if the type of the property is Serializable, it is mapped as @Basic in a column holding the
 * object in its serialized version
 * 4. if the type of the property is java.sql.Clob or java.sql.Blob, it is mapped as @Lob with
 * the appropriate LobType
 * 37.1.4 Multiplicity in Entity Relationships
 * Types:
 * - @OneToOne
 * - @OneToMany
 * - @ManyToOne
 * - @ManyToMany
 * 37.1.5 Bidirectional relationship:
 * - inverse side must refer to its owning side by using mappedBy element of @OneToOne,
 * @OneToMany or @ManyToMany.
 * - For @ManyToOne, many side is always the owning side, so must NOT define the mappedBy element.
 * - For @OneToOne, the owning side is the side that contains the foreign key.
 * - For @ManyToMany, either side may be the owning side.
 * Unidirectional relationship: only one entity has a relationship field/property (which is
 * annotated)
 * Query: navigation depends on Bidirectional or Unidirectional. If unidirectional, only the side
 * has a relationship field/property can navigate across relationships.
 * Cascade Operations:
 * If a post instance is deleted, all its comments will also be deleted.
 * @Entity public class Post {
 * @OneToMany( mappedBy="comment", cascade=REMOVE )
 * public Set<Comment> comments = new HashSet<Comment>();
 * }
 * If one comment is deleted from the comment list in a post (the deletion happens in the post
 * entity),
 * this comment entity will become an orphan and will be also deleted.
 * @Entity public class Post {
 * @OneToMany( mappedBy="comment", orphanRemoval="true" )
 * public Set<Comment> comments = new HashSet<Comment>();
 * }
 * cascade=REMOVE, orphanRemoval element can be put in @OneToMany or @OneToOne.
 * 37.2 Entity Inheritance
 * Entity classes can extend non-entity classes, and non-entity classes can extend entity classes
 * . Entity classes can be both abstract and concrete.
 * Abstract entity:
 * - Abstract entity cannot be instantiated, but can be queried. If an abstract entity is the
 * target of a query,
 * - the query operates on all the concrete subclasses of the abstract entity.
 * - Abstract entity do have a corresponding table?
 * @MappedSuperclass: - Mapped superclasses cannot be queried and cannot be used in EntityManager
 * or Query operations.
 * - Mapped superclasses can't be targets of entity relationships. Mapped superclasses can be
 * abstract or concrete.
 * - Mapped superclasses do not have any corresponding tables in the underlying datastore.
 * Non-Entity Superclasses:
 * - can be either abstract or concrete
 * - The state of non-entity superclasses is nonpersistent, and any state inherited from the
 * non-entity superclass by an entity class is nonpersistent.
 * - Non-entity superclasses may not be used in EntityManager or Query operations.
 * - Any mapping or relationship annotations in non-entity superclasses are ignored.
 * Entity Inheritance Mapping Strategies, @Inheritance(strategy=) at root class:
 * - SINGLE_TABLE (default), a single table per class hierarchy, SUGGESTED.
 * - JOINED, in short, the inherited columns will be in a separate table.
 * - TABLE_PER_CLASS (DON'T USE IT), a table per concrete entity class, may NOT be supported by
 * some JPA providers.
 * JPA 2.1 requirements:
 * 1. The entity class must be annotated with the javax.persistence.Entity annotation
 * 2. The entity class must have a public or protected no-argument constructor.
 * 3. The entity class must be a top-level class.
 * 4. An enum or interface may not be designated as an entity
 * 5. The entity class must not be final. No methods or persistent instance variables of the
 * entity class may be final.
 * 6. If an entity instance is to be used remotely as a detached object, the entity class must
 * implement the Serializable interface.
 * 7. Both abstract and concrete classes can be entities. Entities may extend non-entity classes
 * as well as entity classes,
 * and non-entity classes may extend entity classes.
 * Difference in Hibernate requirements:
 * 1. The entity class must have a no-argument constructor, which may be public, protected or
 * package visibility.
 * 2. The entity class need not be a top-level class.
 * 3. Technically Hibernate can persist final classes or classes with final persistent state
 * accessor (getter/setter) methods.
 * Best practice in Hibernate:
 * 1. non-final classes, non-final getters and setters
 * 2. have a no-argument constructor, defined as public or protected
 * 3. Declare getters and setters for persistent attributes. Attributes (whether fields or
 * getters/setters) need not be declared public.
 * 4. Provide identifier attribute
 * @Embeddable and @Embedded
 * @Transient: transient property, ignored by the Hibernate, not a column in the table
 * @Basic is set by Default: persistent property
 * @Formula can let SQL Database do some computation
 * @Type: If you do not specify a type, Hibernate will use reflection upon the named property and
 * guess the correct Hibernate type.
 * If a property is not annotated, the following rules apply:
 * 1. If the property is of a single type, it is mapped as @Basic
 * 2. if the type of the property is annotated as @Embeddable, it is mapped as @Embedded
 * 3. if the type of the property is Serializable, it is mapped as @Basic in a column holding the
 * object in its serialized version
 * 4. if the type of the property is java.sql.Clob or java.sql.Blob, it is mapped as @Lob with
 * the appropriate LobType
 * 37.1.4 Multiplicity in Entity Relationships
 * Types:
 * - @OneToOne
 * - @OneToMany
 * - @ManyToOne
 * - @ManyToMany
 * 37.1.5 Bidirectional relationship:
 * - inverse side must refer to its owning side by using mappedBy element of @OneToOne,
 * @OneToMany or @ManyToMany.
 * - For @ManyToOne, many side is always the owning side, so must NOT define the mappedBy element.
 * - For @OneToOne, the owning side is the side that contains the foreign key.
 * - For @ManyToMany, either side may be the owning side.
 * Unidirectional relationship: only one entity has a relationship field/property (which is
 * annotated)
 * Query: navigation depends on Bidirectional or Unidirectional. If unidirectional, only the side
 * has a relationship field/property can navigate across relationships.
 * Cascade Operations:
 * If a post instance is deleted, all its comments will also be deleted.
 * @Entity public class Post {
 * @OneToMany( mappedBy="comment", cascade=REMOVE )
 * public Set<Comment> comments = new HashSet<Comment>();
 * }
 * If one comment is deleted from the comment list in a post (the deletion happens in the post
 * entity),
 * this comment entity will become an orphan and will be also deleted.
 * @Entity public class Post {
 * @OneToMany( mappedBy="comment", orphanRemoval="true" )
 * public Set<Comment> comments = new HashSet<Comment>();
 * }
 * cascade=REMOVE, orphanRemoval element can be put in @OneToMany or @OneToOne.
 * 37.2 Entity Inheritance
 * Entity classes can extend non-entity classes, and non-entity classes can extend entity classes
 * . Entity classes can be both abstract and concrete.
 * Abstract entity:
 * - Abstract entity cannot be instantiated, but can be queried. If an abstract entity is the
 * target of a query,
 * - the query operates on all the concrete subclasses of the abstract entity.
 * - Abstract entity do have a corresponding table?
 * @MappedSuperclass: - Mapped superclasses cannot be queried and cannot be used in EntityManager
 * or Query operations.
 * - Mapped superclasses can't be targets of entity relationships. Mapped superclasses can be
 * abstract or concrete.
 * - Mapped superclasses do not have any corresponding tables in the underlying datastore.
 * Non-Entity Superclasses:
 * - can be either abstract or concrete
 * - The state of non-entity superclasses is nonpersistent, and any state inherited from the
 * non-entity superclass by an entity class is nonpersistent.
 * - Non-entity superclasses may not be used in EntityManager or Query operations.
 * - Any mapping or relationship annotations in non-entity superclasses are ignored.
 * Entity Inheritance Mapping Strategies, @Inheritance(strategy=) at root class:
 * - SINGLE_TABLE (default), a single table per class hierarchy, SUGGESTED.
 * - JOINED, in short, the inherited columns will be in a separate table.
 * - TABLE_PER_CLASS (DON'T USE IT), a table per concrete entity class, may NOT be supported by
 * some JPA providers.
 * Naming:
 * - lowercase: use word_count,not Word_Count or WORD_COUNT
 * - underscores separate words: use first_name, not firstName
 * - Avoid data types, reserved words: text, timestamp, lock, table
 * - full words, not abbreviations: use middle_name, not mid_nm.
 * - use common abbreviations: i18n and l10n
 * - Table names should be singular, not plural: use team, not teams
 * - primary key: use id, not table_id
 * - foreign Keys: use table_id
 * - timestamp: created_at, updated_at
 */

/**
 * JPA 2.1 requirements:
 * 1. The entity class must be annotated with the javax.persistence.Entity annotation
 * 2. The entity class must have a public or protected no-argument constructor.
 * 3. The entity class must be a top-level class.
 * 4. An enum or interface may not be designated as an entity
 * 5. The entity class must not be final. No methods or persistent instance variables of the
 * entity class may be final.
 * 6. If an entity instance is to be used remotely as a detached object, the entity class must
 * implement the Serializable interface.
 * 7. Both abstract and concrete classes can be entities. Entities may extend non-entity classes
 * as well as entity classes,
 * and non-entity classes may extend entity classes.
 *
 * Difference in Hibernate requirements:
 * 1. The entity class must have a no-argument constructor, which may be public, protected or
 * package visibility.
 * 2. The entity class need not be a top-level class.
 * 3. Technically Hibernate can persist final classes or classes with final persistent state
 * accessor (getter/setter) methods.
 *
 * Best practice in Hibernate:
 * 1. non-final classes, non-final getters and setters
 * 2. have a no-argument constructor, defined as public or protected
 * 3. Declare getters and setters for persistent attributes. Attributes (whether fields or
 * getters/setters) need not be declared public.
 * 4. Provide identifier attribute
 *
 * @Embeddable and @Embedded
 *
 * @Transient: transient property, ignored by the Hibernate, not a column in the table
 * @Basic is set by Default: persistent property
 *
 * @Formula can let SQL Database do some computation
 *
 * @Type: If you do not specify a type, Hibernate will use reflection upon the named property and
 * guess the correct Hibernate type.
 *
 * If a property is not annotated, the following rules apply:
 * 1. If the property is of a single type, it is mapped as @Basic
 * 2. if the type of the property is annotated as @Embeddable, it is mapped as @Embedded
 * 3. if the type of the property is Serializable, it is mapped as @Basic in a column holding the
 * object in its serialized version
 * 4. if the type of the property is java.sql.Clob or java.sql.Blob, it is mapped as @Lob with
 * the appropriate LobType
 */

/**
 *  37.1.4 Multiplicity in Entity Relationships
 *
 *  Types:
 *  - @OneToOne
 *  - @OneToMany
 *  - @ManyToOne
 *  - @ManyToMany
 *
 *  37.1.5 Bidirectional relationship:
 *  - inverse side must refer to its owning side by using mappedBy element of @OneToOne,
 *  @OneToMany or @ManyToMany.
 *  - For @ManyToOne, many side is always the owning side, so must NOT define the mappedBy element.
 *  - For @OneToOne, the owning side is the side that contains the foreign key.
 *  - For @ManyToMany, either side may be the owning side.
 *
 *  Unidirectional relationship: only one entity has a relationship field/property (which is
 *  annotated)
 *
 *  Query: navigation depends on Bidirectional or Unidirectional. If unidirectional, only the
 *  side has a relationship field/property can navigate across relationships.
 *
 *  Cascade Operations:
 *
 *  If a post instance is deleted, all its comments will also be deleted.
 *  @Entity
 *  public class Post {
 *    @OneToMany( mappedBy="comment", cascade=REMOVE )
 *    public Set<Comment> comments = new HashSet<Comment>();
 *  }
 *
 *  If one comment is deleted from the comment list in a post (the deletion happens in the post
 *  entity),
 *  this comment entity will become an orphan and will be also deleted.
 *
 *  @Entity
 *  public class Post {
 *    @OneToMany( mappedBy="comment", orphanRemoval="true" )
 *    public Set<Comment> comments = new HashSet<Comment>();
 *  }
 *
 *  cascade=REMOVE, orphanRemoval element can be put in @OneToMany or @OneToOne.
 * */

/**
 * 37.2 Entity Inheritance
 *
 * Entity classes can extend non-entity classes, and non-entity classes can extend entity classes
 * . Entity classes can be both abstract and concrete.
 *
 * Abstract entity:
 * - Abstract entity cannot be instantiated, but can be queried. If an abstract entity is the
 * target of a query,
 * - the query operates on all the concrete subclasses of the abstract entity.
 * - Abstract entity do have a corresponding table?
 *
 * @MappedSuperclass:
 * - Mapped superclasses cannot be queried and cannot be used in EntityManager or Query operations.
 * - Mapped superclasses can't be targets of entity relationships. Mapped superclasses can be
 * abstract or concrete.
 * - Mapped superclasses do not have any corresponding tables in the underlying datastore.
 *
 * Non-Entity Superclasses:
 * - can be either abstract or concrete
 * - The state of non-entity superclasses is nonpersistent, and any state inherited from the
 * non-entity superclass by an entity class is nonpersistent.
 * - Non-entity superclasses may not be used in EntityManager or Query operations.
 * - Any mapping or relationship annotations in non-entity superclasses are ignored.
 *
 * Entity Inheritance Mapping Strategies, @Inheritance(strategy=) at root class:
 * - SINGLE_TABLE (default), a single table per class hierarchy, SUGGESTED.
 * - JOINED, in short, the inherited columns will be in a separate table.
 * - TABLE_PER_CLASS (DON'T USE IT), a table per concrete entity class, may NOT be supported by
 * some JPA providers.
 */

/**
 * Naming:
 * - lowercase: use word_count,not Word_Count or WORD_COUNT
 * - underscores separate words: use first_name, not firstName
 * - Avoid data types, reserved words: text, timestamp, lock, table
 * - full words, not abbreviations: use middle_name, not mid_nm.
 * - use common abbreviations: i18n and l10n
 * - Table names should be singular, not plural: use team, not teams
 * - primary key: use id, not table_id
 * - foreign Keys: use table_id
 * - timestamp: created_at, updated_at
 */

/**
 * Base uses the @MappedSuperclass annotation to tell the JPA persistence provide that this object
 * is not an entity but it’s fields will be included in each entities table (for the entities that
 * subclass Base). You can use a mapped superclass to define all common fields.
 */

// setters: setVersion, setUpdatedAt
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Base implements Serializable {

  /**
   * @Id, JPA will recognize it as the object’s ID
   * JPA does not support multiple @Id
   * Composite primary key:
   *    use a component type to represent the identifier and map it as a property in the entity:
   *    you then annotated the property as @EmbeddedId. The component type has to be Serializable.
   *
   * @GeneratedValue, indicate that the ID should be generated automatically.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  protected Long id;

  /**
   * @Column can be used at the property level for properties that are:
   * - not annotated at all
   * - annotated with @Basic
   * - annotated with @Version
   * - annotated with @Lob
   * - annotated with @Temporal
   * - should be ok with @Id?
   *
   * Properties:
   * name (optional): the column name (default to the property name)
   * unique (optional): set a unique constraint on this column or not (default false)
   * nullable (optional): set the column as nullable (default true).
   * insertable (optional): whether or not the column will be part of the insert statement
   * (default true)
   * updatable (optional): whether or not the column will be part of the update statement
   * (default true)
   * columnDefinition (optional): override the sql DDL fragment for this particular column (non
   * portable)
   * table (optional): define the targeted table (default primary table)
   * length (optional): column length (default 255)
   * precision (optional): column decimal precision (default 0)
   * scale (optional): column decimal scale if useful (default 0)
   */
  @Version
  @Column(name = "version")
  protected Integer version;

  /**
   * In SQL Database, name "created_at" is preferred, instead of "createdAt", so we need to
   * define it manually.
   * In plain Java APIs, the temporal precision of time is not defined. When dealing with
   * temporal data you might
   * want to describe the expected precision in database. Temporal data can have DATE, TIME, or
   * TIMESTAMP precision
   * (ie the actual date, only the time, or both). Use the @Temporal annotation to fine tune that.
   * java.util.Date: year, month, day, hrs, min, sec
   * java.sql.Date: year, month, day (hour, minute, second and millisecond are ignored, isn't
   * tied to timezones)
   * java.sql.Time: hrs, min, sec (only contains information about hour, minutes, seconds and
   * milliseconds.)
   * java.sql.Timestamp: year, month, day, hrs, min, sec, nano sec (exact date to the nanosecond,
   * note that util.Date only supports milliseconds)
   */
  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "created_at", nullable = false)
  protected Date createdAt;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column(name = "updated_at", nullable = false)
  protected Date updatedAt;

  public Base() {
  }

  @PrePersist
  private void onPrePersist() {
    Date now = new Date();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  private void onPreUpdate() {
    this.updatedAt = new Date();
  }

  public Long getId() {
    return id;
  }

  public Integer getVersion() {
    return version;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }
}
