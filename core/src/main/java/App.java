import Controller.Interface.IAppController;
import DI.BasicModule;
import com.google.inject.*;

public class App {
	public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        IAppController controller = injector.getInstance(IAppController.class);
        controller.start(args);
    }
}
