package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.ComplexTableObject;
import com.syclo.sap.jco.JCO.Table;

/*Start of Customization for the Complex Table Z_CTIOSLoc */
public class Z_CTIOSLoc extends ComplexTableObject{
	
	public String ID = "";
	public String Plant = "";
	public String ISloc = "";
	public String OSloc = "";
	public String NSloc = "";
	public String Priority = "";
	public String BSloc = "";

	public String getPriority() {
		return Priority;
	}

	public void setPriority(String priority) {
		Priority = priority;
	}
    
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
		setPlant(tbl.getString("WERKS"));
		setNSloc(tbl.getString("LGORT"));
		setISloc(tbl.getString("LGORT_I"));
		setOSloc(tbl.getString("LGORT_O"));
		setPriority(tbl.getString("PRIORITY"));
		setBSloc(tbl.getString("LGORT_B"));
		setID(getPlant()+ getPriority() + getNSloc()+ getISloc()+ getOSloc()+ getBSloc());
		
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
	
	public String getISloc() {
		return ISloc;
	}

	public void setISloc(String iSloc) {
		ISloc = iSloc;
	}

	public String getOSloc() {
		return OSloc;
	}

	public void setOSloc(String oSloc) {
		OSloc = oSloc;
	}

	public String getNSloc() {
		return NSloc;
	}

	public void setNSloc(String nSloc) {
		NSloc = nSloc;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getBSloc() {
		return BSloc;
	}

	public void setBSloc(String bSloc) {
		BSloc = bSloc;
	}

}
