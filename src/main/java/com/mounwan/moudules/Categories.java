package com.mounwan.moudules;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String titleOfWorkOut;

}
