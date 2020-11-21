import Controller.Interface.IAppController;
import DI.BasicModule;
import Service.DriveService;
import com.google.inject.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class App {
	public static void main(String[] args) {
        try {
            var ar = new DriveService().search("reco");
            var c = new DriveService().read(ar.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        Injector injector = Guice.createInjector(new BasicModule());
        IAppController controller = injector.getInstance(IAppController.class);
        controller.start(args);
    }
}
