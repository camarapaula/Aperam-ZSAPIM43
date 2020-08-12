package com.syclo.sap.component.zaperam.object;

import java.util.ArrayList;

import com.syclo.agentry.BusinessLogicException;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_MaterialFetchBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_PurchaseOrderFetchBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_PurchaseOrderPostBAPI;
import com.syclo.sap.jco.JCO;
import com.syclo.sap.jco.JCO.Table;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_InboundDeliveryDocumentsFetchBAPI,Z_InboundDeliveryDocumentsPostBAPI,
 * Z_PurchaseOrderFetchBAPI, Z_PurchaseOrderPostBAPI, Z_MaterialFetchBAPI and stored into this POJO class via getter and setter methods.
 * */

public class OrderItem extends com.syclo.sap.component.purchasing.object.OrderItem{
	
	public String Z_PONum = "";
	public String Z_LineItemNo = "";
	public String Z_BlockedQuantity = "";
	public String Z_MovingLocationIfBlock = "";
	public String Z_Comment = "";
	public String Z_IsBlocked = "";
	public String Z_IsSelected = "";
	public String Batch = "";
	public String Z_RemainingQuantity= "";
	public String Z_InboundDocStatus= "";
	public String Z_ExpiredDate= "";
	public String Z_IDLineItemNo= "";
	public String Z_GLAccount= "";
	public String Z_CostCenter= "";
	public String Z_OrderNumber= "";
	public String Z_MovementReason = "";
	public String Z_LongText = "";
	public String Z_GenMovementReason = "";
	public String Z_IsAttached = "";
	public String Z_Text= "";
	public String Z_ConsignedQty= "";
	public String Z_StorageBin= "";
	public String Z_Repair= "";
	public Z_DocumentLink[] DocumentLinks = new Z_DocumentLink[0];
	public Z_MaterialStockInformation[] Z_MaterialStockInformations = new Z_MaterialStockInformation[0];

   /*
	* Default Constructor definition for the class
	* */
	
	public OrderItem() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public OrderItem(User user) {
		super(user);
		this._imUser = user;
	}
	
	/*
	 * Method to set the custom values OrderItem from the Z_InboundDeliveryDocumentsFetchBAPI to the respective variables in java
	 */
	
	public void setProperties(Table tbl, Table txtTbl) throws Exception {
		setZ_PONum(tbl.getString("EBELN"));
		setZ_LineItemNo(tbl.getString("EBELP"));
		setZ_IDLineItemNo(tbl.getString("POSNR"));
		setMaterial(tbl.getString("MATNR"));
		setUOM(tbl.getString("MEINS"));
		setQuantity(tbl.getString("LFIMG"));
		//setStorageLocation(tbl.getString("LGORT"));
		setBatch(tbl.getString("CHARG"));
		setZ_GLAccount(tbl.getString("SAKTO"));
		setZ_OrderNumber(tbl.getString("AUFNR"));
		setZ_CostCenter(tbl.getString("KOSTL"));
		setShortText(tbl.getString("TXZ01"));
		setZ_IsSelected("True");
		setID(getOrderID()+getZ_PONum()+getZ_LineItemNo()+getZ_IDLineItemNo()+tbl.getString("VBELN"));
		setZ_LongText(getLongText(getZ_PONum(), getZ_LineItemNo(), txtTbl));
		setZ_BlockedQuantity(tbl.getString("LFIMG_B"));
		setZ_MovingLocationIfBlock(tbl.getString("LGORT_B"));
		if(getZ_MovingLocationIfBlock().length()>0){
			setZ_IsBlocked("True");
		}
	}
	
	/*
	 * Method to set the custom values OrderItem from the Z_PurchaseOrderFetchBAPI to the respective variables in java
	 * */
	
