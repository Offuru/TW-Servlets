<%@ page import="com.example.servlets.entities.CourseEntity" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.example.servlets.entities.AppUserEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Enrollment</title>
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

        .action-buttons {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-bottom: 20px;
        }

        .action-buttons button {
            padding: 12px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        .action-buttons button:hover {
            background-color: #0056b3;
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
            flex: 0 0 auto;
        }

        .button-scroll button:hover {
            background-color: #0056b3;
        }

        .table-section {
            margin-top: 30px;
        }

        .table-section table {
            width: 100%;
            border-collapse: collapse;
        }

        .table-section th, .table-section td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        .table-section th {
            background-color: #f8f9fa;
            cursor: pointer;
        }

        .add-grade-btn {
            padding: 8px 15px;
            background-color: #28a745;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
        }

        .add-grade-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <!-- Profile Section -->
    <div class="profile-header">
        <h1>Courses Overview</h1>
        <p>Manage your courses and students.</p>
    </div>

    <!-- Add Grade and Add Course Buttons -->
    <div class="action-buttons">
        <form action="${pageContext.request.contextPath}/grade" method="get">
            <button type="submit">Add Grade</button>
        </form>
        <form action="${pageContext.request.contextPath}/course" method="get">
            <button type="submit">Add Course</button>
        </form>
    </div>

    <!-- Horizontal Scrollable Buttons Section -->
    <div class="button-scroll">
        <%
            Map<CourseEntity, Set<AppUserEntity>> usersByCourses =
                    (Map<CourseEntity, Set<AppUserEntity>>) session.getAttribute("usersByCourses");

            if (usersByCourses != null) {
                int i = 0;
                for (CourseEntity course : usersByCourses.keySet()) {
                    i++;
        %>
        <button onclick="showStudents(<%= i %>)"><%= course.getName() %></button>
        <%
                }
            }
        %>
    </div>

    <!-- Students Table Section -->
    <div class="table-section">
        <%
            int i = 0;
            if (usersByCourses != null) {
                for (CourseEntity course : usersByCourses.keySet()) {
                    i++;
                    Set<AppUserEntity> students = usersByCourses.get(course);
        %>
        <div id="students-<%= i %>" class="student-container" style="display: none;">
            <h3>Students Enrolled in <%= course.getName() %>: <%= usersByCourses.get(course).size() %></h3>
            <table>
                <thead>
                <tr>
                    <th onclick="sortTable(<%= i %>, 0)" data-sort-order="original">Name</th>
                    <th onclick="sortTable(<%= i %>, 1)" data-sort-order="original">Email</th>
                    <th>Action</th> <!-- New column for the action button -->
                </tr>
                </thead>
                <tbody>
                <% for (AppUserEntity student : students) { %>
                <tr>
                    <td><%= student.getUsername() %></td>
                    <td><%= student.getEmail() %></td>
                    <td>
                        <!-- Form for adding grade -->
                        <form action="${pageContext.request.contextPath}/grade" method="get">
                            <input type="hidden" name="studentId" value="<%= student.getId() %>">
                            <input type="hidden" name="courseId" value="<%= course.getId() %>">
                            <input type="submit" class="add-grade-btn" value="Add Grade">
                        </form>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <%
                }
            }
        %>
    </div>
</div>

<script>
    function showStudents(id) {
        // Hide all student sections
        document.querySelectorAll('.student-container').forEach(function(div) {
            div.style.display = 'none';
        });

        // Show the selected student section
        const studentSection = document.getElementById('students-' + id);
        if (studentSection) {
            studentSection.style.display = 'block';
        }
    }

    function sortTable(sectionId, columnIndex) {
        // Get the table and tbody for the specific section
        const table = document.getElementById('students-' + sectionId).querySelector('table');
        const tbody = table.querySelector('tbody');
        const rows = Array.from(tbody.rows);

        // Get the header cell that was clicked
        const headerCell = table.querySelector('th:nth-child(' + (columnIndex + 1) + ')');
        let currentOrder = headerCell.getAttribute('data-sort-order');
        let nextOrder = (currentOrder === 'asc') ? 'desc' : (currentOrder === 'desc') ? 'original' : 'asc';

        // Save the original order if it's not saved yet
        if (!table.originalOrder) {
            table.originalOrder = rows.map(row => row.cloneNode(true));
        }

        // Sort the rows
        rows.sort((rowA, rowB) => {
            const cellA = rowA.cells[columnIndex].innerText.trim().toLowerCase();
            const cellB = rowB.cells[columnIndex].innerText.trim().toLowerCase();

            if (!isNaN(cellA) && !isNaN(cellB)) {
                return nextOrder === 'asc' ? cellA - cellB : cellB - cellA;
            } else {
                return nextOrder === 'asc'
                    ? cellA.localeCompare(cellB)
                    : cellB.localeCompare(cellA);
            }
        });

        // Restore the original order or apply the sorted rows
        if (nextOrder === 'original') {
            tbody.innerHTML = '';
            table.originalOrder.forEach(row => tbody.appendChild(row.cloneNode(true)));
        } else {
            tbody.innerHTML = '';
            rows.forEach(row => tbody.appendChild(row));
        }

        // Update the sort order for the header
        headerCell.setAttribute('data-sort-order', nextOrder);
    }
</script>
</body>
</html>
