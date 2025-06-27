<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*, co.edu.unipiloto.arquitecturas.entity.Student, co.edu.unipiloto.arquitecturas.entity.Curso"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Estudiantes y Cursos</title>
</head>
<body>
    <h1>Formulario Estudiante</h1>
    <form action="StudentServlet" method="post">
        ID: <input type="text" name="studentID"><br>
        Nombre: <input type="text" name="firstName"><br>
        Apellido: <input type="text" name="lastName"><br>
        Año: <input type="text" name="yearLevel"><br><br>

        <h3>Cursos disponibles:</h3>
        <%
            List<Curso> cursos = (List<Curso>) application.getAttribute("cursosDisponibles");
            if (cursos != null && !cursos.isEmpty()) {
                for (Curso c : cursos) {
        %>
            <input type="checkbox" name="cursos" value="<%= c.getCodigoCurso() %>">
            <%= c.getNombreCurso() %> (<%= c.getCodigoCurso() %>)<br>
        <%
                }
            } else {
        %>
            <p><em>No hay cursos disponibles.</em></p>
        <%
            }
        %>

        <br>
        <input type="submit" name="action" value="Create">
        <input type="submit" name="action" value="Read">
        <input type="submit" name="action" value="Update">
        <input type="submit" name="action" value="Delete">
    </form>

    <hr>

    <h2>Crear nuevo curso</h2>
    <form action="CursoServlet" method="post">
        Código del curso: <input type="text" name="codigoCurso"><br>
        Nombre del curso: <input type="text" name="nombreCurso"><br>
        Créditos: <input type="number" name="creditos"><br>
        Semestre: <input type="number" name="semestre"><br>
        Estudiantes admitidos: <input type="number" name="admitidos"><br>
        <input type="submit" name="action" value="Create">
    </form>

    <hr>

    <h2>Eliminar curso existente</h2>
    <form action="CursoServlet" method="post">
        Código del curso a eliminar: <input type="text" name="codigoCurso"><br>
        <input type="submit" name="action" value="Delete">
    </form>

    <hr>

    <h2>Listado de estudiantes</h2>
    <%
        List<Student> allStudents = (List<Student>) request.getAttribute("allStudents");
        if (allStudents != null && !allStudents.isEmpty()) {
    %>
        <table border="1">
            <tr>
                <th>ID</th><th>Nombre</th><th>Apellido</th><th>Año</th><th>Cursos</th>
            </tr>
            <%
                for (Student st : allStudents) {
            %>
                <tr>
                    <td><%= st.getId() %></td>
                    <td><%= st.getFirstName() %></td>
                    <td><%= st.getLastName() %></td>
                    <td><%= st.getYearLevel() %></td>
                    <td>
                        <%
                            List<Curso> cursosEst = st.getCursos();
                            if (cursosEst != null) {
                                for (Curso c : cursosEst) {
                                    out.print(c.getNombreCurso() + "<br>");
                                }
                            }
                        %>
                    </td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        } else {
    %>
        <p><em>No hay estudiantes registrados aún.</em></p>
    <%
        }
    %>
</body>
</html>
