package Repository.Interface;

import Domain.NodeAttribute;
import Domain.Root;

public interface IStorageRepository {
    void save(String filePath);

    void open(String filePath);

    void addAttribute(String nodeName, NodeAttribute attribute);

    Root getRoot();

    String getStorageFile();
}
