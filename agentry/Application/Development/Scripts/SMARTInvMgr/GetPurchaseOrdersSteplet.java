/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/rounds/branches/tips/2.0/java/src/Syclo/sap/rounds/stephandler/CTCodeGroupStepHandler.java $
 * $Id: CTCodeGroupStepHandler.java 2008-03-12 09:04:01Z cayyagari $
 *
 * Copyright © 1995-2008 Syclo LLC. All Rights Reserved.
 */

package com.syclo.sap.mm.steplet;

import com.syclo.sap.mm.object.PurchaseOrder;
import com.syclo.sap.mm.stephandler.PurchaseOrderStepHandler;

import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.AgentryException;

import com.syclo.agentry.TransactionSession;
import com.syclo.sap.Steplet;

public class GetPurchaseOrdersSteplet extends Steplet {
	
	/** 
	 * Constructors for Agentry sessions (Transaction, Fetch & Push)
	 * @param session
	 * @throws AgentryException
	 */
	public GetPurchaseOrdersSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}
	public GetPurchaseOrdersSteplet(FetchSession session) throws AgentryException {
		super(session);
	}	public GetPurchaseOrdersSteplet(PushSession session) throws AgentryException {
		super(session);
	}
	
	
	/**
	 * DoSetplet - Get Purchase Order data from SAP	 
	 * @see com.syclo.sap.mm.Steplet#doSteplet()
	 */
	public PurchaseOrder[] _returnData;	
	public boolean doSteplet() throws AgentryException {
		try {
			com.syclo.sap.mm.User u = (com.syclo.sap.mm.User)_user;
			u.setIsPurchaseOrderFetch(true);
			u.setIsProductionOrderFetch(false);
			u.setIsProductionOrder(false);
			PurchaseOrderStepHandler handler = new PurchaseOrderStepHandler(u);
			_returnData = handler.getPurchaseOrders();
			_user.inFetch(true);
			return true;
		}
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}	
}
