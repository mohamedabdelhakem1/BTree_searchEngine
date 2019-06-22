package eg.edu.alexu.csd.filestructure.btree;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.management.RuntimeErrorException;

public class SearchEngine implements ISearchEngine {
	private IBTree<String, HashMap<String, Integer>> btree;
	private DocumentParser parser;
	
	public SearchEngine(int minDegree) {
		btree = new BTree<String, HashMap<String,Integer>>(minDegree);
		parser = new DocumentParser();
	}
	
	@Override
	public void indexWebPage(String filePath) {
		if(filePath == null || filePath.isEmpty()) throw new RuntimeErrorException(new Error());
		HashMap<String, HashMap<String, Integer>> map = parser.parse(filePath);
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
		if(directoryPath == null || directoryPath.isEmpty()) throw new RuntimeErrorException(new Error());
		File dir = new File(directoryPath);
		if(!dir.exists()) throw new RuntimeErrorException(new Error());
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
		if(filePath == null || filePath.isEmpty()) throw new RuntimeErrorException(new Error());
		HashMap<String, HashMap<String, Integer>> map = parser.parse(filePath);
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
		if(word == null) throw new RuntimeErrorException(new Error());
		if(word.equals("" )) return  new ArrayList<ISearchResult>();
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
		if(sentence == null) throw new RuntimeErrorException(new Error());
		if(sentence.equals("" )) return  new ArrayList<ISearchResult>();	
		sentence = sentence.toLowerCase();
		HashMap<String, Integer> documents = null;
		sentence = sentence.trim();
		String[] wordsToSearch = sentence.split("\\s+");
		if(wordsToSearch.length > 0) {
			documents = btree.search(wordsToSearch[0]);
			for(int i = 1; i < wordsToSearch.length; i++) {
				HashMap<String, Integer> nextWordindices = btree.search(wordsToSearch[i]);
				if(nextWordindices == null) {
					return new ArrayList<>();
				}
				Set<String> doc_ids_set = nextWordindices.keySet();
				String[] doc_ids = new String[doc_ids_set.size()];
				doc_ids = doc_ids_set.toArray(doc_ids);
				
				for(String idnext: doc_ids) {
					if(!documents.containsKey(idnext)) {
						nextWordindices.remove(idnext);
						continue;
					} else {
						int rank = Math.min(documents.get(idnext), nextWordindices.get(idnext));
						documents.put(idnext, rank);
					}
				}
				doc_ids = documents.keySet().toArray(doc_ids);
				
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
