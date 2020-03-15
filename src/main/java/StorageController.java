import org.json.JSONObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StorageController {
	public Root Root = new Root();
	private TreeWalker walker = new TreeWalker();
	

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

	public void addAttribute(String nodeName, NodeAttribute attribute)
	{
		Node result = walker.search(this.Root, nodeName);
		if(result == null) {
			System.out.println(nodeName + "note found ");
			return;
		}

		for(NodeAttribute a : result.getAttributes()){
			if(a.getName() == attribute.getName()){
				a.setBValue(attribute.getBValue());
				System.out.println(nodeName + " updated attribute "+ attribute.getName() + " value "+ attribute.getBValue());
				return;
			}
		}

		System.out.println(nodeName + " added attribute "+ attribute.getName() + " value "+ attribute.getBValue());
		result.getAttributes().add(attribute);
	}
	
}