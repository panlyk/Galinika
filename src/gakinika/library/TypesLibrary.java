package gakinika.library;

public class TypesLibrary {
	
	public enum galenicType {
		CREAM("Κρέμα",50f,10f,2.26f,0.21f),
		SOLUTION("Διάλυμα", 100f, 50f, 1.94f, 0.19f);
		
		private final String displayName;
		private final float minimumQuantity;
		private final float divisionQuantity;
		private final float minimumCost;
		private final float costIncrement;
		

		galenicType(String displayName, float minimumQuantity, float divisionQuantity, float minimumCost, float costIncrement) {
	        this.displayName = displayName;
	        this.minimumQuantity = minimumQuantity;
	        this.divisionQuantity = divisionQuantity;
	        this.minimumCost = minimumCost;
	        this.costIncrement = costIncrement;
		}
	    public String getDisplayName() {
	        return displayName;
	    }

	    public float getMinimumQuantity() {
	        return minimumQuantity;
	    }

	    public float getDivisionQuantity() {
	        return divisionQuantity;
	    }

	    public float getMinimumCost() {
	        return minimumCost;
	    }

	    public float getCostIncrement() {
	        return costIncrement;
	    }
	    
		
		@Override
		public String toString() {
			return displayName;
		}
	}

}
