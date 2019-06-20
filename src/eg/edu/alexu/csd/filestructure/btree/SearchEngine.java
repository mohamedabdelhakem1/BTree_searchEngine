package eg.edu.alexu.csd.filestructure.btree;

import java.util.HashMap;
import java.util.List;

public class SearchEngine implements ISearchEngine {
	private IBTree<String, HashMap<String, Integer>> btree;
	
	public SearchEngine() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void indexWebPage(String filePath) {
		// TODO Auto-generated method stub
		
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
