import java.util.ArrayList;

public class TextController {

	public Root parseTextFile(String fileName) {
		System.out.println("Checklist file:" + fileName);

		ParagraphTextParser parser = new ParagraphTextParser();

		ArrayList<Node> nodes = parser.parseTextFile(fileName);

		Root root = new Root();
		root.Nodes = nodes;

		return root;
	}
}