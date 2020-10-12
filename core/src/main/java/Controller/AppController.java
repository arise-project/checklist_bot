package Controller;

import Command.*;
import Command.Interface.IStorageCommand;
import Command.Interface.IStorageCommandBus;
import Controller.Interface.IAppController;
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
            System.out.println();
            System.out.println("Commands works in sequence: command1 [parameter] command2 [parameter1] [parameter2] command3");
        }

        int argIndex = 0;
        while (argIndex < args.length) {
            IStorageCommand command = null;
            String commandName = args[argIndex];
            switch (commandName) {
                case "read_text_file":
                    System.out.println("read_text_file: " + args[argIndex + 2] + " name: " + args[argIndex + 1]);
					command = new ReadTextFileCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 3));
                    argIndex += 2;
                    break;
                case "statistics":
					command = new StatisticsCommand();
                    break;
                case "open_storage":
                    System.out.println("open_storage: " + args[argIndex + 1]);
					command = new OpenStorageCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
                    argIndex++;
                    break;
                case "save_storage":
                    System.out.println("save_storage: " + args[argIndex + 1]);
					command = new SaveStorageCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
					argIndex++;
					break;
                case "set_battr":
                    System.out.println("set_battr: " + args[argIndex + 1]);
					command = new SetBAttrCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 3));
					argIndex+=2;
                    break;
                case "reset_attr":
                    System.out.println("reset_attr: " + args[argIndex + 1]);
					command = new ResetAttrCommand();
					command.setAttributes(Arrays.copyOfRange(args, argIndex + 1, argIndex + 2));
					argIndex++;
                    break;
            }
            argIndex++;

            if(command != null){
				storageCommandBus.Dispatch(command);
			}
            else {
				System.out.println(commandName + ": NOT FOUND");
			}
        }
    }
}