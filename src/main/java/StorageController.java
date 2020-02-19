import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StorageController {
	public Root Root = new Root();

	public StorageController(){
		Root.setName("Post AI Book");
	}

	public void save(String filePath){
		if(Root == null)
		{
			System.out.println("Can not save empty storage to "+ filePath);
			return;
		}

		JSONObject storage = new JSONObject(Root);

		FileWriter writer = null;
		try
		{
			writer = new FileWriter(filePath);
			writer.write(storage.toString(4));	
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
	
	public Root open(String filePath){
		
		ObjectMapper mapper =  new ObjectMapper();

		try {
			File file = new File(filePath);	
			Root root = mapper.readValue(file, Root.class);
			this.Root = root;
			
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return this.Root;
	}
}