package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Z_PickingList;
import com.syclo.sap.component.zaperam.object.Z_PickingListItem;
import com.syclo.sap.jco.JCO;

/*
 * This method is to set the custom values to the IT_PICKUP table in SAP and get the 
 * returned values from ET_PICKUP to Agentry
 * */

public class Z_PickingListsFetchBAPI extends BAPI{
	
	public String isSelected = "";

	public Z_PickingListsFetchBAPI(User u) throws AgentryException {
		super(u, new GregorianCalendar());
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * custom setParameters() method for setting the custom values to the IT_PICKUP Table 
	 * */
	
	public void setParameters(SAPObject obj) throws Exception {
		this._log.entry();
		try {
			super.setParameters(obj);
			//Getting the fetch properties from Agentry
			String plant = this._user.getString("fetch.Plant").trim();
			setValue(_imports,"IV_WERKS", plant);
			int sz = Integer.parseInt(this._user.eval("<<size fetch.Z_TempPickingListObjects>>"));
			this._log.info("Fetch rows returned: {}", new Object[] { Integer.valueOf(sz) });
			String prefix = "fetch.Z_TempPickingListObjects.";
			JCO.Table tbl = this._tables.getTable("IT_PICKUP");
			if(sz>0){
				if( _user.getString(prefix + 0 + ".Z_isSelectedPL").equalsIgnoreCase("X")){
					isSelected = "True";
					for(int i = 0; i < sz; i++){
						tbl.appendRow();
						tbl.setRow(i);
						setValue(tbl, "PICK_LIST_NO", _user.getString(prefix + i + ".PickingListNumber"));
					}
				}
			}
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}


/*
	 * custom processResults() method for fetching the custom values for Z_PickingList
	 * */
	
	@Override
	public ArrayList<SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<SAPObject> picklists = new ArrayList<SAPObject>();
		try {
			JCO.Table _picktbl = this._tables.getTable("ET_PICKUP");
			JCO.Table _selectedEntrytbl = _picktbl;
			this._log.debug("rows returned: {}", new Object[] { Integer.valueOf(_picktbl.getNumRows()) });
			int numHdrRecs = _picktbl.getNumRows();
			
			for (int i = 0; i < numHdrRecs; ++i) {
				_selectedEntrytbl.setRow(i);
				String _picklistno = _selectedEntrytbl.getString("PICK_LIST_NO");
				if(!picklists.contains(_picklistno)){
					Z_PickingList obj = (Z_PickingList) createSAPObject(
							"Z_PickingList");
					obj.setProperties(_selectedEntrytbl, isSelected);
					picklists.add(obj);
					ArrayList<Z_PickingListItem> pickListItems = getItems(_picklistno, _picktbl);
					obj.PickingListItems = ((Z_PickingListItem[]) (Z_PickingListItem[]) SAPObjectArrayFactory
							.createSAPObjectArray(pickListItems,
									SAPObjectFactory.create(_user, "Z_PickingListItem")));
				}
			}
			
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(picklists.size()));
		return picklists;
	}
	
	/*method to set the custom values for Z_PickingListItem object*/
	
	protected ArrayList<Z_PickingListItem> getItems(String _picklistno, JCO.Table _picktbl) throws AgentryException {
		try {
			int numItems = _picktbl.getNumRows();
			ArrayList<Z_PickingListItem> picklistItems = new ArrayList<Z_PickingListItem>(numItems);
			for (int j = 0; j < numItems; ++j) {
				_picktbl.setRow(j);
				String hdrIDAtItem = _picktbl.getString("PICK_LIST_NO");
				if (hdrIDAtItem.equalsIgnoreCase(_picklistno)) {
					Z_PickingListItem item = (Z_PickingListItem) SAPObjectFactory.create(
							this._user.getSAPObject("Z_PickingListItem"), new Class[] { User.class },
							new Object[] { this._user });
					item.setProperties(_picktbl);
					picklistItems.add(item);
				}
			}
			return picklistItems;
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		return null;
	}
	
	/*
	 * This method will be creating the SAP Object - Z_PickingList
	 * */
	
	public SAPObject createSAPObject(String name) throws AgentryException, Exception {
		return SAPObjectFactory.create(this._user.getSAPObject(name), new Class[] { User.class },
				new Object[] { this._user });
	}

}
