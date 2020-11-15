package Repository.Interface;

import Domain.NodeAttribute;
import Domain.Root;

public interface IStorageRepository {
    void save(String filePath, Root root);

    Root open(String filePath);

    void addAttribute(String nodeName, NodeAttribute attribute, Root root);

    String getStorageFile();
}
