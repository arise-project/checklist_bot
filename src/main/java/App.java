import com.google.inject.*;

public class App {
	public static AppController Controller = new AppController();

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        Controller.start(args);
    }
}
