public class TextController {

	public Root parseTextFile(String fileName) {
		System.out.println("Checklist file:" + fileName);

		return null;
	}

	public String GetNextParagraphName(Root root) {
		return "P"+String.valueOf(root.Size); 
	}
}