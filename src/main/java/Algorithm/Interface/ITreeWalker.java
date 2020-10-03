package Algorithm.Interface;

import Domain.Node;
import Domain.Root;

public interface ITreeWalker {
    Node search(Root root, String nodeName);
}
