package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.ComplexTableObject;
import com.syclo.sap.jco.JCO.Table;

/*Start of Customization for the Complex Table Z_CTMovementReason */
public class Z_CTMovementReason extends ComplexTableObject{
	
	public String ID = "";
	public String LangKey = "";
	public String MovementType = "";
	public String Reason = "";
	public String ReasonTxt = "";

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
		setLangKey(tbl.getString("SPRAS"));
		setMovementType(tbl.getString("BWART"));
		setReason(tbl.getString("GRUND"));
		setReasonTxt(tbl.getString("GRTXT"));
		setID(getReason()+getMovementType());		
	}

	/*This is a abstract method of ComplexTableObject abstract class.
     * As the delete functionality is not used here so this method is not being used.
	 */
	
	@Override
	public void setPropertiesForDeletedRecord(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getLangKey() {
		return LangKey;
	}

	public void setLangKey(String langKey) {
		LangKey = langKey;
	}

	public String getMovementType() {
		return MovementType;
	}

	public void setMovementType(String movementType) {
		MovementType = movementType;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getReasonTxt() {
		return ReasonTxt;
	}

	public void setReasonTxt(String reasonTxt) {
		ReasonTxt = reasonTxt;
	}

	public void setID(String iD) {
		ID = iD;
	}

}

