package murach.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {
    private static EntityManagerFactory emf;
    
    static {
        try {
            // Check if running on Render (has DATABASE_URL env var)
            String databaseUrl = System.getenv("DATABASE_URL");
            System.out.println("[DBUtil] Checking DATABASE_URL...");
            System.out.println("[DBUtil] DATABASE_URL exists: " + (databaseUrl != null));
            
            if (databaseUrl != null && !databaseUrl.isEmpty()) {
                System.out.println("[DBUtil] Using PostgreSQL from DATABASE_URL");
                // Running on Render with PostgreSQL
                // Handle both postgres:// and postgresql://
                if (databaseUrl.startsWith("postgres://")) {
                    databaseUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://");
                } else if (databaseUrl.startsWith("postgresql://")) {
                    databaseUrl = "jdbc:" + databaseUrl;
                }
                
                Map<String, String> properties = new HashMap<>();
                properties.put("javax.persistence.jdbc.url", databaseUrl);
                properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
                properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
                properties.put("eclipselink.logging.level", "INFO");
                
                System.out.println("[DBUtil] Connecting to: " + databaseUrl.replaceAll(":[^:@]+@", ":***@"));
                emf = Persistence.createEntityManagerFactory("userAdminPU", properties);
                System.out.println("[DBUtil] PostgreSQL connection successful!");
            } else {
                System.out.println("[DBUtil] Using local MySQL from persistence.xml");
                // Running locally with MySQL
                emf = Persistence.createEntityManagerFactory("userAdminPU");
                System.out.println("[DBUtil] MySQL connection successful!");
            }
        } catch (Exception e) {
            System.err.println("[DBUtil] ERROR: Failed to initialize database connection");
            e.printStackTrace();
            throw e;
        }
    }
    
    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}
