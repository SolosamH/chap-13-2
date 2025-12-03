<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>User Admin</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    <h1>User Admin</h1>
    
    <h2>List of Users</h2>
    
    <table>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Action</th>
        </tr>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>
                <a href="users?action=display_user&amp;email=${user.email}">Update</a> |
                <a href="users?action=delete_user&amp;email=${user.email}" 
                   onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
            </td>
        </tr>
        </c:forEach>
    </table>
    
    <br>
    <p><a href="users?action=display_users">Refresh</a></p>
    
</body>
</html>