	public void setProperties(Table tbl,Table txtTbl,String _freetext, Z_PurchaseOrderFetchBAPI bapi) throws Exception {
		setZ_PONum(tbl.getString("EBELN"));
		setZ_LineItemNo(tbl.getString("EBELP"));
		setZ_IDLineItemNo(tbl.getString("POSNR"));
		setMaterial(tbl.getString("MATNR"));
		setUOM(tbl.getString("MEINS"));
		setZ_RemainingQuantity(tbl.getString("LFIMG"));
		setZ_InboundDocStatus(tbl.getString("DEL_STATUS"));
		setStorageLocation(tbl.getString("LGORT"));
		setBatch(tbl.getString("CHARG"));
		setZ_GLAccount(tbl.getString("SAKTO"));
		setZ_OrderNumber(tbl.getString("AUFNR"));
		setZ_CostCenter(tbl.getString("KOSTL"));
		setShortText(tbl.getString("TXZ01"));
		setZ_IsSelected("True");
		setID(getOrderID()+getZ_LineItemNo());
	    setZ_Text(_freetext);
		setZ_LongText(getLongText(getZ_PONum(), getZ_LineItemNo(), txtTbl));		
	}
	
	/*
	 * Method to set the custom values OrderItem from the Z_MaterialFetchBAPI to the respective variables in java
	 * */
	
	public void setProperties(Table tbl, Table txtTbl, Table stockTbl, Z_MaterialFetchBAPI bapi) throws Exception {
		// TODO Auto-generated method stub
		setMaterial(tbl.getString("MATNR"));
		setQuantity(tbl.getString("LFIMG"));
		setStorageLocation(tbl.getString("LGORT"));
		setShortText(tbl.getString("TXZ01"));
		setZ_StorageBin(tbl.getString("CHARG"));
		setZ_ConsignedQty(tbl.getString("LFIMG_C"));
		setUOM(tbl.getString("MEINS"));
		if(tbl.getString("STATUS").equals("1")){
		setZ_Repair("Yes");
		}
		else{
			setZ_Repair("No");
		}
		setID(tbl.getString("MATNR"));
		setZ_LongText(getLongText(txtTbl));
		setZ_BlockedQuantity(tbl.getString("LFIMG_B"));
		
		int stockCount = stockTbl.getNumRows();
		ArrayList<Z_MaterialStockInformation> stocks = new ArrayList<Z_MaterialStockInformation>(stockCount);
		for (int i = 0; i < stockCount; ++i) {
			// Z_MaterialStockInformation stock = (Z_MaterialStockInformation) SAPObjectFactory.create(this._user.getSAPObject("Z_MaterialStockInformation"),
			Z_MaterialStockInformation stock = (Z_MaterialStockInformation) SAPObjectFactory.create("com.syclo.sap.component.zaperam.object.Z_MaterialStockInformation",
					new Class[] { User.class }, new Object[] { this._user });
			stockTbl.setRow(i);
			stock.setProperties(stockTbl);
			stocks.add(stock);
		}
		this.Z_MaterialStockInformations = ((Z_MaterialStockInformation[]) (Z_MaterialStockInformation[]) SAPObjectArrayFactory.createSAPObjectArray(stocks,
				SAPObjectFactory.create(_user, "com.syclo.sap.component.zaperam.object.Z_MaterialStockInformation")));
	}
	
