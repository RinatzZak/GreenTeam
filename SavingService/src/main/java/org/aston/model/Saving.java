package org.aston.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "saving")
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "inRubles")
    private Double inRubles;

    @Column(name = "inDollars")
    private Double inDollars;

    @Column(name = "inEuro")
    private Double inEuro;

    public Saving(Integer userId, Double inRubles, Double inDollars, Double inEuro) {
        this.userId = userId;
        this.inRubles = inRubles;
        this.inDollars = inDollars;
        this.inEuro = inEuro;
    }
}
