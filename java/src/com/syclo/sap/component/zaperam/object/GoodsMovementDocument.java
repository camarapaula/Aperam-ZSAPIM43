package com.syclo.sap.component.zaperam.object;

import java.util.ArrayList;

import com.syclo.agentry.BusinessLogicException;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_DeliveryDocumentsConfirmUpdateBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_DeliveryDocumentsPostBAPI;
import com.syclo.sap.jco.JCO;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_DeliveryDocumentsFetchBAPI,Z_DeliveryDocumentsConfirmUpdateBAPI,Z_DeliveryDocumentsPostBAPI and stored into this POJO class via
 * getter and setter methods.
 * */

public class GoodsMovementDocument extends com.syclo.sap.component.goodsmovement.object.GoodsMovementDocument{
	public Component[] Items = new Component[0];
	public String Z_MaterialSearch = "";
	public String Z_DeliveryPersName = "";
	public String Z_PickingListSearch = "";
	
	/*
	* Default Constructor definition for the class
	* */
	
	public GoodsMovementDocument() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public GoodsMovementDocument(User user) {
		super(user);
	}
	
	/*method to pass the arguments from Z_DeliveryDocumentsFetchBAPI to set the variables in java*/
	
	public void setProperties(User u, JCO.Table tbl, String materialSearch, String pickingListSearch, String reqname) throws Exception {
		try {
			setID(tbl.getString("DEL_BIN"));
			setMaterialDocument(tbl.getString("DEL_BIN"));
			setMovementType(tbl.getString("BWART"));
			setZ_MaterialSearch(materialSearch);
			setZ_PickingListSearch(pickingListSearch);
			setZ_DeliveryPersName(reqname);
			
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	
	/*
	 * Method to set the custom values GoodsMovementDocument and call the setproperties method for component object from 
	 * the Z_DeliveryDocumentsPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, Z_DeliveryDocumentsPostBAPI bapi , int i) throws BusinessLogicException {
		try {
			String prefix = "transaction.Z_DeliveryIssue." + i + ".";
			String itemprefix = prefix + "Items";
			setMaterialDocument(_user.getString(prefix + "MaterialDocument"));
			setZ_DeliveryPersName(_user.getString(prefix + "Z_DeliveryPersName"));
			int itemCount = Integer.parseInt(this._user.eval("<<size " + itemprefix +">>"));
			ArrayList<Component> items = new ArrayList<Component>(itemCount);
			for(int j = 0; j<itemCount;j++){
				if (_user.getBoolean(itemprefix +"."+ j + ".Z_isSelected")) {
					Component item = (Component) SAPObjectFactory.create(this._user.getSAPObject("Z_Component"),
							new Class[] { User.class }, new Object[] { this._user });
					item.setProperties(user, i, j);
					items.add(item);
				}
			}
			this.Items = ((Component[]) (Component[]) SAPObjectArrayFactory
					.createSAPObjectArray(items,
							SAPObjectFactory.create(_user, "Z_Component")));
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	
	/*
	 * Method to set the custom values GoodsMovementDocument and call the setproperties method for component object from 
	 * the Z_DeliveryDocumentsConfirmUpdateBAPI to the respective variables in java
	 * */
	
	
	public void setProperties(User user, Z_DeliveryDocumentsConfirmUpdateBAPI bapi , int i) throws BusinessLogicException {
		try {
			String prefix = "transaction.Z_DeliveryIssue." + i + ".";
			String itemprefix = prefix + "Items";
			setMaterialDocument(_user.getString(prefix + "MaterialDocument"));
			setZ_DeliveryPersName(_user.getString(prefix + "Z_DeliveryPersName"));
			int itemCount = Integer.parseInt(this._user.eval("<<size " + itemprefix +">>"));
			ArrayList<Component> items = new ArrayList<Component>(itemCount);
			for(int j = 0; j<itemCount;j++){
				if (_user.getBoolean(itemprefix +"."+ j + ".Z_isSelected")) {
					Component item = (Component) SAPObjectFactory.create(this._user.getSAPObject("Z_Component"),
							new Class[] { User.class }, new Object[] { this._user });
					item.setProperties(user, i, j);
					items.add(item);
				}
			}
			this.Items = ((Component[]) (Component[]) SAPObjectArrayFactory
					.createSAPObjectArray(items,
							SAPObjectFactory.create(_user, "Z_Component")));
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}

	public Component[] getItems() {
		return Items;
	}

	public void setItems(Component[] items) {
		Items = items;
	}

	public String getZ_MaterialSearch() {
		return Z_MaterialSearch;
	}

	public void setZ_MaterialSearch(String z_MaterialSearch) {
		Z_MaterialSearch = z_MaterialSearch;
	}

	public String getZ_DeliveryPersName() {
		return Z_DeliveryPersName;
	}

	public void setZ_DeliveryPersName(String z_DeliveryPersName) {
		Z_DeliveryPersName = z_DeliveryPersName;
	}

	public String getZ_PickingListSearch() {
		return Z_PickingListSearch;
	}

	public void setZ_PickingListSearch(String z_PickingListSearch) {
		Z_PickingListSearch = z_PickingListSearch;
	}
	

}
