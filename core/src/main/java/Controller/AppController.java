package Controller;

import Command.*;
import Command.Interface.IStorageCommand;
import Command.Interface.IStorageCommandBus;
import Controller.Interface.IAppController;
import Domain.Root;
import com.google.inject.Inject;

import java.util.Arrays;

public class AppController implements IAppController {
    private final IStorageCommandBus storageCommandBus;

    @Inject
    public AppController(IStorageCommandBus storageCommandBus) {
        this.storageCommandBus = storageCommandBus;
    }

    @Override
    public void start(String[] args) {

        if (args.length == 0) {
            System.out.println("Commands:");
            System.out.println();
            System.out.println("read_text_file [Name] [file_path] #read text file and parse it with split by paragraph.");
            System.out.println("statistics #show current storage statistics.");
            System.out.println("open_storage [file_path] #read storage from file.");
            System.out.println("save_storage [file_path] #save storage to file.");
            System.out.println("set_battr [node_name] #set named attribute to True.");
            System.out.println("reset_attr [node_name] #reset named attribute.");
            System.out.println("connect_everynote #connect with 'everynote' token env  variable");
            System.out.println("read_enote [note_name] #read note by name from connected everynote");
            System.out.println("list_enotebooks #list all notebooks from connected everynote");
            System.out.println("list_all_enotes #list all notes from connected everynote");
            System.out.println("everynote_production #enable everynote connect to production");
            System.out.println("list_enotes_for_notebook [notebook_name] #list notebook notes from connected everynote");
            System.out.println();
            System.out.println("Commands works in sequence: command1 [parameter] command2 [parameter1] [parameter2] command3");
            return;
        }

        Root root = new Root();
        int argIndex = 0;
        while (argIndex < args.length) {
            IStorageCommand command = null;
            String commandName = args[argIndex];
            switch (commandName) {
                case "read_text_file":
                    System.out.println(">>>>>>>>");
                    System.out.println("read_text_file: " + args[argIndex + 2] + " name: " + args[argIndex + 1]);
                    System.out.println(">>>>>>>>");
					command = new ReadTextFileCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 3));
                    argIndex += 2;
                    break;
                case "statistics":
                    System.out.println(">>>>>>>>");
                    System.out.println("statistics");
                    System.out.println(">>>>>>>>");
					command = new StatisticsCommand();
                    break;
                case "open_storage":
                    System.out.println(">>>>>>>>");
                    System.out.println("open_storage: " + args[argIndex + 1]);
                    System.out.println(">>>>>>>>");
					command = new OpenStorageCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
                    argIndex++;
                    break;
                case "save_storage":
                    System.out.println(">>>>>>>>");
                    System.out.println("save_storage: " + args[argIndex + 1]);
                    System.out.println(">>>>>>>>");
					command = new SaveStorageCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
					argIndex++;
					break;
                case "reset_attr":
                    System.out.println(">>>>>>>>");
                    System.out.println("reset_attr: " + args[argIndex + 1]);
                    System.out.println(">>>>>>>>");
					command = new ResetAttrCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
					argIndex++;
                    break;
                case "connect_everynote":
                    System.out.println(">>>>>>>>");
                    System.out.println("connect_everynote");
                    System.out.println(">>>>>>>>");
                    if(System.getenv("everynote") == null){
                        System.out.println("ERROR: set env variable everynote with auth token");
                    }
                    else {
                        command = new ConnectEverynoteCommand();
                    }
                    break;
                case "read_enote":
                    System.out.println(">>>>>>>>");
                    System.out.println("read_enote: name: " + args[argIndex + 1]);
                    System.out.println(">>>>>>>>");
                    command = new ReadENoteCommand();
                    command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
                    argIndex += 1;
                    break;
                case "list_enotebooks":
                    System.out.println(">>>>>>>>");
                    System.out.println("list_enotebooks");
                    System.out.println(">>>>>>>>");
                    command = new ListENotebooksCommand();
                    break;
                case "list_all_enotes":
                    System.out.println(">>>>>>>>");
                    System.out.println("list_all_enotes");
                    System.out.println(">>>>>>>>");
                    command = new ListAllENotesCommand();
                    break;
                case "everynote_production":
                    System.out.println(">>>>>>>>");
                    System.out.println("everynote_production");
                    System.out.println(">>>>>>>>");
                    command = new EverynoteProductionCommand();
                    break;
                case "list_enotes_for_notebook":
                    System.out.println(">>>>>>>>");
                    System.out.println("list_enotes_for_notebook: name: " + args[argIndex + 1]);
                    System.out.println(">>>>>>>>");
                    command = new ListENotesForNotebookCommand();
                    command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
                    argIndex += 1;
                    break;
            }
            argIndex++;

            if(command != null){
                command.setRoot(root);
				storageCommandBus.Dispatch(command);
				root = command.getRoot();
			}
            else {
				System.out.println(commandName + ": NOT FOUND");
			}
        }
    }
}