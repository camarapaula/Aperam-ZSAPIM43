package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.ConversionUtility;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Order;
import com.syclo.sap.component.zaperam.object.OrderItem;
import com.syclo.sap.component.zaperam.object.Z_DocumentLink;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */

public class Z_PurchaseOrderPostBAPI extends BAPI {

	public ArrayList<Order> _inboundDocs = new ArrayList<Order>();
	public Order inboundOrd = null;
	public OrderItem inboundOrdItem = null;
	public Z_DocumentLink doclink = null;
	public String isdocAttachment = "";

	/*
	 * This method is to call the set properties method for the Order object
	 * */
	
	public Z_PurchaseOrderPostBAPI(User u, GregorianCalendar lu) throws Exception {
		super(u, lu);
		// TODO Auto-generated constructor stub
		int docCount = Integer.parseInt(this._user.eval("<<size transaction.Orders>>"));
		for (int i = 0; i < docCount; i++) {

			Order _inboundDoc = (Order) SAPObjectFactory.create(this._user.getSAPObject("Z_Order"),
					new Class[] { User.class }, new Object[] { this._user });
			_inboundDoc.setProperties(_user, this, i);
			_inboundDocs.add(_inboundDoc);
		}
	}
	
	/* This method is to pass and set the arguments values to the java variables*/
	
	public Z_PurchaseOrderPostBAPI(User u, GregorianCalendar lu, Order ord, OrderItem item, Z_DocumentLink _doclink) throws Exception {
		super(u, lu);
		inboundOrd = ord;
		inboundOrdItem = item;
		doclink = _doclink;
		isdocAttachment = "X";
	}

