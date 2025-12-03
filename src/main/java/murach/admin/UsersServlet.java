package murach.admin;

import murach.business.User;
import murach.data.UserDB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        String url = "/index.jsp";
        
        if (action == null) {
            action = "display_users";
        }
        
        if (action.equals("display_users")) {
            // Get all users from database
            List<User> users = UserDB.selectUsers();
            request.setAttribute("users", users);
            url = "/index.jsp";
        } 
        else if (action.equals("display_user")) {
            // Get user by email
            String email = request.getParameter("email");
            User user = UserDB.selectUser(email);
            request.setAttribute("user", user);
            url = "/user.jsp";
        }
        else if (action.equals("update_user")) {
            // Update user
            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            
            User user = UserDB.selectUser(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            UserDB.update(user);
            
            // Get updated list of users
            List<User> users = UserDB.selectUsers();
            request.setAttribute("users", users);
            url = "/index.jsp";
        }
        else if (action.equals("delete_user")) {
            // Delete user
            String email = request.getParameter("email");
            User user = UserDB.selectUser(email);
            UserDB.delete(user);
            
            // Get updated list of users
            List<User> users = UserDB.selectUsers();
            request.setAttribute("users", users);
            url = "/index.jsp";
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
