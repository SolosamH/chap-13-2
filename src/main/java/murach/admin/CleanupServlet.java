package murach.admin;

import murach.business.User;
import murach.data.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/cleanup")
public class CleanupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h1>Cleanup Duplicate Emails</h1>");
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            // Get all users
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u ORDER BY u.email, u.userId", User.class);
            List<User> allUsers = query.getResultList();
            
            // Find duplicates
            Map<String, List<User>> emailMap = new HashMap<>();
            for (User user : allUsers) {
                String email = user.getEmail();
                emailMap.putIfAbsent(email, new ArrayList<>());
                emailMap.get(email).add(user);
            }
            
            // Delete duplicates (keep first one)
            EntityTransaction trans = em.getTransaction();
            trans.begin();
            
            int deletedCount = 0;
            for (Map.Entry<String, List<User>> entry : emailMap.entrySet()) {
                List<User> users = entry.getValue();
                if (users.size() > 1) {
                    out.println("<p>Found " + users.size() + " users with email: " + entry.getKey() + "</p>");
                    // Keep first, delete others
                    for (int i = 1; i < users.size(); i++) {
                        User toDelete = users.get(i);
                        em.remove(em.merge(toDelete));
                        deletedCount++;
                        out.println("<p>Deleted user: " + toDelete.getFirstName() + " " + toDelete.getLastName() + " (ID: " + toDelete.getUserId() + ")</p>");
                    }
                }
            }
            
            trans.commit();
            
            out.println("<h2>Cleanup Complete!</h2>");
            out.println("<p>Deleted " + deletedCount + " duplicate users.</p>");
            out.println("<p><a href='users'>Go to User Admin</a></p>");
            
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        out.println("</body></html>");
    }
}
