package eg.edu.alexu.csd.filestructure.btree;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.management.RuntimeErrorException;

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
		File dir = new File(directoryPath);
		if(dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					indexDirectory(file.getPath());
				} else {
					indexWebPage(file.getPath());
				}
			}
		} else {
			throw new RuntimeErrorException(new Error("this is not a directory"));
		}
	}

	@Override
	public void deleteWebPage(String filePath) {
		HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();
		for(String id: map.keySet()) {
			HashMap<String, Integer> words = map.get(id);
			for(String word: words.keySet()) {
				HashMap<String, Integer> wordIndices = btree.search(word);
				if(wordIndices != null) {
					wordIndices.remove(id);
				}
			}
		}
	}

	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
		word = word.toLowerCase();
		HashMap<String, Integer> wordIndices = btree.search(word);
		List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
		if(wordIndices == null) return null;
		for(String id: wordIndices.keySet()) {
			SearchResult res = new SearchResult(id, wordIndices.get(id));
			searchResults.add(res);
		}
		return searchResults;
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
		sentence = sentence.toLowerCase();
		HashMap<String, Integer> documents = null;
		String[] wordsToSearch = sentence.split(" ");
		if(wordsToSearch.length > 0) {
			documents = btree.search(wordsToSearch[0]);
			for(int i = 1; i < wordsToSearch.length; i++) {
				HashMap<String, Integer> nextWordindices = btree.search(wordsToSearch[i]);
				Set<String> doc_ids = nextWordindices.keySet();
				for(String idnext: doc_ids) {
					if(!documents.containsKey(idnext)) {
						nextWordindices.remove(idnext);
						continue;
					} else {
						int rank = Math.min(documents.get(idnext), nextWordindices.get(idnext));
						documents.put(idnext, rank);
					}
				}
				doc_ids = documents.keySet();
				for(String idnext: doc_ids) {
					if(!nextWordindices.containsKey(idnext)) {
						documents.remove(idnext);
						continue;
					}
				}
			}
		}
		if(documents != null) {
			List<ISearchResult> results = new ArrayList<ISearchResult>();
			for(String id: documents.keySet()) {
				results.add(new SearchResult(id, documents.get(id)));
			}
			return results;
		}
		return null;
	}

}
