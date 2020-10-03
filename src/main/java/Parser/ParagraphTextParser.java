import Domain.Node;
import Domain.Note;

import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ParagraphTextParser {

	public ArrayList<Node> parseTextFile(String filePath){
		ArrayList<Node> notes = new ArrayList<>();

		File file = new File(filePath);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}

		String st = null;
		int count = 0;
		int index = 0;
		Note node = new Note();
		StringBuilder sb = new StringBuilder();
		do{
			try
			{
				st = reader.readLine();
			}
			catch(IOException e){
				e.printStackTrace();
			}
			
			if(st == null){
				break;
			}
			
			if(st.trim().length() == 0)
			{
				if(count > 0)
				{
					node.setName(getNextParagraphName(index));
					node.setText(sb.toString());
					notes.add(node);
					node = new Note();
					sb = new StringBuilder();
					count = 0;
					index++;				
				}
			}
			else {
				sb.append(st);
				count++;
			}
		}
		while(st != null);

		return notes;
	}

	public String getNextParagraphName(int index) {
		return "P"+String.valueOf(index); 
	}
}