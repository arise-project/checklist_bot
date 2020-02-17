public class AppController {
	public Root Storage;

	public void start(String[] args) {		
		if(args.length == 2) {
		 	switch(args[0])
		 	{
		 		case "text_file":
					parseTextFile(args[1]);
				break;
		 	}
		}
	}

	public void parseTextFile(String fileName){
		System.out.println("Checklist file:" + fileName);
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