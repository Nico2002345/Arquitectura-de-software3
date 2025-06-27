<%-- 
    Document   : curso
    Created on : 26/06/2025, 09:30:47 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Cursos</title>
</head>
<body>
    <h1>Formulario de Registro de Curso</h1>
    <form action="CursoServlet" method="post">
        <label>Código del Curso:</label><br>
        <input type="text" name="codigoCurso" required><br>

        <label>Nombre del Curso:</label><br>
        <input type="text" name="nombreCurso" required><br>

        <label>Número de Créditos:</label><br>
        <input type="number" name="creditos" required><br>

        <label>Semestre:</label><br>
        <input type="number" name="semestre" required><br>

        <label>Estudiantes Admitidos:</label><br>
        <input type="number" name="admitidos" required><br><br>

        <input type="submit" name="action" value="Create">
    </form>

    <hr>
    <h2>Eliminar un Curso</h2>
    <form action="CursoServlet" method="post">
        <label>Código del Curso:</label><br>
        <input type="text" name="codigoCurso" required><br><br>
        <input type="submit" name="action" value="Delete">
    </form>

    <hr>
    <h2>Lista de Cursos Existentes</h2>
    <table border="1">
        <tr>
            <th>Código</th>
            <th>Nombre</th>
            <th>Créditos</th>
            <th>Semestre</th>
            <th>Admitidos</th>
        </tr>
        <%
            List<co.edu.unipiloto.arquitecturas.entity.Curso> cursos = 
                (List<co.edu.unipiloto.arquitecturas.entity.Curso>) application.getAttribute("cursosDisponibles");
            if (cursos != null && !cursos.isEmpty()) {
                for (co.edu.unipiloto.arquitecturas.entity.Curso c : cursos) {
        %>
        <tr>
            <td><%= c.getCodigoCurso() %></td>
            <td><%= c.getNombreCurso() %></td>
            <td><%= c.getCreditos() %></td>
            <td><%= c.getSemestre() %></td>
            <td><%= c.getEstudiantesAdmitidos() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr><td colspan="5">No hay cursos registrados.</td></tr>
        <%
            }
        %>
    </table>
</body>
</html>