	/*
	 * Method to set the custom values OrderItem from the Z_InboundDeliveryDocumentsPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, int i, int j) throws BusinessLogicException {
		try {
			String prefix = "transaction.Z_POrders." + i + ".Items." + j + ".";
			String docPrefix = prefix + "Z_DocumentLinks";
			// if(_user.getBoolean("transaction.Z_POrders." + i + ".Items." + j
			// + ".Z_IsSelected")){
			setMaterial(user.getString(prefix + "Material"));
			setUOM(user.getString(prefix + "UOM"));
			setZ_PONum(user.getString(prefix + "Z_PONum"));
			setZ_LineItemNo(user.getString(prefix + "Z_LineItemNo"));
			setZ_IDLineItemNo(user.getString(prefix + "Z_IDLineItemNo"));
			setStorageLocation(user.getString(prefix + "StorageLocation"));
			setQuantity(user.getString(prefix + "Quantity"));
			setBatch(user.getString(prefix + "Batch"));
			setZ_GLAccount(user.getString(prefix + "Z_GLAccount"));
			setZ_CostCenter(user.getString(prefix + "Z_CostCenter"));
			setZ_OrderNumber(user.getString(prefix + "Z_OrderNumber"));
			setZ_GenMovementReason(user.getString(prefix + "Z_GenMovementReason"));
			setZ_MovementReason(user.getString(prefix + "Z_MovementReason"));
			setZ_Comment(user.getString(prefix + "Z_Comment"));
			if (user.getBoolean(prefix + "Z_IsSelected")) {
				setZ_IsSelected("True");
			}
			if (user.getBoolean(prefix + "Z_IsBlocked")) {
				setZ_IsBlocked("X");
				setZ_BlockedQuantity(user.getString(prefix + "Z_BlockedQuantity"));
				setZ_MovingLocationIfBlock(user.getString(prefix + "Z_MovingLocationIfBlock"));

			}
			// }
			int docCount = Integer.parseInt(this._user.eval("<<size " + docPrefix + ">>"));
			ArrayList<Z_DocumentLink> docs = new ArrayList<Z_DocumentLink>(docCount);
			for (int k = 0; k < docCount; k++) {
				
				Z_DocumentLink doc = (Z_DocumentLink) SAPObjectFactory.create(this._user.getSAPObject("Z_DocumentLink"),
						new Class[] { User.class }, new Object[] { this._user });
				doc.setProperties(user, prefix, k);
				docs.add(doc);

			}
			this.DocumentLinks = ((Z_DocumentLink[]) (Z_DocumentLink[]) SAPObjectArrayFactory.createSAPObjectArray(docs,
					SAPObjectFactory.create(_user, "Z_DocumentLink")));

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}

	/*
	 * Method to set the custom values OrderItem from the Z_PurchaseOrderPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, int i, int j, Z_PurchaseOrderPostBAPI bapi) throws BusinessLogicException {
		try {
			String prefix = "transaction.Orders." + i + ".Items." + j + ".";
			String docPrefix = prefix + "Z_DocumentLinks";
			// if(_user.getBoolean("transaction.Orders." + i + ".Items." + j +
			// ".Z_IsSelected")){
			setMaterial(user.getString(prefix + "Material"));
			setUOM(user.getString(prefix + "UOM"));
			setZ_PONum(user.getString(prefix + "Z_PONum"));
			setZ_LineItemNo(user.getString(prefix + "Z_LineItemNo"));
			setStorageLocation(user.getString(prefix + "StorageLocation"));
			setZ_RemainingQuantity(user.getString(prefix + "Z_RemainingQuantity"));
			setBatch(user.getString(prefix + "Batch"));
			setZ_GLAccount(user.getString(prefix + "Z_GLAccount"));
			setZ_CostCenter(user.getString(prefix + "Z_CostCenter"));
			setZ_OrderNumber(user.getString(prefix + "Z_OrderNumber"));
			setZ_GenMovementReason(user.getString(prefix + "Z_GenMovementReason"));
			setZ_MovementReason(user.getString(prefix + "Z_MovementReason"));
			setZ_Comment(user.getString(prefix + "Z_Comment"));
			setZ_Text(user.getString(prefix + "Z_Text"));
			setZ_ExpiredDate(user.eval("<<" + prefix + "Z_ExpiredDate format=\"%Y%m%d\">>"));
			if (user.getBoolean(prefix + "Z_IsSelected")) {
				setZ_IsSelected("True");
			}
			if (user.getBoolean(prefix + "Z_IsBlocked")) {
				setZ_IsBlocked("X");
				setZ_BlockedQuantity(user.getString(prefix + "Z_BlockedQuantity"));
				setZ_MovingLocationIfBlock(user.getString(prefix + "Z_MovingLocationIfBlock"));

			}
			// }
			int docCount = Integer.parseInt(this._user.eval("<<size " + docPrefix + ">>"));
			ArrayList<Z_DocumentLink> docs = new ArrayList<Z_DocumentLink>(docCount);
			for (int k = 0; k < docCount; k++) {

				Z_DocumentLink doc = (Z_DocumentLink) SAPObjectFactory.create(this._user.getSAPObject("Z_DocumentLink"),
						new Class[] { User.class }, new Object[] { this._user });
				doc.setProperties(user, prefix, k);
				docs.add(doc);

			}
			this.DocumentLinks = ((Z_DocumentLink[]) (Z_DocumentLink[]) SAPObjectArrayFactory.createSAPObjectArray(docs,
					SAPObjectFactory.create(_user, "Z_DocumentLink")));

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}
	
	/*method to get the long text for the respective OrderItem object*/
	
