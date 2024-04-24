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
    public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(Long id, String firstName, String lastName, double salary, String email, String password,
			Genre genre, Type type, String imgProfil, Date birthDate, Date startWork, Collection<AppRole> roles,
			Department department) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.email = email;
		this.password = password;
		this.genre = genre;
		this.type = type;
		this.imgProfil = imgProfil;
		this.birthDate = birthDate;
		this.startWork = startWork;
		this.roles = roles;
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getImgProfil() {
		return imgProfil;
	}

	public void setImgProfil(String imgProfil) {
		this.imgProfil = imgProfil;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getStartWork() {
		return startWork;
	}

	public void setStartWork(Date startWork) {
		this.startWork = startWork;
	}

	public Collection<AppRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<AppRole> roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

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

