package Controller;

import Domain.Node;
import Domain.Root;
import Parser.Interface.IParagraphTextParser;
import com.google.inject.Inject;

import java.util.ArrayList;

public class TextController implements Controller.Interface.ITextController {

	private final IParagraphTextParser parser;

	@Inject
	public TextController(IParagraphTextParser parser){
		this.parser = parser;
	}

	@Override
	public Root parseTextFile(String fileName) {
		System.out.println("Checklist file:" + fileName);

		ArrayList<Node> nodes = parser.parseTextFile(fileName);

		Root root = new Root();
		root.setNodes(nodes);

		return root;
	}
}