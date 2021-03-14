package Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Repository.Interface.IStorageRepository;
import Domain.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;

public class StorageRepository implements IStorageRepository {
	private String storageFile;

	@Inject
	public StorageRepository(){
	}

	@Override
	public void save(String filePath, Root root){
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
	public Root open(String filePath){
		ObjectMapper mapper =  new ObjectMapper();

		try {
			File file = new File(filePath);	
			Root root = mapper.readValue(file, Root.class);
			storageFile = filePath;
			return root;
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getFile(){
		if(storageFile == null){
			return "IN_MEMORY";
		}
		return storageFile;
	}
}