	/*
	 * custom setParameters() method for Z_PurchaseOrderPostBAPI to save the custom values to the IT_RECEIPT.
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			if (isdocAttachment.length() == 0) {
				JCO.Table receiptTbl = this._tables.getTable("IT_RECEIPT");
				int docCount = _inboundDocs.size();
				for (int j = 0; j < docCount; j++) {
					int itemCount = _inboundDocs.get(j).getItems().length;
					for (int i = 0; i < itemCount; i++) {
						if (_inboundDocs.get(j).getItems()[i].Z_IsSelected.length() > 0
								&& Double.parseDouble(_inboundDocs.get(j).getItems()[i].getZ_RemainingQuantity()) > 0) {
							receiptTbl.appendRow();
							// String imageString =
							// _inboundDocs.get(j).getItems()[i].getDocumentLinks()[k].getPicture();
							setValue(receiptTbl, "MATNR", _inboundDocs.get(j).getItems()[i].getMaterial());
							setValue(receiptTbl, "MEINS", _inboundDocs.get(j).getItems()[i].getUOM());
							setValue(receiptTbl, "EBELN", _inboundDocs.get(j).getItems()[i].getZ_PONum());
							setValue(receiptTbl, "EBELP", _inboundDocs.get(j).getItems()[i].getZ_LineItemNo());
							setValue(receiptTbl, "CHARG", _inboundDocs.get(j).getItems()[i].getBatch());
							setValue(receiptTbl, "SAKTO", _inboundDocs.get(j).getItems()[i].getZ_GLAccount());
							setValue(receiptTbl, "AUFNR", _inboundDocs.get(j).getItems()[i].getZ_OrderNumber());
							setValue(receiptTbl, "KOSTL", _inboundDocs.get(j).getItems()[i].getZ_CostCenter());
							setValue(receiptTbl, "GRUND_UN",
									_inboundDocs.get(j).getItems()[i].getZ_GenMovementReason());
							setValue(receiptTbl, "GRUND", _inboundDocs.get(j).getItems()[i].getZ_MovementReason());
							setValue(receiptTbl, "COMMENTS", _inboundDocs.get(j).getItems()[i].getZ_Comment());
							setValue(receiptTbl, "VERUR", _inboundDocs.get(j).getItems()[i].getZ_Text());
							if (_user.getString("transaction.Z_MovingLocation").length() > 3) {
								setValue(receiptTbl, "LGORT", _user.getString("transaction.Z_MovingLocation"));
							} else {
								setValue(receiptTbl, "LGORT", _inboundDocs.get(j).getItems()[i].getStorageLocation());
							}
							setValue(receiptTbl, "BDATE",
									_user.eval("<<transaction.Z_BookingDate format=\"%Y%m%d\">>"));
							if (_inboundDocs.get(j).getItems()[i].getZ_BlockedQuantity().length() > 0 && Double
									.parseDouble(_inboundDocs.get(j).getItems()[i].getZ_BlockedQuantity()) > 0) {
								Double qty = Double
										.parseDouble(_inboundDocs.get(j).getItems()[i].getZ_RemainingQuantity())
										- Double.parseDouble(_inboundDocs.get(j).getItems()[i].getZ_BlockedQuantity());
								setValue(receiptTbl, "LFIMG", qty);
							} else {
								setValue(receiptTbl, "LFIMG",
										_inboundDocs.get(j).getItems()[i].getZ_RemainingQuantity());
							}
							setValue(receiptTbl, "WERKS", _user.getString("transaction.Plant"));
							if (!_inboundDocs.get(j).getItems()[i].getZ_ExpiredDate().equals("00000101")) {
								setValue(receiptTbl, "EXP_DATE", _inboundDocs.get(j).getItems()[i].getZ_ExpiredDate());
							}
							if (_user.getString("transaction.Z_NeedsGR").equals("True")) {
								setValue(receiptTbl, "WBSTK", "X");
								setValue(receiptTbl, "BWART", "101");
							}
							if (_inboundDocs.get(j).getItems()[i].Z_IsBlocked.equalsIgnoreCase("X")) {
								// receiptTbl.appendRow();
								setValue(receiptTbl, "LFIMG_B",
										_inboundDocs.get(j).getItems()[i].getZ_BlockedQuantity());
								setValue(receiptTbl, "LGORT_B",
										_inboundDocs.get(j).getItems()[i].getZ_MovingLocationIfBlock());

							}

						}
					}
				}
			} else {
				if (inboundOrd != null && inboundOrdItem != null) {
					if (inboundOrd.getZ_InboundDocNo().length() > 0) {
						setValue(_imports, "IV_OBJKEY", inboundOrd.getZ_InboundDocNo());
						//int doclinkCount = inboundOrdItem.getDocumentLinks().length;
						//for (int k = 0; k < doclinkCount; k++) {
							String imageString = "";
							imageString = doclink.getPicture();
							byte[] bytes = ConversionUtility.hexStringToByteArray(imageString);
							setValue(_imports, "IV_FILENAME", doclink.getFileName());
							setValue(_imports, "IV_SIZE", String.valueOf(bytes.length));
							setValue(_imports, "IV_FILE", bytes);
						//}
					}
				}
			}

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	/*This method is overridden from the standard BAPI class.
	 * The method is to retrieve back the values from ET_RECEIPT table, sap .
     */
	
	@Override
	public ArrayList<Order> processResults() throws Exception {
		// TODO Auto-generated method stub
		try {
			JCO.Table _Inboundtbl = this._tables.getTable("ET_RECEIPT");
			// JCO.Table _selectedtbl = _Inboundtbl;
			this._log.debug("rows returned: {}", new Object[] { Integer.valueOf(_Inboundtbl.getNumRows()) });
			int numHdrRecs = _Inboundtbl.getNumRows();
			for (int i = 0; i < numHdrRecs; ++i) {
				_Inboundtbl.setRow(i);
				for (int j = 0; j < _inboundDocs.size(); j++) {
					if (_Inboundtbl.getString("EBELN").equalsIgnoreCase(_inboundDocs.get(j).getOrderID())) {
						_inboundDocs.get(j).setZ_InboundDocNo(_Inboundtbl.getString("MBLNR") + _Inboundtbl.getString("GJAHR"));
					}
				}
			}

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(_inboundDocs.size()));
		return _inboundDocs;
	}

}