	public String getLongText(String objKey, String objitemKey, JCO.Table notesTable)
		    throws Exception
		  {
		    String str = "";
		    if (notesTable == null) {
		      return str;
		    }
		    boolean firstLine = true;
		    for (int i = 0; i < notesTable.getNumRows(); i++)
		    {
		      notesTable.setRow(i);
		      String docKey = notesTable.getString("DOC_NUMBER");
		      String itemKey = notesTable.getString("ITM_NUMBER");
		      String thisItemKey = removeLeadingZeros(itemKey);
		      this._log.debug("Doc Key " + docKey + " Item Key " + thisItemKey + "Po Num " + objKey + " Item No " + removeLeadingZeros(objitemKey));
		      if (docKey.equalsIgnoreCase(objKey) && thisItemKey.equalsIgnoreCase(removeLeadingZeros(objitemKey)))
		      {
		        String formatFlag = notesTable.getString("FORMAT_COL");
		        if (firstLine) {
		          str = str + notesTable.getString("TEXT_LINE");
		        } else if (formatFlag.trim().equals("*")) {
		          str = str + " " + notesTable.getString("TEXT_LINE");
		        } else {
		          str = str + "\n" + notesTable.getString("TEXT_LINE");
		        }
		        if (firstLine == true) {
		          firstLine = false;
		        }
		      }
		    }
		    this._log.debug("Long Text : " + str);
		    return replaceNewLineInText(str);
		  }
		  
	public String getLongText(JCO.Table notesTable)
		    throws Exception
		  {
		    String str = "";
		    if (notesTable == null) {
		      return str;
		    }
		    boolean firstLine = true;
		    for (int i = 0; i < notesTable.getNumRows(); i++)
		    {   
		    	notesTable.setRow(i);
		        String formatFlag = notesTable.getString("XLOEK");
		        if (firstLine) {
		          str = str + notesTable.getString("TDLINE");
		        } else if (formatFlag.trim().equals("*")) {
		          str = str + " " + notesTable.getString("TDLINE");
		        } else {
		          str = str + "\n" + notesTable.getString("TDLINE");
		        }
		        if (firstLine == true) {
		          firstLine = false;
		        }
		    }
		    this._log.debug("Long Text : " + str);
		    return replaceNewLineInText(str);
		  }


	public String getZ_PONum() {
		return Z_PONum;
	}

	public void setZ_PONum(String purchaseOrderNumber) {
		Z_PONum = purchaseOrderNumber;
	}
	
	public String getZ_LineItemNo() {
		return Z_LineItemNo;
	}

	public void setZ_LineItemNo(String z_LineItemNo) {
		Z_LineItemNo = z_LineItemNo;
	}

	public String getZ_BlockedQuantity() {
		return Z_BlockedQuantity;
	}

	public void setZ_BlockedQuantity(String z_BlockedQuantity) {
		Z_BlockedQuantity = z_BlockedQuantity;
	}

	public String getZ_MovingLocationIfBlock() {
		return Z_MovingLocationIfBlock;
	}

	public void setZ_MovingLocationIfBlock(String z_MovingLocationIfBlock) {
		Z_MovingLocationIfBlock = z_MovingLocationIfBlock;
	}

	public String getZ_Comment() {
		return Z_Comment;
	}

	public void setZ_Comment(String z_Comment) {
		Z_Comment = z_Comment;
	}

	public String getZ_IsBlocked() {
		return Z_IsBlocked;
	}

