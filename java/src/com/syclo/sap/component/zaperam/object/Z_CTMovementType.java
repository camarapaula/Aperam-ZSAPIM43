package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.ComplexTableObject;
import com.syclo.sap.jco.JCO.Table;

/*Start of Customization for the Complex Table Z_CTMovementType */
public class Z_CTMovementType extends ComplexTableObject{
	
	public String ID = "";
	public String Plant = "";
	public String MovementType = "";
	public String Description = "";

	/*This is a abstract method of ComplexTableObject abstract class.The ID is used to store the unique key
	 * of object*/
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	/*This is a abstract method of ComplexTableObject abstract class.As long text functionality is not used
	 * here so this method is not being used.*/
	
	@Override
	public void setNotes(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*Method to set the values in the backend for the complex table fields via the setter methods
	 * created for these fields
	 */

	@Override
	public void setProperties(Table tbl) throws Exception {
		// TODO Auto-generated method stub
		setPlant(tbl.getString("RULES"));
		setMovementType(tbl.getString("BWART"));
		setDescription(this.MovementType  + " - " + tbl.getString("BTEXT"));		
		setID(getPlant()+getMovementType());
		
	}

	/*This is a abstract method of ComplexTableObject abstract class.
     * As the delete functionality is not used here so this method is not being used.
	 */
	
	@Override
	public void setPropertiesForDeletedRecord(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getMovementType() {
		return MovementType;
	}

	public void setMovementType(String movementType) {
		MovementType = movementType;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public void setID(String iD) {
		ID = iD;
	}		

}
