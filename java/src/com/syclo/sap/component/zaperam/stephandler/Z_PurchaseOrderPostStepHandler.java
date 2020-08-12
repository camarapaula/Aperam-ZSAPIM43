package com.syclo.sap.component.zaperam.stephandler;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_PurchaseOrderPostBAPI;
import com.syclo.sap.component.zaperam.object.Order;
import com.syclo.sap.component.zaperam.object.OrderItem;
import com.syclo.sap.component.zaperam.object.Z_DocumentLink;

/*This Step handler class is called by the Z_PurchaseOrderPostSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_PurchaseOrderPostStepHandler extends StepHandler{
	public Z_PurchaseOrderPostStepHandler(User user) {
		super(user);
	}
	
	/*
	 * This method will be used to invoke the BAPI class, Z_PurchaseOrderPostBAPI,
	 * and the invoke the BAPI class with the parameters order, orderitem, documentLinks for the picture attachement.
	 */
	
	public void run() throws AgentryException {
		try {
			Z_PurchaseOrderPostBAPI bapi = (Z_PurchaseOrderPostBAPI) BAPIFactory.create(
					"Z_PurchaseOrderPostBAPI", new Class[] { User.class, GregorianCalendar.class },
					new Object[] { this._user, new GregorianCalendar() });

			bapi.run(null);
			ArrayList<Order> inboundOrders = bapi.processResults();
			for(int k = 0; k<inboundOrders.size();k++){
				int itemsCount = inboundOrders.get(k).getItems().length;
				for (int m = 0; m < itemsCount ; m++){
					if(inboundOrders.get(k).getItems()[m].getDocumentLinks().length>0){
						int docCount = inboundOrders.get(k).getItems()[m].getDocumentLinks().length;
						for (int n = 0; n < docCount ; n++){
						Z_PurchaseOrderPostBAPI bapidoc = (Z_PurchaseOrderPostBAPI) BAPIFactory.create(
								"Z_PurchaseOrderPostBAPI", new Class[] { User.class, GregorianCalendar.class, Order.class, OrderItem.class, Z_DocumentLink.class },
								new Object[] { this._user, new GregorianCalendar(), inboundOrders.get(k), inboundOrders.get(k).getItems()[m], inboundOrders.get(k).getItems()[m].getDocumentLinks()[n] });

						bapidoc.run(null);
						}
					}
				}
				
			}
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}

}

