<%@ page import="com.example.servlets.entities.GradeEntity" %>
<%@ page import="com.example.servlets.entities.CourseEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.servlets.entities.AppUserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Page</title>
    <link rel="stylesheet" href="styles.css"> <!-- Optional external stylesheet -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #e9ecef;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .profile-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 800px;
        }

        .profile-header {
            text-align: center;
            margin-bottom: 20px;
        }

        .profile-header h1 {
            font-size: 28px;
            color: #343a40;
            margin: 10px 0;
        }

        .profile-header p {
            font-size: 18px;
            color: #6c757d;
            margin: 5px 0;
        }

        .button-scroll {
            display: flex;
            overflow-x: auto;
            gap: 15px;
            padding: 10px;
            margin-top: 20px;
            background: #f8f9fa;
            border-radius: 10px;
            box-shadow: inset 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .button-scroll button {
            padding: 12px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
            flex: 0 0 auto; /* Prevent buttons from shrinking */
        }

        .button-scroll button:hover {
            background-color: #0056b3;
        }

        .button-scroll::-webkit-scrollbar {
            height: 8px;
        }

        .button-scroll::-webkit-scrollbar-thumb {
            background-color: #ced4da;
            border-radius: 4px;
        }

        .button-scroll::-webkit-scrollbar-thumb:hover {
            background-color: #adb5bd;
        }

        .grades-section {
            margin-top: 30px;
        }

        .grades-section table {
            width: 100%;
            border-collapse: collapse;
        }

        .grades-section th, .grades-section td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        .grades-section th {
            background-color: #f8f9fa;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <!-- Profile Section -->
    <div class="profile-header">
        <h1>Profile</h1>
        <p>Username: ${sessionScope.currentUser.username}</p>
        <p>Email: ${sessionScope.currentUser.email}</p>
        <%
            String role = ((AppUserEntity) session.getAttribute("currentUser")).getRole().name();
            String capitalizedRole = role.substring(0, 1).toUpperCase() + role.substring(1).toLowerCase();
        %>
        <p>Role: <%= capitalizedRole %></p>
    </div>

    <!-- Horizontal Scrollable Buttons Section -->
    <div class="button-scroll">
        <%
            Map<CourseEntity, List<GradeEntity>> courseGradesMap =
                    (Map<CourseEntity, List<GradeEntity>>) session.getAttribute("grades");
            Map<CourseEntity, Double> finalGrades =
                    (Map<CourseEntity, Double>) session.getAttribute("finalGrades");

            if (courseGradesMap != null) {
                int i = 0;
                for (CourseEntity course : courseGradesMap.keySet()) {
                    i++;
        %>
        <button onclick="showGrades(<%= i %>)"><%= course.getName() %></button>
        <%
                }
            }
        %>
    </div>

    <!-- Grades Section -->
    <div class="grades-section">
        <%
            int i = 0;
            if (courseGradesMap != null) {
                for (CourseEntity course : courseGradesMap.keySet()) {
                    i++;
                    List<GradeEntity> grades = courseGradesMap.get(course);
                    Double finalGrade = finalGrades.get(course);
        %>
        <div id="grades-<%= i %>" class="grade-container" style="display: none;">
            <h3>Grades for <%= course.getName() %></h3>
            <table>
                <thead>
                <tr>
                    <th>Grade ID</th>
                    <th>Score</th>
                </tr>
                </thead>
                <tbody>
                <% for (GradeEntity grade : grades) { %>
                <tr>
                    <td><%= grade.getDateAssigned() %></td>
                    <td><%= grade.getScore() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <p><strong>Final Grade:</strong> <%= finalGrade != null ? finalGrade : "N/A" %></p>
        </div>
        <%
                }
            }
        %>
    </div>
</div>

<script>
    function showGrades(id) {
        // Hide all grade sections
        document.querySelectorAll('.grade-container').forEach(function(div) {
            div.style.display = 'none';
        });

        // Show the selected grade section
        const gradeSection = document.getElementById('grades-' + id);
        if (gradeSection) {
            gradeSection.style.display = 'block';
        }
    }
</script>
</body>
</html>
