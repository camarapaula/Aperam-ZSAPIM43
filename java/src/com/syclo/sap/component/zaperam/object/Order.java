package com.syclo.sap.component.zaperam.object;

import java.util.ArrayList;

import com.syclo.agentry.BusinessLogicException;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_InboundDeliveryDocumentsPostBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_PurchaseOrderFetchBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_PurchaseOrderPostBAPI;
import com.syclo.sap.jco.JCO;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_InboundDeliveryDocumentsFetchBAPI,Z_InboundDeliveryDocumentsPostBAPI,
 * Z_PurchaseOrderFetchBAPI, Z_PurchaseOrderPostBAPI and stored into this POJO class via getter and setter methods.
 * */

public class Order extends com.syclo.sap.component.purchasing.object.Order{
	public OrderItem[] Items = new OrderItem[0];
	public String Z_InboundDocNo ="";
	
	/*
	* Default Constructor definition for the class
	* */
	
	public Order() {
	}
	
	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Order(User user) {
		super(user);
	}
	
	/*
	 * Method to set the custom values Order from the Z_InboundDeliveryDocumentsFetchBAPI to the respective variables in java
	 * */
	
	public void setProperties(User u, JCO.Table tbl) throws Exception {
		try {
			setOrderID(tbl.getString("VBELN"));
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	
	/*
	 * Method to set the custom values Order from the Z_PurchaseOrderFetchBAPI to the respective variables in java
	 * */
	
	public void setProperties(User u, JCO.Table tbl,Z_PurchaseOrderFetchBAPI bapi) throws Exception {
		try {
			setOrderID(tbl.getString("EBELN"));
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	
	/*
	 * Method to set the custom values Order from the Z_InboundDeliveryDocumentsPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, Z_InboundDeliveryDocumentsPostBAPI bapi , int i) throws BusinessLogicException {
		try {
			String prefix = "transaction.Z_POrders." + i + ".";
			String itemprefix = prefix + "Items";
			setOrderID(_user.getString(prefix + "OrderID"));
			int itemCount = Integer.parseInt(this._user.eval("<<size " + itemprefix +">>"));
			ArrayList<OrderItem> items = new ArrayList<OrderItem>(itemCount);
			for(int j = 0; j<itemCount;j++){
				//if (_user.getBoolean(itemprefix +"."+ j + ".Z_IsSelected")) {
				OrderItem item = (OrderItem) SAPObjectFactory.create(
						this._user.getSAPObject("Z_OrderItem"), new Class[] { User.class },
						new Object[] { this._user });
				item.setProperties(user, i, j);
				items.add(item);
				//}
			}
			this.Items = ((OrderItem[]) (OrderItem[]) SAPObjectArrayFactory
					.createSAPObjectArray(items,
							SAPObjectFactory.create(_user, "Z_OrderItem")));
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	
	/*
	 * Method to set the custom values Order from the Z_PurchaseOrderPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, Z_PurchaseOrderPostBAPI bapi , int i) throws BusinessLogicException {
		try {
			String prefix = "transaction.Orders." + i + ".";
			String itemprefix = prefix + "Items";
			setOrderID(_user.getString(prefix + "OrderID"));
			int itemCount = Integer.parseInt(this._user.eval("<<size " + itemprefix +">>"));
			ArrayList<OrderItem> items = new ArrayList<OrderItem>(itemCount);
			for(int j = 0; j<itemCount;j++){
				if (_user.getBoolean(itemprefix +"."+ j + ".Z_IsSelected")) {
				OrderItem item = (OrderItem) SAPObjectFactory.create(
						this._user.getSAPObject("Z_OrderItem"), new Class[] { User.class },
						new Object[] { this._user });
				item.setProperties(user, i, j, bapi);
				items.add(item);
				}
			}
			this.Items = ((OrderItem[]) (OrderItem[]) SAPObjectArrayFactory
					.createSAPObjectArray(items,
							SAPObjectFactory.create(_user, "Z_OrderItem")));
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	public OrderItem[] getItems() {
		return Items;
	}

	public void setItems(OrderItem[] items) {
		Items = items;
	}

	public String getZ_InboundDocNo() {
		return Z_InboundDocNo;
	}

	public void setZ_InboundDocNo(String z_InboundDocNo) {
		Z_InboundDocNo = z_InboundDocNo;
	}
}
