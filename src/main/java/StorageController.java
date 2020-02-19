import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

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
		return null;
	}
}