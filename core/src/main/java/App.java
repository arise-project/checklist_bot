import Controller.Interface.IAppController;
import DI.BasicModule;
import Service.EverynoteService;
import com.google.inject.*;

public class App {
	public static void main(String[] args) {
        var e = new EverynoteService();
        if(e.auth()){
            e.searchNotes("test");
        }

        Injector injector = Guice.createInjector(new BasicModule());
        IAppController controller = injector.getInstance(IAppController.class);
        controller.start(args);
    }
}
