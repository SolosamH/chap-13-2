<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>User Admin - Edit User</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css"/>
</head>
<body>
    <h1>User Admin</h1>
    
    <h2>Update User</h2>
    
    <form action="users" method="post">
        <input type="hidden" name="action" value="update_user">
        
        <label>Email:</label>
        <input type="email" name="email" value="${user.email}" readonly><br>
        
        <label>First Name:</label>
        <input type="text" name="firstName" value="${user.firstName}" required><br>
        
        <label>Last Name:</label>
        <input type="text" name="lastName" value="${user.lastName}" required><br>
        
        <label>&nbsp;</label>
        <input type="submit" value="Update User">
        <a href="users?action=display_users">Cancel</a>
    </form>
    
</body>
</html>
