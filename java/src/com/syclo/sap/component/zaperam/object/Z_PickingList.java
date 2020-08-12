package com.syclo.sap.component.zaperam.object;

import java.util.ArrayList;

import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.SAPObjectI;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_PostPickingListsStepHandler;
import com.syclo.sap.jco.JCO.Table;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_PickingListsFetchBAPI and stored into this POJO class via
 * getter and setter methods.
 * */

public class Z_PickingList extends SAPObject implements SAPObjectI{
	

/*
	* Default Constructor definition for the class
	* */
	
	public Z_PickingList() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_PickingList(User user) {
		super(user);
	}
	
	public String PickingListNumber = "";
	public String IsPicked = "";
	public String IsSelected = "";
	public String TotalItems = "";
	public String DeliveryBin = "";
	public String Plant = "";
	public String PartialDeliveryBin = "";
	public String Z_WONum = "";
	public String IsDelivery = "";
	public String IsDeliveryFlag = "";
	public Z_PickingListItem[] PickingListItems = new Z_PickingListItem[0];


	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return PickingListNumber;
	}

	@Override
	public void setNotes(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperties(Table tbl) throws Exception {
		// TODO Auto-generated method stub
		setPickingListNumber(tbl.getString("PICK_LIST_NO"));
		setPartialDeliveryBin(tbl.getString("DEL_BIN"));		
	}
	
	/*method to pass the arguments from Z_PickingListsFetchBAPI to set the variables in java*/
	
	public void setProperties(Table tbl, String sel) throws Exception {
		// TODO Auto-generated method stub
		setPickingListNumber(tbl.getString("PICK_LIST_NO"));
		setPartialDeliveryBin(tbl.getString("DEL_BIN"));
		setZ_WONum(tbl.getString("AUFNR"));
		setIsSelected(sel);
	}
	
	public void setProperties(User u, Z_PostPickingListsStepHandler handler) throws Exception {
		String prefix = "transaction.";
		setPickingListNumber(u.getString(prefix + "PickingListNumber"));
		setPlant(u.getString(prefix + "Plant"));
		setDeliveryBin(u.getString(prefix + "DeliveryBin"));
		/*if(u.getBoolean(prefix + "IsDelivery"))
		{
			setIsDelivery("X");
			IsDeliveryFlag = "X";
		}*/
		
		ArrayList<Z_PickingListItem> _items = new ArrayList<Z_PickingListItem>();
		int itemCount = Integer.parseInt(this._user.eval("<<size transaction.PickingListItems>>"));
		for(int i = 0; i<itemCount;i++){
			if(_user.getBoolean("transaction.PickingListItems." + i + ".IsPicked")){
				Z_PickingListItem _item = (Z_PickingListItem) SAPObjectFactory.create(
						this._user.getSAPObject("Z_PickingListItem"), new Class[] { User.class },
						new Object[] { this._user });
				_item.setProperties(_user, i);
				_items.add(_item);
			}
		}
		
		this.PickingListItems = ((Z_PickingListItem[]) (Z_PickingListItem[]) SAPObjectArrayFactory
				.createSAPObjectArray(_items,
						SAPObjectFactory.create(_user, "Z_PickingListItem")));
	}

	public String getPickingListNumber() {
		return PickingListNumber;
	}

	public void setPickingListNumber(String pickingListNumber) {
		PickingListNumber = pickingListNumber;
	}

	public String getIsPicked() {
		return IsPicked;
	}

	public void setIsPicked(String isPicked) {
		IsPicked = isPicked;
	}

	public String getIsSelected() {
		return IsSelected;
	}

	public void setIsSelected(String isSelected) {
		IsSelected = isSelected;
	}

	public String getTotalItems() {
		return TotalItems;
	}

	public void setTotalItems(String totalItems) {
		TotalItems = totalItems;
	}

	public String getDeliveryBin() {
		return DeliveryBin;
	}

	public void setDeliveryBin(String deliveryBin) {
		DeliveryBin = deliveryBin;
	}

	public Z_PickingListItem[] getPickingListItems() {
		return PickingListItems;
	}

	public void setPickingListItems(Z_PickingListItem[] pickingListItems) {
		PickingListItems = pickingListItems;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getPartialDeliveryBin() {
		return PartialDeliveryBin;
	}

	public void setPartialDeliveryBin(String partialDeliveryBin) {
		PartialDeliveryBin = partialDeliveryBin;
	}

	public String getZ_WONum() {
		return Z_WONum;
	}

	public void setZ_WONum(String z_WONum) {
		Z_WONum = z_WONum;
	}

	public String getIsDelivery() {
		return IsDelivery;
	}

	public void setIsDelivery(String isDelivery) {
		IsDelivery = isDelivery;
	}

}
