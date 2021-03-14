package Repository.Interface;

import Domain.Knowledge.KB;

public interface IFSKnowledgeRepository {
    void save(String filePath, KB root);

    KB open(String filePath);

    String getFile();
}
