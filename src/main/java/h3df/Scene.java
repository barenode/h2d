package h3df;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import h2d.common.Image;
import h2d.common.ImageImpl;
import h2d.common.Line;
import h2d.common.Point;
import solids.Solid;
import transforms.Camera;
import transforms.Point3D;
import transforms.Vec3D;

public class Scene {

	private Camera camera = new Camera();
	private Image image = new ImageImpl(1, 1000, 800);
	private List<Solid> solids = new ArrayList<>();	
	private Vec3D viewport = new Vec3D();
	
	public Scene() {
		super();
	}
	
	public void add(Solid solid) {
		solids.add(solid);
	}

	public List<Solid> getSolids() {
		return solids;
	}	
	
	private Function<Point3D, Point3D> transform = (p) -> {
		return p
			.mul(camera.getViewMatrix());
			
			
	};	

	private Supplier<List<Line>> pipeline = () -> {
		return solids.stream()
			.map(s -> s.getEdges())
			.flatMap(e -> e.stream())
			.map(e -> {
				return Pair.of(
					transform.apply(e.first).dehomog(), 
					transform.apply(e.second).dehomog());
			})
			.filter(p -> {
				return p.first.isPresent() && p.second.isPresent();
			})
			.map(p -> {
				return Pair.of(
					p.first.get().mul(viewport),
					p.second.get().mul(viewport));
			})
			.map(p -> {
				return new Line(
					Point.fromVec(p.first), 
					Point.fromVec(p.second));
			}).collect(Collectors.toList());
	};
	
	
	private static class Pair<T> {

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
	
	

	
//	private class Pipeline implements Supplier<List<Line>> {
//		@Override
//		public List<Line> get() {
//			solids.forEach(s -> {
//				s.getEdges().forEach(e -> {
//					
//				});
//			});
//			return null;
//		}		
//	}	
}
