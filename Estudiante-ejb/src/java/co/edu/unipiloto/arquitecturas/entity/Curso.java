/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unipiloto.arquitecturas.entity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "CURSO")
public class Curso implements Serializable {

    @Id
    @Column(name = "CODIGO_CURSO")
    private String codigoCurso;

    @Column(name = "NOMBRE_CURSO")
    private String nombreCurso;

    @Column(name = "CREDITOS")
    private int creditos;

    @Column(name = "SEMESTRE")
    private int semestre;

    @Column(name = "ESTUDIANTES_ADMITIDOS")
    private int estudiantesAdmitidos;

    @ManyToMany(mappedBy = "cursos")
    private List<Student> estudiantes;

    // Getters y setters
    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getEstudiantesAdmitidos() {
        return estudiantesAdmitidos;
    }

    public void setEstudiantesAdmitidos(int estudiantesAdmitidos) {
        this.estudiantesAdmitidos = estudiantesAdmitidos;
    }

    public List<Student> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Student> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
