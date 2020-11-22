import Controller.Interface.IAppController;
import DI.BasicModule;
import Service.EverynoteService;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.thrift.TException;
import com.google.inject.*;

public class App {
	public static void main(String[] args) {
        try {
            var e = new EverynoteService();
            e.auth();
            e.listNotes();
            e.searchNotes();
        } catch (TException e) {
            e.printStackTrace();
        } catch (EDAMSystemException e) {
            e.printStackTrace();
        } catch (EDAMUserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Injector injector = Guice.createInjector(new BasicModule());
        IAppController controller = injector.getInstance(IAppController.class);
        controller.start(args);
    }
}
