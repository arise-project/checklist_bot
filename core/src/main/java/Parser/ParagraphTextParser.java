package Parser;

import Domain.Node;
import Domain.Note;
import Parser.Interface.IParagraphTextParser;

import java.io.*;
import java.util.ArrayList;

public class ParagraphTextParser implements IParagraphTextParser {
	@Override
	public ArrayList<Node> parseText(String content){
		BufferedReader reader = new BufferedReader(new StringReader(content));
		return parse(reader);
	}

	@Override
	public ArrayList<Node> parseTextFile(String filePath){
		File file = new File(filePath);
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(file));
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			return new ArrayList<>();
		}

		return parse(reader);
	}

	private ArrayList<Node> parse(BufferedReader reader){
		ArrayList<Node> notes = new ArrayList<>();

		String st = null;
		int count = 0;
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

			if(st != null){
				if(st.trim().length() == 0)
				{
					if(count > 0)
					{
						node.setText(sb.toString());
						node.setName("NOTE_"+ node.hashCode());
						notes.add(node);
						node = new Note();
						sb = new StringBuilder();
						count = 0;
					}
				}
				else {
					sb.append(st);
					count++;
				}
			}
		}
		while(st != null);

		return notes;
	}
}