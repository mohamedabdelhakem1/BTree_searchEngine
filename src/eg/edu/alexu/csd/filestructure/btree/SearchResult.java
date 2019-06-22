package eg.edu.alexu.csd.filestructure.btree;

public class SearchResult implements ISearchResult {
	private String id;
	private int rank;

	public SearchResult(String id, int rank) {
		this.id = id;
		this.rank = rank;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id + "      " +String.valueOf(rank);
	}
}
