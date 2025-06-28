package co.edu.unipiloto.servlet;
import co.edu.unipiloto.arquitecturas.entity.Curso;
import co.edu.unipiloto.arquitecturas.session.CursoFacadeLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.*;

public class CursoServlet extends HttpServlet {

    @EJB
    private CursoFacadeLocal cursoFacade;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String codigo = request.getParameter("codigoCurso");

        if ("Create".equals(action)) {
            if (cursoFacade.find(codigo) == null) {
                String nombre = request.getParameter("nombreCurso");
                int creditos = Integer.parseInt(request.getParameter("creditos"));
                int semestre = Integer.parseInt(request.getParameter("semestre"));
                int admitidos = Integer.parseInt(request.getParameter("admitidos"));

                Curso curso = new Curso();
                curso.setCodigoCurso(codigo);
                curso.setNombreCurso(nombre);
                curso.setCreditos(creditos);
                curso.setSemestre(semestre);
                curso.setEstudiantesAdmitidos(admitidos);

                cursoFacade.create(curso);
            }

        } else if ("Delete".equals(action)) {
            Curso existente = cursoFacade.find(codigo);
            if (existente != null) {
                cursoFacade.remove(existente);
            }
        }
        getServletContext().setAttribute("cursosDisponibles", cursoFacade.findAll());
        response.sendRedirect("index.jsp");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }
}
