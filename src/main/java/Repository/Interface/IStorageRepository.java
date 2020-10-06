package Repository.Interface;

import Domain.NodeAttribute;
import Domain.Root;

public interface IStorageService {
    void save(String filePath);

    Root open(String filePath);

    void addAttribute(String nodeName, NodeAttribute attribute);

    void setRoot(Root root);

    Root getRoot();

    String getStorageFile();
}
