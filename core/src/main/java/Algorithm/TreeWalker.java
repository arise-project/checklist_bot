package Algorithm;

import Algorithm.Interface.ITreeWalker;
import Domain.Node;
import Domain.Paragraph;
import Domain.Root;

import java.util.*;

public class TreeWalker implements ITreeWalker {

	@Override
	public Node search(Root root, String nodeName){
		Queue<Node> nodes = new LinkedList<>();
		nodes.add(root);
		while(nodes.size() > 0){
			Node current = nodes.poll();
			if(nodeName.equals(current.getName())){
				return current;
			}
			
			if(current instanceof Paragraph){
				Paragraph p = (Paragraph)current;
				ArrayList<Node> c = p.getNodes();
				
				for(Node n : c){
					if(nodeName.equals(n.getName())){
						return n;
					}

					nodes.add(n);
				}
			}
		}

		return null;
	}

	public Map<Integer,Node> getInBreadth(Root root){
		Map<Integer,Node> result = new HashMap<>();
		Queue<Node> nodes = new LinkedList<>();
		nodes.add(root);

		while(nodes.size() > 0){
			Node current = nodes.poll();
			if(!result.containsKey(current.hashCode()))
			{
				//todo: tree may contain duplicates after ParagraphTextParser
				result.put(current.hashCode(), current);
			}

			if(current instanceof Paragraph){
				Paragraph p = (Paragraph)current;
				ArrayList<Node> c = p.getNodes();

				nodes.addAll(c);
			}
		}

		return result;
	}
}