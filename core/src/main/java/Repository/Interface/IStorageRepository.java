package Repository.Interface;

import Domain.Node;
import Domain.NodeAttribute;
import Domain.Root;

import java.util.Map;

public interface IStorageRepository {
    void save(String filePath);

    void open(String filePath);

    void addAttribute(String nodeName, NodeAttribute attribute);

    Root getRoot();

    String getStorageFile();

    Map<Integer, Node> getNodes();
}
