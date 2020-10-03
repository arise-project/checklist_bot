package Algorithm.Interface;

import Domain.Node;
import Domain.Root;

import java.util.Map;

public interface ITreeWalker {
    Node search(Root root, String nodeName);
    Map<Integer,Node> getInBreadth(Root root);
}
