package com.currencyconverter.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="Converter")
public class Converter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NotNull(message = "Currency change shouldn't be null")
    @Column(name="CURRENCY_CHANGE")
    private String currencyChange;

    @NotNull(message = "Rate shouldn't be null")
    @Column(name = "RATE")
    private Double rate;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

}
