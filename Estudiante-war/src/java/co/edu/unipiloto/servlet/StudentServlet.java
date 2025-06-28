/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package co.edu.unipiloto.servlet;

import co.edu.unipiloto.arquitecturas.entity.Curso;
import co.edu.unipiloto.arquitecturas.entity.Student;
import co.edu.unipiloto.arquitecturas.session.CursoFacadeLocal;
import co.edu.unipiloto.arquitecturas.session.StudentFacadeLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentServlet extends HttpServlet {

    @EJB
    private StudentFacadeLocal studentFacade;

    @EJB
    private CursoFacadeLocal cursoFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("studentID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String yearStr = request.getParameter("yearLevel");

        Student s = new Student();
        try {
            if (idStr != null && !idStr.isEmpty()) {
                s.setId(Integer.parseInt(idStr));
            }
            s.setFirstName(firstName);
            s.setLastName(lastName);
            if (yearStr != null && !yearStr.isEmpty()) {
                s.setYearLevel(Integer.parseInt(yearStr));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String operation = request.getParameter("action");
        if (operation != null) {
            switch (operation) {
                case "Create":
                    List<Curso> cursosParaCrear = new ArrayList<>();
                    String[] cursosSelCrear = request.getParameterValues("cursos");
                    boolean cuposDisponibles = true;
                    StringBuilder cursosLlenos = new StringBuilder();
                    if (cursosSelCrear != null) {
                        for (String cod : cursosSelCrear) {
                            Curso curso = cursoFacade.find(cod);
                            if (curso != null) {
                                int inscritos = 0;
                                for (Student est : studentFacade.findAll()) {
                                    if (est.getCursos() != null && est.getCursos().contains(curso)) {
                                        inscritos++;
                                    }
                                }
                                if (inscritos < curso.getEstudiantesAdmitidos()) {
                                    cursosParaCrear.add(curso);
                                } else {
                                    cuposDisponibles = false;
                                    cursosLlenos.append(curso.getNombreCurso()).append(", ");
                                }
                            }
                        }
                    }
                    if (cuposDisponibles) {
                        s.setCursos(cursosParaCrear);
                        studentFacade.create(s);
                        request.setAttribute("mensaje", "Estudiante creado con éxito.");
                    } else {
                        String mensaje = "No hay cupos para los siguientes cursos: " + cursosLlenos.toString().replaceAll(", $", "");
                        request.setAttribute("mensaje", mensaje);
                    }
                    break;
                case "Read":
                    Student found = studentFacade.find(s.getId());
                    if (found != null) {
                        s = found;
                    } else {
                        request.setAttribute("mensaje", "Estudiante no encontrado.");
                    }
                    break;
                case "Update":
                    Student estudianteExistente = studentFacade.find(s.getId());
                    if (estudianteExistente == null) {
                        request.setAttribute("mensaje", "No se encontró el estudiante para actualizar.");
                        break;
                    }
                    // Solo actualiza campos si vienen del formulario
                    if (firstName != null && !firstName.isEmpty()) {
                        estudianteExistente.setFirstName(firstName);
                    }
                    if (lastName != null && !lastName.isEmpty()) {
                        estudianteExistente.setLastName(lastName);
                    }
                    if (yearStr != null && !yearStr.isEmpty()) {
                        try {
                            estudianteExistente.setYearLevel(Integer.parseInt(yearStr));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    List<Curso> cursosParaUpdate = new ArrayList<>();
                    String[] cursosSelUpdate = request.getParameterValues("cursos");
                    boolean cuposValidos = true;
                    StringBuilder cursosLlenosUpdate = new StringBuilder();
                    if (cursosSelUpdate != null) {
                        for (String cod : cursosSelUpdate) {
                            Curso curso = cursoFacade.find(cod);
                            if (curso != null) {
                                int inscritos = 0;
                                for (Student est : studentFacade.findAll()) {
                                    if (est.getId().equals(estudianteExistente.getId())) continue;
                                    if (est.getCursos() != null && est.getCursos().contains(curso)) {
                                        inscritos++;
                                    }
                                }
                                if (inscritos < curso.getEstudiantesAdmitidos()) {
                                    cursosParaUpdate.add(curso);
                                } else {
                                    cuposValidos = false;
                                    cursosLlenosUpdate.append(curso.getNombreCurso()).append(", ");
                                }
                            }
                        }
                        if (cuposValidos) {
                            estudianteExistente.setCursos(cursosParaUpdate);
                        } else {
                            String mensaje = "No se pudo actualizar. Sin cupos en: " + cursosLlenosUpdate.toString().replaceAll(", $", "");
                            request.setAttribute("mensaje", mensaje);
                            break;
                        }
                    }
                    studentFacade.edit(estudianteExistente);
                    s = estudianteExistente;
                    request.setAttribute("mensaje", "Estudiante actualizado con éxito.");
                    break;
                case "Delete":
                    Student estudianteAEliminar = studentFacade.find(s.getId());
                    if (estudianteAEliminar != null) {
                        estudianteAEliminar.setCursos(null); // rompe la relación con cursos
                        studentFacade.edit(estudianteAEliminar); // guarda sin cursos
                        studentFacade.remove(estudianteAEliminar); // elimina el estudiante
                        s = new Student(); // limpia el objeto para JSP
                        request.setAttribute("mensaje", "Estudiante eliminado correctamente.");
                    } else {
                        request.setAttribute("mensaje", "No se encontró el estudiante para eliminar.");
                    }
                    break;
            }
        }

        // Enviar datos al JSP
        request.setAttribute("student", s);
        request.setAttribute("allStudents", studentFacade.findAll());
        request.setAttribute("cursosDisponibles", cursoFacade.findAll());

        // Redirigir al JSP
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador de estudiantes con asignación de cursos y control de cupos";
    }
}
