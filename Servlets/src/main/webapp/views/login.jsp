<%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 2024-12-14
  Time: 4:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
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

        .login-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 100%;
            max-width: 350px;
        }

        h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #343a40;
        }

        .form-group {
            margin-bottom: 20px;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #495057;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ced4da;
            border-radius: 5px;
            font-size: 16px;
            color: #495057;
            transition: border-color 0.2s;
        }

        input:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
        }

        button:hover {
            background-color: #0056b3;
        }

        p {
            margin-top: 15px;
            font-size: 14px;
            color: #6c757d;
        }

        a {
            color: #007bff;
            text-decoration: none;
            font-weight: 500;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Login</h2>
    <!-- Form action points to the HTTPS servlet -->
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" placeholder="Enter your email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>

        <div class="form-group">
            <button type="submit">Login</button>
        </div>
    </form>

    <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>.</p>
</div>
</body>
</html>


