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
                    studentFacade.create(s);
                    break;
                case "Read":
                    Student found = studentFacade.find(s.getId());
                    if (found != null) {
                        s = found;
                    }
                    break;
                case "Update":
                    studentFacade.edit(s);
                    break;
                case "Delete":
                    studentFacade.remove(s);
                    s = new Student(); // limpia los campos
                    break;
            }
        }

        // Asignar cursos seleccionados si se seleccionaron (después del Create o Update)
        String[] cursosSeleccionados = request.getParameterValues("cursos");
        if (cursosSeleccionados != null && s.getId() != null) {
            List<Curso> listaCursos = new ArrayList<>();
            for (String cod : cursosSeleccionados) {
                Curso c = cursoFacade.find(cod);
                if (c != null) {
                    listaCursos.add(c);
                }
            }
            s.setCursos(listaCursos);
            studentFacade.edit(s); // ACTUALIZA la relación cursos-estudiante
        }

        // Guardar atributos para mostrar en index.jsp
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
        return "Controlador de estudiantes con asignación de cursos";
    }
}