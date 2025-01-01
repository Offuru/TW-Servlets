<%--
  Created by IntelliJ IDEA.
  User: Andrei
  Date: 2024-12-14
  Time: 4:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
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

        .home-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 100%;
            max-width: 600px;
        }

        h1 {
            font-size: 32px;
            color: #343a40;
            margin-bottom: 20px;
        }

        p {
            font-size: 18px;
            color: #6c757d;
            margin-bottom: 30px;
        }

        .btn-group {
            display: flex;
            justify-content: center;
            gap: 15px;
        }

        button {
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

        button:hover {
            background-color: #0056b3;
        }

        a {
            text-decoration: none;
            color: #fff;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="home-container">
    <h1>Welcome, ${sessionScope.currentUser.id}</h1>
    <h1>Welcome, ${sessionScope.currentUser.username}</h1>
    <h1>Welcome, ${sessionScope.currentUser.email}</h1>
    <h1>Welcome, ${sessionScope.currentUser.role.name()}</h1>
    <h1>Welcome, ${sessionScope.currentUser.password}</h1>
</div>
</body>
</html>

