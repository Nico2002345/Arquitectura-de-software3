/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.unipiloto.arquitecturas.entity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "STUDENT")
@XmlRootElement
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;

    @Size(max = 50)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Size(max = 50)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "YEAR_LEVEL")
    private Integer yearLevel;

    @ManyToMany
    @JoinTable(
        name = "STUDENT_CURSO",
        joinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "CURSO_ID", referencedColumnName = "CODIGO_CURSO")
    )
    private List<Curso> cursos;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public Integer getYearLevel() { return yearLevel; }
    public void setYearLevel(Integer yearLevel) { this.yearLevel = yearLevel; }
    public List<Curso> getCursos() { return cursos; }
    public void setCursos(List<Curso> cursos) { this.cursos = cursos; }
}


