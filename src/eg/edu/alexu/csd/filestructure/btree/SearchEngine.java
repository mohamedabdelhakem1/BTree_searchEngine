package eg.edu.alexu.csd.filestructure.btree;

import java.util.HashMap;
import java.util.List;

public class SearchEngine implements ISearchEngine {
	private IBTree<String, HashMap<String, Integer>> btree;
	
	public SearchEngine() {
		btree = new BTree<String, HashMap<String,Integer>>(10);
	}
	
	
	@Override
	public void indexWebPage(String filePath) {
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		for(String id: map.keySet()) {
			HashMap<String, Integer> words = map.get(id);
			for(String word: words.keySet()) {
				HashMap<String, Integer> wordIndices = btree.search(word);
				if(wordIndices == null) {
					wordIndices = new HashMap<String, Integer>();
					wordIndices.put(id, words.get(word));
					btree.insert(word, wordIndices);
				} else {
					wordIndices.put(id, words.get(word));
				}
			}
		}
	}

	@Override
	public void indexDirectory(String directoryPath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteWebPage(String filePath) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
		// TODO Auto-generated method stub
		return null;
	}

}
