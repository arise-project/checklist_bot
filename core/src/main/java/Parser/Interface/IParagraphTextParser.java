package Parser.Interface;

import Domain.Node;

import java.util.ArrayList;

public interface IParagraphTextParser {
    ArrayList<Node> parseText(String content);

    ArrayList<Node> parseTextFile(String filePath);
}
