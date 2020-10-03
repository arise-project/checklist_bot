package Controller.Interface;

import Domain.NodeAttribute;
import Domain.Root;

public interface IStorageController {
    void save(String filePath);

    Root open(String filePath);

    void addAttribute(String nodeName, NodeAttribute attribute);

    void setRoot(Root root);

    Root getRoot();

    String getStorageFile();
}
