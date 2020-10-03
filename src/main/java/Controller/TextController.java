package Controller;

import Domain.Node;
import Domain.Root;
import Parser.ParagraphTextParser;

import java.util.ArrayList;

public class TextController {

	public Root parseTextFile(String fileName) {
		System.out.println("Checklist file:" + fileName);

		ParagraphTextParser parser = new ParagraphTextParser();

		ArrayList<Node> nodes = parser.parseTextFile(fileName);

		Root root = new Root();
		root.setNodes(nodes);

		return root;
	}
}