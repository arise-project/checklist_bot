package Repository;

import Domain.Knowledge.KB;
import Domain.Root;
import Repository.Interface.IFSKnowledgeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FsKnowledgeRepository implements IFSKnowledgeRepository {
    private String storageFile;

    @Override
    public void save(String filePath, KB root) {
        if(root == null)
        {
            System.out.println("Can not save empty storage to "+ filePath);
            return;
        }

        ObjectMapper mapper =  new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        FileWriter writer = null;
        try
        {
            String output = mapper.writeValueAsString(root);

            writer = new FileWriter(filePath);
            writer.write(output);
            storageFile = filePath;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try
            {
                if(writer != null){
                    writer.flush();
                    writer.close();
                }
            }
            catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }

    @Override
    public KB open(String filePath) {
        ObjectMapper mapper =  new ObjectMapper();

        try {
            File file = new File(filePath);
            KB root = mapper.readValue(file, KB.class);
            storageFile = filePath;
            return root;
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getFile() {
        if(storageFile == null){
            return "IN_MEMORY";
        }
        return storageFile;
    }
}