	public void setZ_IsBlocked(String z_IsBlocked) {
		Z_IsBlocked = z_IsBlocked;
	}

	public String getZ_IsSelected() {
		return Z_IsSelected;
	}

	public void setZ_IsSelected(String z_IsSelected) {
		Z_IsSelected = z_IsSelected;
	}

	public String getBatch() {
		return Batch;
	}

	public void setBatch(String batch) {
		Batch = batch;
	}

	public String getZ_RemainingQuantity() {
		return Z_RemainingQuantity;
	}

	public void setZ_RemainingQuantity(String z_RemainingQuantity) {
		Z_RemainingQuantity = z_RemainingQuantity;
	}

	public String getZ_InboundDocStatus() {
		return Z_InboundDocStatus;
	}

	public void setZ_InboundDocStatus(String z_InboundDocStatus) {
		Z_InboundDocStatus = z_InboundDocStatus;
	}

	public String getZ_ExpiredDate() {
		return Z_ExpiredDate;
	}

	public void setZ_ExpiredDate(String z_ExpiredDate) {
		Z_ExpiredDate = z_ExpiredDate;
	}

	public String getZ_IDLineItemNo() {
		return Z_IDLineItemNo;
	}

	public void setZ_IDLineItemNo(String z_IDLineItemNo) {
		Z_IDLineItemNo = z_IDLineItemNo;
	}

	public String getZ_GLAccount() {
		return Z_GLAccount;
	}

	public void setZ_GLAccount(String z_GLAccount) {
		Z_GLAccount = z_GLAccount;
	}

	public String getZ_CostCenter() {
		return Z_CostCenter;
	}

	public void setZ_CostCenter(String z_CostCenter) {
		Z_CostCenter = z_CostCenter;
	}

	public String getZ_OrderNumber() {
		return Z_OrderNumber;
	}

	public void setZ_OrderNumber(String z_OrderNumber) {
		Z_OrderNumber = z_OrderNumber;
	}

	public String getZ_MovementReason() {
		return Z_MovementReason;
	}

	public void setZ_MovementReason(String z_MovementReason) {
		Z_MovementReason = z_MovementReason;
	}

	public String getZ_LongText() {
		return Z_LongText;
	}

	public void setZ_LongText(String z_LongText) {
		Z_LongText = z_LongText;
	}	
	
	public String getZ_GenMovementReason() {
		return Z_GenMovementReason;
	}

	public void setZ_GenMovementReason(String z_GenMovementReason) {
		Z_GenMovementReason = z_GenMovementReason;
	}

	public Z_DocumentLink[] getDocumentLinks() {
		return DocumentLinks;
	}

	public void setDocumentLinks(Z_DocumentLink[] documentLinks) {
		DocumentLinks = documentLinks;
	}

	public String getZ_IsAttached() {
		return Z_IsAttached;
	}

	public void setZ_IsAttached(String z_IsAttached) {
		Z_IsAttached = z_IsAttached;
	}

	public String getZ_Text() {
		return Z_Text;
	}

	public void setZ_Text(String z_Text) {
		Z_Text = z_Text;
	}

	public String getZ_ConsignedQty() {
		return Z_ConsignedQty;
	}

	public void setZ_ConsignedQty(String z_ConsignedQty) {
		Z_ConsignedQty = z_ConsignedQty;
	}

	public String getZ_StorageBin() {
		return Z_StorageBin;
	}

	public void setZ_StorageBin(String z_StorageBin) {
		Z_StorageBin = z_StorageBin;
	}

	public String getZ_Repair() {
		return Z_Repair;
	}

	public void setZ_Repair(String z_Repair) {
		Z_Repair = z_Repair;
	}

	public Z_MaterialStockInformation[] getZ_MaterialStockInformations() {
		return Z_MaterialStockInformations;
	}

	public void setZ_MaterialStockInformations(Z_MaterialStockInformation[] z_MaterialStockInformations) {
		Z_MaterialStockInformations = z_MaterialStockInformations;
	}
	
}
 
