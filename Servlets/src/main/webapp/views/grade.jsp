<%@ page import="com.example.servlets.entities.CourseEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.servlets.entities.AppUserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assign Grade</title>
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
            max-width: 900px;
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

        .form-container {
            margin-top: 30px;
        }

        .form-container form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .form-container label {
            font-size: 16px;
            font-weight: 600;
            color: #343a40;
        }

        .form-container select, .form-container input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            width: 100%;
        }

        .form-container input[type="submit"] {
            background-color: #28a745;
            color: white;
            border: none;
            font-size: 16px;
            cursor: pointer;
            font-weight: 600;
        }

        .form-container input[type="submit"]:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <!-- Profile Section -->
    <div class="profile-header">
        <h1>Assign Grade</h1>
        <p>Select a student, a course, and enter the grade.</p>
    </div>

    <!-- Form Section -->
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/grade" method="POST">
            <label for="student">Student</label>
            <select name="student" id="student">
                <%
                    List<AppUserEntity> allStudents = (List<AppUserEntity>) session.getAttribute("allStudents");
                    AppUserEntity selectedStudent = (AppUserEntity) session.getAttribute("student");
                    if (allStudents != null) {
                        for (AppUserEntity student : allStudents) {
                            // Check if this student is the selected one
                            String selected = (selectedStudent != null && selectedStudent.getUsername().equals(student.getUsername())) ? "selected" : "";
                %>
                <option value="<%= student.getId() %>" <%= selected %> ><%= student.getUsername() %> - <%= student.getEmail() %></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="course">Course</label>
            <select name="course" id="course">
                <%
                    List<CourseEntity> availableCourses = (List<CourseEntity>) session.getAttribute("availableCourses");
                    CourseEntity selectedCourse = (CourseEntity) session.getAttribute("course");
                    if (availableCourses != null) {
                        for (CourseEntity course : availableCourses) {
                            // Check if this course is the selected one
                            String selected = (selectedCourse != null && selectedCourse.getName().equals(course.getName())) ? "selected" : "";
                %>
                <option value="<%= course.getId() %>" <%= selected %> ><%= course.getName() %></option>
                <%
                        }
                    }
                %>
            </select>

            <label for="score">Score</label>
            <input type="number" name="score" id="score" min="0" max="100" required>

            <input type="submit" value="Assign Grade">
        </form>
    </div>
</div>
</body>
</html>
