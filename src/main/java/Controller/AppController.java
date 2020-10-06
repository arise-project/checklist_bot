package Controller;

import Controller.Interface.IAppController;
import Repository.Interface.IStorageRepository;
import Service.Interface.ITextService;
import Domain.NodeAttribute;
import Domain.Note;
import Domain.Paragraph;
import Domain.Root;
import com.google.inject.Inject;

public class AppController implements IAppController {
	private final ITextService text;
	private final IStorageRepository storage;

	@Inject
	public AppController(ITextService text, IStorageRepository storage) {
		this.text = text;
		this.storage = storage;
	}

	@Override
	public void start(String[] args) {

		 if(args.length == 0)
		 {
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
		 while(argIndex < args.length)
		 {
		 		 switch(args[argIndex])
		 		 {
		 		 	case "read_text_file":
						System.out.println("read_text_file: " + args[argIndex+2] + " name: " + args[argIndex+1]);
						storage.getRoot().setName(args[argIndex+1]);
						text.parseTextFile(storage.getRoot(), args[argIndex+2]);
		 				argIndex+=2;
		 			break;
		 			case "statistics":
						System.out.println("statistics: " + storage.getStorageFile());
		 				if(storage.getRoot() != null) {
		 					System.out.println("Tree size: " + storage.getRoot().getSize());
		 				}
		 				else {
							System.out.println("Storage is EMPTY");
						}
		 			break;
		 			case "open_storage":
						System.out.println("open_storage: " + args[argIndex+1]);
		 				storage.open(args[argIndex+1]);
		 				argIndex++;
		 			break;
		 			case "save_storage":
						System.out.println("save_storage: " + args[argIndex+1]);
		 				if(storage.getRoot() != null) {
		 					storage.save(args[argIndex+1]);
		 				}
		 			case "set_battr":
						System.out.println("set_battr: " + args[argIndex+1]);
		 				if(storage.getRoot() != null) {
		 					NodeAttribute a = new NodeAttribute();
		 					a.setName(args[argIndex+1]);
		 					a.setBValue(true);
		 					storage.addAttribute(args[argIndex+1], a);
		 					argIndex++;
		 				}
		 			break;
		 			case "reset_attr":
						System.out.println("reset_attr: " + args[argIndex+1]);
		 				if(storage.getRoot() != null) {
		 					NodeAttribute a = new NodeAttribute();
		 					a.setName(args[argIndex+1]);
		 					a.setBValue(null);
		 					storage.addAttribute(args[argIndex+1], a);
		 					argIndex++;
		 				}
		 			break;
		 		 }
		 		 argIndex++;
		 }
	}
	
	@Override
	public void addRootParagraph() {
		Paragraph p;
	}

	@Override
	public void addRootNote(){
		Note note;
	}

	@Override
	public void addParagraph() {
		Paragraph r;
		Paragraph p;
	}
	
	@Override
	public void addNote(){
		Paragraph r;
		Note note;
	}
}