package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.ConversionUtility;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Component;
import com.syclo.sap.component.zaperam.object.GoodsMovementDocument;
import com.syclo.sap.jco.JCO;

/*
 * This method is to set the custom values to the IT tables in SAP and get the 
 * returned values from ET tables to Agentry
 * */

public class Z_DeliveryDocumentsFetchBAPI extends BAPI{
	protected String returnUpdateDT = "";
	protected GregorianCalendar _callTime = null;
	public String materialSearch = "";
	public String pickingListSearch = "";
	public String reqname = "";
	
	 /*
		* Parameterized Constructor definition for the class
		* */
	
	public Z_DeliveryDocumentsFetchBAPI(User user, GregorianCalendar callTime) throws Exception {
		super(user, new GregorianCalendar());
		this._callTime = callTime;
	}
	
	/*
	 * custom setParameters() method for setting the custom values to the IV and IT Tables.
	 * */
	
	public void setParameters(SAPObject obj) throws Exception {
		this._log.entry();
		try {
			super.setParameters(obj);
			//Getting the fetch properties from Agentry
			reqname = this._user.getString("fetch.RequestorName").trim();
			
			String plant = this._user.getString("fetch.Plant").trim();
			String material = this._user.getString("fetch.MaterialNumber").trim();
			String pickno = this._user.getString("fetch.PickingListNumber").trim();
			String purchaseno = this._user.getString("fetch.PurchaseReqNumber").trim().toUpperCase();			
			String unloadpoint = this._user.getString("fetch.UnloadPoint").trim();
			String workorder = this._user.getString("fetch.WorkOrder").trim();
			setValue(_imports,"IV_WERKS", plant);
			setValue(_imports,"IV_WORK_ORD", workorder);
			//setFilterRange("IT_PLANT_RA", "I", "EQ", plant, "");
			setFilterRange("IT_PICKING_RA", "I", "EQ", pickno, "");
			setFilterRange("IT_MATERIAL_RA", "I", "EQ", material, "");
			setFilterRange("IT_PURCHASE_RA", "I", "EQ", purchaseno, "");
			//setFilterRange("IT_REQUESTOR_RA", "I", "EQ", reqname, "");
			setFilterRange("IT_UNLOAD_POINT", "I", "EQ", unloadpoint, "");			
			
			if(material.length()>0){
				materialSearch = "X";
			}
			if(pickno.length()>0){
				pickingListSearch = "X";
			}
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
	/*
	 * custom processResults() method for fetching the custom values for GoodsMovementDocument and component object.
	 * */
	
	public ArrayList<SAPObject> processResults() throws AgentryException {
		this._log.entry();
		//Reading the returned in export tables from SAP
		ArrayList<SAPObject> docs = new ArrayList<SAPObject>();
		try {
			this.returnUpdateDT = ConversionUtility.getTimeStampAsString(this._callTime, "MM/dd/yyyy HH:mm:ss");
			//The export table is looped over and the values are stored in Material POJO class as a collection
			JCO.Table _docstbl = this._tables.getTable("ET_PICKUP");
			JCO.Table _selectedtbl = _docstbl;
			this._log.debug("rows returned: {}", new Object[] { Integer.valueOf(_docstbl.getNumRows()) });

			docs.clear();
			int numHdrRecs = _docstbl.getNumRows();
			for (int i = 0; i < numHdrRecs; ++i) {
				_selectedtbl.setRow(i);
				String _delBin = _selectedtbl.getString("DEL_BIN");
				if(!docs.contains(_delBin)){
					GoodsMovementDocument obj = (GoodsMovementDocument) createSAPObject(
							"Z_GoodsMovementDocument");
					obj.setProperties(this._user, _selectedtbl, materialSearch, pickingListSearch, reqname);
					docs.add(obj);
					ArrayList<Component> items = getItems(_delBin, _docstbl);
					obj.Items = ((Component[]) (Component[]) SAPObjectArrayFactory
							.createSAPObjectArray(items,
									SAPObjectFactory.create(_user, "Z_Component")));
				}
			}
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(docs.size()));
		return docs;
	}
	
	/*method to set the custom values for component object */
	
	protected ArrayList<Component> getItems(String _delBin, JCO.Table _docstbl) throws AgentryException {
		try {
			int numItems = _docstbl.getNumRows();
			ArrayList<Component> items = new ArrayList<Component>(numItems);
			for (int j = 0; j < numItems; ++j) {
				_docstbl.setRow(j);
				String hdrIDAtItem = _docstbl.getString("DEL_BIN");
				if (hdrIDAtItem.equalsIgnoreCase(_delBin)) {
					Component item = (Component) SAPObjectFactory.create(
							this._user.getSAPObject("Z_Component"), new Class[] { User.class },
							new Object[] { this._user });
					item.setProperties(_docstbl);
					items.add(item);
				}
			}
			return items;
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		return null;
	}
	
	/*
	 * This method will be creating the SAP Object - GoodsMovementDocument
	 * */
	
	public SAPObject createSAPObject(String name) throws AgentryException, Exception {
		return SAPObjectFactory.create(this._user.getSAPObject(name), new Class[] { User.class },
				new Object[] { this._user });
	}

}
