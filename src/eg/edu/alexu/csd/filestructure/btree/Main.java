package eg.edu.alexu.csd.filestructure.btree;
import java.util.HashMap;
import java.util.Map.Entry;


public class Main {

	public static void main(String[] args) {
		DocumentParser parser =  new  DocumentParser();
		HashMap<String, HashMap<String, Integer>> m = null;
		m = parser.parse("C:\\Users\\SHIKO\\git\\BTree_searchEngine\\Wikipedia Data Sample\\wiki_00.xml");
		for(Entry<String, HashMap<String, Integer>> e: m.entrySet() ) {
			System.out.println(e.getKey());
			for(Entry<String, Integer>  e1 : e.getValue().entrySet()) {
				System.out.println(e1.getKey() + "   : " + e1.getValue());
			}
			System.out.println();
		}
		}
}
