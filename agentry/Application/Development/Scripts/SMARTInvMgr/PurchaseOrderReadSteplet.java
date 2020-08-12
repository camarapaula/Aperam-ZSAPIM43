/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/inventorymanager/branches/tips/3.0/java/src/com/syclo/sap/mm/steplet/PurchaseOrderReadSteplet.java $
 * $Id: PurchaseOrderReadSteplet.java 3134 2009-12-16 21:16:38Z dapril $
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

public class PurchaseOrderReadSteplet extends Steplet {

	/** 
	 * Constructors for Agentry sessions (Transaction, Fetch & Push)
	 * @param session
	 * @throws AgentryException
	 */
	public PurchaseOrderReadSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public PurchaseOrderReadSteplet(FetchSession session) throws AgentryException {
		super(session);
	}	
	
	public PurchaseOrderReadSteplet(PushSession session) throws AgentryException {
		super(session);
	}


	/**
	 * DoSetplet - Replace Purchase Order data from SAP	 
	 * @see com.syclo.sap.mm.Steplet#doSteplet()
	 */
	public PurchaseOrder[] _returnData;	
	public boolean doSteplet() throws AgentryException {
		try {
			com.syclo.sap.mm.User u = (com.syclo.sap.mm.User)_user;
			if (u.getIsPurchaseOrder()) {
				if (!u.getIsPurchaseOrderFetch()) {
					PurchaseOrderStepHandler handler = new PurchaseOrderStepHandler(u);
					_returnData = handler.PurchaseOrderRead();
				}
			}
			return true;
		}
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}	
}