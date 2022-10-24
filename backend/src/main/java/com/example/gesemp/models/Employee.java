package com.example.gesemp.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private double salary;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 3)
    @Enumerated(EnumType.ORDINAL)
    private Genre genre;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private Type type;

    private String imgProfil;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startWork;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles= new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    public Employee(Long id, String firstName, String lastName, String email, double salary, String password, Genre genre, Type type, String imgProfil, Date birthDate, Date startWork) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.password = password;
        this.genre = genre;
        this.type = type;
        this.imgProfil = imgProfil;
        this.birthDate = birthDate;
        this.startWork = startWork;
    }
}

