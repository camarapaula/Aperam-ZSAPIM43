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
import com.syclo.sap.component.zaperam.object.OrderItem;
import com.syclo.sap.component.zaperam.object.Order;
import com.syclo.sap.jco.JCO;


/*
 * This method is to set the custom values to the IT tables in SAP and get the 
 * returned values from ET tables to Agentry
 * */

public class Z_InboundDeliveryDocumentsFetchBAPI extends BAPI{
	protected String returnUpdateDT = "";
	protected GregorianCalendar _callTime = null;
	
	public Z_InboundDeliveryDocumentsFetchBAPI(User user, GregorianCalendar callTime) throws Exception {
		super(user, new GregorianCalendar());
		this._callTime = callTime;
	}
	
	/*
	 * custom setParameters() method for setting the custom values to the IT_VBELN Table 
	 * */
	
	public void setParameters(SAPObject obj) throws Exception {
		this._log.entry();
		try {
			super.setParameters(obj);
			//Getting the fetch properties from Agentry
			String plant = this._user.getString("fetch.Plant").trim();
			setValue(_imports,"IV_WERKS", plant);
			int sz = Integer.parseInt(this._user.eval("<<size fetch.Z_TempOrderObjects>>"));
			String prefix = "fetch.Z_TempOrderObjects.";
			JCO.Table tbl = this._tables.getTable("IT_VBELN");
			for(int i = 0; i < sz; i++){
				tbl.appendRow();
				setValue(tbl, "VBELN", _user.getString(prefix + i + ".OrderID"));
			}
				
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
	/*
	 * custom processResults() method for fetching the custom values from ET_RECEIPT table for Order object
	 * */
	
	public ArrayList<SAPObject> processResults() throws AgentryException {
		this._log.entry();
		//Reading the returned in export tables from SAP
		ArrayList<SAPObject> docs = new ArrayList<SAPObject>();
		try {
			this.returnUpdateDT = ConversionUtility.getTimeStampAsString(this._callTime, "MM/dd/yyyy HH:mm:ss");
			//The export table is looped over and the values are stored in Material POJO class as a collection
			JCO.Table _docstbl = this._tables.getTable("ET_RECEIPT");
			JCO.Table _selectedtbl = _docstbl;
			this._log.debug("rows returned: {}", new Object[] { Integer.valueOf(_docstbl.getNumRows()) });

			docs.clear();
			int numHdrRecs = _docstbl.getNumRows();
			for (int i = 0; i < numHdrRecs; ++i) {
				_selectedtbl.setRow(i);
				String _orderID = _selectedtbl.getString("VBELN");
				if(!docs.contains(_orderID)){
					Order obj = (Order) createSAPObject(
							"Z_Order");
					obj.setProperties(this._user, _selectedtbl);
					docs.add(obj);
					ArrayList<OrderItem> items = getItems(_orderID, _docstbl);
					obj.Items = ((OrderItem[]) (OrderItem[]) SAPObjectArrayFactory
							.createSAPObjectArray(items,
									SAPObjectFactory.create(_user, "Z_OrderItem")));
				}
			}
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(docs.size()));
		return docs;
	}
	
	/*method to set the custom values for OrderItem object*/
	
	protected ArrayList<OrderItem> getItems(String _orderID, JCO.Table _docstbl) throws AgentryException {
		try {
			JCO.Table _txttbl = this._tables.getTable("ET_LTEXT");
			int numItems = _docstbl.getNumRows();
			ArrayList<OrderItem> items = new ArrayList<OrderItem>(numItems);
			for (int j = 0; j < numItems; ++j) {
				_docstbl.setRow(j);
				String hdrIDAtItem = _docstbl.getString("VBELN");
				if (hdrIDAtItem.equalsIgnoreCase(_orderID)) {
					OrderItem item = (OrderItem) SAPObjectFactory.create(
							this._user.getSAPObject("Z_OrderItem"), new Class[] { User.class },
							new Object[] { this._user });
					item.setProperties(_docstbl, _txttbl);
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
	 * This method will be creating the SAP Object - Order
	 * */
	
	public SAPObject createSAPObject(String name) throws AgentryException, Exception {
		return SAPObjectFactory.create(this._user.getSAPObject(name), new Class[] { User.class },
				new Object[] { this._user });
	}

}
