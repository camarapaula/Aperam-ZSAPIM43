package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.object.PhysicalInventoryDocument;
import com.syclo.sap.component.zaperam.object.Material;
import com.syclo.sap.jco.JCO.Table;

public class Z_PhysicalInventoryFetchBAPI extends com.syclo.sap.component.physicalinventory.bapi.PhysicalInventoryFetchBAPI{

	public Z_PhysicalInventoryFetchBAPI(User user, GregorianCalendar lu, GregorianCalendar callTime) throws Exception {
		super(user, lu, callTime);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<SAPObject> processResults() throws AgentryException {
		ArrayList<SAPObject> physicalinventorydocs = super.processResults();
		Table items = this._tables.getTable("ET_ITEMS");
		int numItems = items.getNumRows();
		for (SAPObject p : physicalinventorydocs) {
			PhysicalInventoryDocument phyInvObj = (PhysicalInventoryDocument) p;
			//if (((com.syclo.sap.component.physicalinventory.object.PhysicalInventoryDocument)phyInvObj).getMaterials() != null) {			
				ArrayList<Material> materials = new ArrayList<Material>(numItems);
				for (int j = 0; j < (phyInvObj).getMaterials().length; ++j) {
					Material mat = (Material) (phyInvObj).getMaterials()[j];
					String _id = mat.getID();
					for (int i = 0; i < items.getNumRows(); ++i) {
						items.setRow(i);
						String thisDocID = items.getString("IBLNR");
						String thisItemID = items.getString("ZEILI");
						if (_id.equalsIgnoreCase(thisDocID + thisItemID)) {
							mat.setStorageBin(items.getString("SGT_SCAT"));
							mat.setZ_STLocSBin(mat.getStorageLocation()+mat.getStorageBin());
							break;
						}
					}
					materials.add(mat);
				}
				phyInvObj.setMaterials((Material[]) (Material[]) SAPObjectArrayFactory.createSAPObjectArray(materials,
						SAPObjectFactory.create(this._imUser, "Material")));
			//}
		}		
		return physicalinventorydocs;
		
	}

}
