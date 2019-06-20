package eg.edu.alexu.csd.filestructure.btree;
import java.util.HashMap;
import java.util.Map.Entry;


import java.io.*;

public class Main {

	public static void main(String[] args) {
		DocumentParser parser =  new  DocumentParser();
		HashMap<Integer, HashMap<String, Integer>> m = null;
		try {
			m = parser.parse("C:\\Users\\SHIKO\\git\\BTree_searchEngine\\Wikipedia Data Sample\\wiki_00.xml");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for(Entry<Integer ,HashMap<String, Integer>> e: m.entrySet() ) {
			System.out.println(e.getKey());
			for(Entry<String, Integer>  e1 : e.getValue().entrySet()) {
				System.out.println(e1.getKey() + "   : " + e1.getValue());
			}
			System.out.println();
		}
		}
}
