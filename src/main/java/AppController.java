public class AppController {

	
	public NavigationController Navigation = new NavigationController();
	public TextController Text = new TextController();
	public StorageController Storage = new StorageController();

	public void start(String[] args) {		
		 if(args.length == 0)
		 {
			System.out.println("Commands:");
			System.out.println();
			System.out.println("text_file [file_path] #read text file and parse it with split by paragraph.");
			System.out.println("statistics #show current storage statistics.");
			System.out.println("open [file_path] #read stroage from file.");
			System.out.println("save [file_path] #save stroage to file.");
			System.out.println();
			System.out.println("Commands works in sequence: command1 [paramater] command2 [paramater1] [paramater2] command3");
		 }
		 
		 int argIndex = 0;
		 while(argIndex < args.length)
		 {
		 		 switch(args[argIndex])
		 		 {
		 		 	case "text_file":
		 				Storage.Root = Text.parseTextFile(args[argIndex+1]);
		 				argIndex++;
		 			break;
		 			case "statistics":
		 				if(Storage.Root != null) {
		 					System.out.println("Tree size: " + Storage.Root.Size);
		 				}
		 			break;
		 			case "open":
		 				Storage.Root = Storage.open(args[argIndex+1]);
		 				argIndex++;
		 			break;
		 			case "save":
		 				if(Storage.Root != null) {
		 					Storage.save(args[argIndex+1]);
		 				}
		 			break;
		 		 }
		 		 argIndex++;
		 }
	}
	
	public void addRootParagraph() {
		Paragraph p;
	}

	public void addRootNote(){
		Note note;
	}

	public void addParagraph() {
		Paragraph r;
		Paragraph p;
	}
	
	public void addNote(){
		Paragraph r;
		Note note;
	}
}