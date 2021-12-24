package Repository.Interface;

import Domain.Root;

public interface IStorageRepository {
    void save(String filePath, Root root);

    Root open(String filePath);

    String getFile();
}
