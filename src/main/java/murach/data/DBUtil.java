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
                // Render format: postgres://user:pass@host:port/dbname
                // JDBC needs: jdbc:postgresql://host:port/dbname?user=user&password=pass
                
                if (databaseUrl.startsWith("postgres://")) {
                    // Parse the URL manually
                    String withoutProtocol = databaseUrl.substring("postgres://".length());
                    // Format: user:pass@host:port/dbname
                    
                    int atIndex = withoutProtocol.indexOf('@');
                    String credentials = withoutProtocol.substring(0, atIndex);
                    String hostAndDb = withoutProtocol.substring(atIndex + 1);
                    
                    int colonIndex = credentials.indexOf(':');
                    String user = credentials.substring(0, colonIndex);
                    String password = credentials.substring(colonIndex + 1);
                    
                    // Build JDBC URL
                    databaseUrl = "jdbc:postgresql://" + hostAndDb;
                    if (!databaseUrl.contains("?")) {
                        databaseUrl += "?";
                    } else {
                        databaseUrl += "&";
                    }
                    databaseUrl += "user=" + user + "&password=" + password + "&sslmode=require";
                    
                    Map<String, String> properties = new HashMap<>();
                    properties.put("javax.persistence.jdbc.url", databaseUrl);
                    properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
                    properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
                    properties.put("eclipselink.logging.level", "INFO");
                    
                    System.out.println("[DBUtil] Connecting to PostgreSQL...");
                    emf = Persistence.createEntityManagerFactory("userAdminPU", properties);
                    System.out.println("[DBUtil] PostgreSQL connection successful!");
                } else {
                    // Already in JDBC format
                    Map<String, String> properties = new HashMap<>();
                    properties.put("javax.persistence.jdbc.url", databaseUrl);
                    properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
                    properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
                    properties.put("eclipselink.logging.level", "INFO");
                    
                    emf = Persistence.createEntityManagerFactory("userAdminPU", properties);
                }
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
