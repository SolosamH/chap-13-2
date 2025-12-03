package murach.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {
    private static EntityManagerFactory emf;
    
    static {
        // Check if running on Render (has DATABASE_URL env var)
        String databaseUrl = System.getenv("DATABASE_URL");
        
        if (databaseUrl != null && databaseUrl.startsWith("postgres://")) {
            // Running on Render with PostgreSQL
            databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
            
            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", databaseUrl);
            properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
            properties.put("eclipselink.logging.level", "INFO");
            
            emf = Persistence.createEntityManagerFactory("userAdminPU", properties);
        } else {
            // Running locally with MySQL
            emf = Persistence.createEntityManagerFactory("userAdminPU");
        }
    }
    
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
