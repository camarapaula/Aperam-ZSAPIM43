package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.ComplexTableObject;
import com.syclo.sap.User;
import com.syclo.sap.jco.JCO.Table;

/*Start of Customization for the Complex Table Z_CTPrinter */
public class Z_CTPrinter extends ComplexTableObject {
	public String ID = "";
	public String PrinterName = "";
	public String Plant = "";
	public String SLOC = "";
	public String SBin = "";
	public String LabelInd = "";
	public String LabelSize = "";

	public Z_CTPrinter() {
	}

	public Z_CTPrinter(User user) {
		super(user);
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
		setPrinterName(tbl.getString("LDEST"));
		setPlant(tbl.getString("WERKS"));
		setSLOC(tbl.getString("LGORT"));
		setSBin(tbl.getString("LGPLA"));
		setLabelSize(tbl.getString("LABEL_SIZE"));
		setID(getPrinterName()+getPlant()+getSLOC()+getSBin()+getLabelSize());
	}

	/*This is a abstract method of ComplexTableObject abstract class.
     * As the delete functionality is not used here so this method is not being used.
	 */
	
	@Override
	public void setPropertiesForDeletedRecord(Table tbl) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public String getPrinterName() {
		return PrinterName;
	}

	public void setPrinterName(String printerName) {
		PrinterName = printerName;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getSLOC() {
		return SLOC;
	}

	public void setSLOC(String sLOC) {
		SLOC = sLOC;
	}

	public String getSBin() {
		return SBin;
	}

	public void setSBin(String sBin) {
		SBin = sBin;
	}

	public String getLabelInd() {
		return LabelInd;
	}

	public void setLabelInd(String labelInd) {
		LabelInd = labelInd;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getLabelSize() {
		return LabelSize;
	}

	public void setLabelSize(String labelSize) {
		LabelSize = labelSize;
	}

}
