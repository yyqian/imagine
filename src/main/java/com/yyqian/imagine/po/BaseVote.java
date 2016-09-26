package com.yyqian.imagine.po;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by yyqian on 12/12/15.
 * The base type for Vote
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BaseVote implements Serializable {

  @Column(name = "score", nullable = false)
  protected int score;

  public BaseVote() {
  }

  public BaseVote(int score) {
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public abstract User getUser();

}
