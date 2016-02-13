package org.gojul.gojulutils.hibernateutils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GojulHibernateCollectionsMergeToolTest {

	private final static class DummyEntity implements GojulHibernateMergeable<DummyEntity> {
		
		private Long key;
		private String label;
		
		public DummyEntity(final Long key, final String label) {
			this.key = key;
			this.label = label;
		}
		
		public Long getKey() {
			return this.key;
		}

		@Override
		public void mergeEntity(DummyEntity entity) {
			if (entity == null) {
				return;
			}
			this.label = entity.label;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			// Only the key should be present in the hashcode as it is the element
			// which is known not to be altered.
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DummyEntity other = (DummyEntity) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (label == null) {
				if (other.label != null)
					return false;
			} else if (!label.equals(other.label))
				return false;
			return true;
		}
		
	}
	
	private final static class DummyEntityKeyInstanciator implements GojulHibernateKeyInstanciator<Long, DummyEntity> {

		@Override
		public Long generateKey(DummyEntity element) {
			return element.getKey();
		}
		
	}
	
	private DummyEntity e1, e2, e4;
	private DummyEntity e1New, e2New;
	private DummyEntity e1Merge, e2Merge, e3Merge;
	private DummyEntityKeyInstanciator keyInstanciator;
	private GojulHibernateCollectionsMergeTool<Long, DummyEntity> mergeTool;
	
	@Before
	public void setUp() throws Exception {
		e1 = new DummyEntity(1L, "e1");
		e2 = new DummyEntity(2L, "e2");
		e4 = new DummyEntity(4L, "e4");
		
		e1New = new DummyEntity(null, "e1New");
		e2New = new DummyEntity(null, "e2New");
		
		e1Merge = new DummyEntity(1L, "e1Merge");
		e2Merge = new DummyEntity(2L, "e2Merge");
		e3Merge = new DummyEntity(3L, "e3Merge");

		keyInstanciator = new DummyEntityKeyInstanciator();
		mergeTool = new GojulHibernateCollectionsMergeTool<>(keyInstanciator, new HashSet<>(Arrays.asList(e1, e2, e4, e1New, e2New)));
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullInstanciatorThrowsException() {
		new GojulHibernateCollectionsMergeTool<>(null, new HashSet<>(Arrays.asList(e1, e2, e1New)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullElementListThrowsException() {
		new GojulHibernateCollectionsMergeTool<>(keyInstanciator, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorWithElementListContainingNullThrowsException() {
		new GojulHibernateCollectionsMergeTool<>(keyInstanciator, new HashSet<>(Arrays.asList(e1, null, e1New)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testMergeEntitiesWithoutDeleteWithNullEntitiesThrowsException() {
		mergeTool.mergeEntitiesWithoutDelete(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMergeEntitiesWithoutDeleteWithEntitiesContainingNullValuesThrowsException() {
		mergeTool.mergeEntitiesWithoutDelete(new HashSet<>(Arrays.asList(e1Merge, null, e3Merge)));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testMergeEntitiesWithoutDeleteWithSourceEntitiesContainingTwiceTheSameKeyThrowsException() {
		DummyEntity e2Bad = new DummyEntity(2L, "e2Bad");
		
		GojulHibernateCollectionsMergeTool<Long, DummyEntity> mt = new GojulHibernateCollectionsMergeTool<>(keyInstanciator, new HashSet<>(Arrays.asList(e1, e2, e2Bad, e4, e1New, e2New)));
		
		mt.mergeEntitiesWithoutDelete(new HashSet<>(Arrays.asList(e1Merge, e2Merge, e3Merge)));
	}
	
	@Test
	public void testMergeEntitiesWithoutDelete() {
		Set<DummyEntity> entitiesToMerge = new HashSet<>(Arrays.asList(e1Merge, e2Merge, e3Merge));
		
		mergeTool.mergeEntitiesWithoutDelete(entitiesToMerge);
		
		assertEquals(new HashSet<>(Arrays.asList(e1, e2, e3Merge, e4, e1New, e2New)), entitiesToMerge);
		
		for (DummyEntity e: entitiesToMerge) {
			if (e1.equals(e)) {
				assertSame("e1Merge is not same as e", e, e1Merge);
			} else if (e2.equals(e)) {
				assertSame("e1Merge is not same as e", e, e2Merge);
			}
		}
	}

	@Test(expected = NullPointerException.class)
	public void testMergeEntitiesWithDeleteWithNullEntitiesThrowsException() {
		mergeTool.mergeEntitiesWithDelete(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMergeEntitiesWithDeleteWithEntitiesContainingNullValuesThrowsException() {
		mergeTool.mergeEntitiesWithDelete(new HashSet<>(Arrays.asList(e1Merge, null, e3Merge)));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testMergeEntitiesWithDeleteWithSourceEntitiesContainingTwiceTheSameKeyThrowsException() {
		DummyEntity e2Bad = new DummyEntity(2L, "e2Bad");
		
		GojulHibernateCollectionsMergeTool<Long, DummyEntity> mt = new GojulHibernateCollectionsMergeTool<>(keyInstanciator, new HashSet<>(Arrays.asList(e1, e2, e2Bad, e4, e1New, e2New)));
		
		mt.mergeEntitiesWithDelete(new HashSet<>(Arrays.asList(e1Merge, e2Merge, e3Merge)));
	}
	
	@Test
	public void testMergeEntitiesWithDelete() {
		Set<DummyEntity> entitiesToMerge = new HashSet<>(Arrays.asList(e1Merge, e2Merge, e3Merge));
		
		mergeTool.mergeEntitiesWithDelete(entitiesToMerge);
		
		assertEquals(new HashSet<>(Arrays.asList(e1, e2, e4, e1New, e2New)), entitiesToMerge);
		
		for (DummyEntity e: entitiesToMerge) {
			if (e1.equals(e)) {
				assertSame("e1Merge is not same as e", e, e1Merge);
			} else if (e2.equals(e)) {
				assertSame("e1Merge is not same as e", e, e2Merge);
			}
		}
	}
}
