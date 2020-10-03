package Controller;

import Controller.Interface.IAppController;
import Controller.Interface.IStorageController;
import Controller.Interface.ITextController;
import Domain.NodeAttribute;
import Domain.Note;
import Domain.Paragraph;
import com.google.inject.Inject;

public class AppController implements IAppController {
	private final ITextController text;
	private final IStorageController storage;

	@Inject
	public AppController(ITextController text, IStorageController storage) {
		this.text = text;
		this.storage = storage;
	}

	@Override
	public void start(String[] args) {
		 if(args.length == 0)
		 {
			System.out.println("Commands:");
			System.out.println();
			System.out.println("text_file [Name] [file_path] #read text file and parse it with split by paragraph.");
			System.out.println("statistics #show current storage statistics.");
			System.out.println("open [file_path] #read storage from file.");
			System.out.println("save [file_path] #save storage to file.");
			System.out.println("set_attr [node_name] #set named attribute to node as True.");
			System.out.println("reset_attr [node_name] #set named attribute to node as True.");
			System.out.println();
			System.out.println("Commands works in sequence: command1 [parameter] command2 [parameter1] [parameter2] command3");
		 }
		 
		 int argIndex = 0;
		 while(argIndex < args.length)
		 {
		 		 switch(args[argIndex])
		 		 {
		 		 	case "text_file":
		 				storage.setRoot(text.parseTextFile(args[argIndex+2]));
		 				storage.getRoot().setName(args[argIndex+1]);
		 				argIndex+=2;
		 			break;
		 			case "statistics":
		 				if(storage.getRoot() != null) {
		 					System.out.println("Tree size: " + storage.getRoot().getSize());
		 				}
		 			break;
		 			case "open":
		 				storage.setRoot(storage.open(args[argIndex+1]));
		 				argIndex++;
		 			break;
		 			case "save":
		 				if(storage.getRoot() != null) {
		 					storage.save(args[argIndex+1]);
		 				}
		 			case "set_attr":
		 				if(storage.getRoot() != null) {
		 					NodeAttribute a = new NodeAttribute();
		 					a.setName(args[argIndex+1]);
		 					a.setBValue(true);
		 					storage.addAttribute(args[argIndex+1], a);
		 					argIndex++;
		 				}
		 			break;
		 			case "reset_attr":
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