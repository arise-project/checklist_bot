package Service;

import Domain.Everynote.ENote;
import Domain.Node;
import Domain.Root;
import Parser.Interface.IParagraphTextParser;
import Service.Interface.ITextService;
import com.google.inject.Inject;

import java.util.ArrayList;

public class TextService implements ITextService {

	private final IParagraphTextParser parser;

	@Inject
	public TextService(IParagraphTextParser parser){
		this.parser = parser;
	}

	@Override
	public void parseTextFile(Root root, String fileName) {
		ArrayList<Node> nodes = parser.parseTextFile(fileName);
		root.setNodes(nodes);
	}

	@Override
	public void parseENote(Root root, ENote note) {
		ArrayList<Node> nodes = parser.parseText(note.getContent());
		root.setNodes(nodes);
	}
}