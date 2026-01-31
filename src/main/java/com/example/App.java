package main.java.com.example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    // Create Logger instance
    private static final Logger logger =
            LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        Calculator calc = new Calculator();

        // Use logger instead of System.out.println
        logger.info("Result: {}", 
                calc.calculate(10, 5, "add-again"));

        UserService service = new UserService();

        service.findUser("admin");

        // Dangerous operation (keep only if required)
        service.deleteUser("admin");
    }
}

