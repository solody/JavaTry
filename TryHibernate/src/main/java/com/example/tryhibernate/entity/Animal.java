package com.example.tryhibernate.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVENT_DATE")
    private Date date;

    public Animal(String title, Date date) {
        this.title = title;
        this.date = date;
    }
}
