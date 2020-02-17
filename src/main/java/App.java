import java.io.IOException;

public class App {
	public static AppController Controller = new AppController();
	
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
		
        Controller.start(args);
    }
}
