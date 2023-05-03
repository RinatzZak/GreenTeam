package org.aston.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "currencies")
@EqualsAndHashCode
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numCode")
    private String numCode;

    @Column(name = "charCode")
    private String charCode;

    @Column(name = "nominal")
    private String nominal;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "dateOfCreation")
    private LocalDate dateOfCreation;

    public Currency(String numCode, String charCode, String nominal,
                    String name, String value, LocalDate dateOfCreation) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
        this.dateOfCreation = dateOfCreation;
    }
}
