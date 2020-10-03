package Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Algorithm.Interface.ITreeWalker;
import Controller.Interface.IStorageController;
import Domain.Node;
import Domain.NodeAttribute;
import Domain.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Inject;

public class StorageController implements IStorageController {
	private Domain.Root root = new Root();
	Map<Integer,Node> dictionary = new HashMap<>();

	private final ITreeWalker walker;
	private String storageFile;

	@Inject
	public StorageController(ITreeWalker walker){
		this.walker = walker;
		root.setName("Post AI Book");
	}

	@Override
	public void save(String filePath){
		if(root == null)
		{
			System.out.println("Can not save empty storage to "+ filePath);
			return;
		}

		ObjectMapper mapper =  new ObjectMapper();
		mapper.enableDefaultTyping();
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
			this.root = root;
			storageFile = filePath;
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return this.root;
	}

	@Override
	public void addAttribute(String nodeName, NodeAttribute attribute)  {
		Node result = walker.search(this.root, nodeName);
		if(result == null) {
			System.out.println(nodeName + " not found ");
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
		storageFile = null;
	}

	@Override
	public void setRoot(Root root){
		this.root = root;
		this.dictionary = walker.getInBreadth(root);
	}

	@Override
	public Root getRoot(){
		return this.root;
	}

	@Override
	public String getStorageFile(){
		if(storageFile == null){
			return "IN_MEMORY";
		}
		return storageFile;
	}
}