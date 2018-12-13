package h3df;

public class Pair<T> {

	public final T first;
	public final T second;
	
	private Pair(T first, T second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	public static <K> Pair<K> of(K first, K second) {
		return new Pair<>(first, second);
	}
}
