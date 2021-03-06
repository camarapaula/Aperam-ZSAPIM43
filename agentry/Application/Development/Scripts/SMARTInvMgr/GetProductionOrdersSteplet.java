/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/rounds/branches/tips/2.0/java/src/Syclo/sap/rounds/stephandler/CTCodeGroupStepHandler.java $
 * $Id: CTCodeGroupStepHandler.java 2008-03-12 09:04:01Z cayyagari $
 *
 * Copyright © 1995-2008 Syclo LLC. All Rights Reserved.
 */

package com.syclo.sap.mm.steplet;

import com.syclo.sap.mm.object.ProductionOrder;
import com.syclo.sap.mm.stephandler.ProductionOrderStepHandler;

import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.AgentryException;

import com.syclo.agentry.TransactionSession;
import com.syclo.sap.Steplet;


public class GetProductionOrdersSteplet extends Steplet {
	
	/** 
	 * Constructors for Agentry sessions (Transaction, Fetch & Push)
	 * @param session
	 * @throws AgentryException
	 */
	public GetProductionOrdersSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}
	public GetProductionOrdersSteplet(FetchSession session) throws AgentryException {
		super(session);
	}
	public GetProductionOrdersSteplet(PushSession session) throws AgentryException {
		super(session);
	}
			
	
	/* (non-Javadoc)
	 * @see Syclo.sap.Steplet#doSteplet()
	 */
	public ProductionOrder[] _returnData;	
	public boolean doSteplet() throws AgentryException {
		try {
			com.syclo.sap.mm.User u = (com.syclo.sap.mm.User)_user;
			u.setIsProductionOrder(true);
			u.setIsPurchaseOrderFetch(false);
			u.setIsProductionOrderFetch(true);
			ProductionOrderStepHandler handler = new ProductionOrderStepHandler(u);
			_returnData  = handler.getProductionOrders();			
			return true;
		}
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}

}
