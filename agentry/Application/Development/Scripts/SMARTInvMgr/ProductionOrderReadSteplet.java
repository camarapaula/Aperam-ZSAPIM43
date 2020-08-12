/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/inventorymanager/branches/tips/3.0/java/src/com/syclo/sap/mm/steplet/ProductionOrderReadSteplet.java $
 * $Id: ProductionOrderReadSteplet.java 3134 2009-12-16 21:16:38Z dapril $
 *
 * Copyright © 1995-2009 Syclo LLC. All Rights Reserved.
 */

package com.syclo.sap.mm.steplet;

import com.syclo.sap.mm.object.ProductionOrder;
import com.syclo.sap.mm.stephandler.ProductionOrderStepHandler;

import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.AgentryException;

import com.syclo.agentry.TransactionSession;
import com.syclo.sap.Steplet;

public class ProductionOrderReadSteplet extends Steplet {

	/** 
	 * Constructors for Agentry sessions (Transaction, Fetch & Push)
	 * @param session
	 * @throws AgentryException
	 */
	public ProductionOrderReadSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public ProductionOrderReadSteplet(FetchSession session) throws AgentryException {
		super(session);
	}	
	
	public ProductionOrderReadSteplet(PushSession session) throws AgentryException {
		super(session);
	}


	/**
	 * DoSetplet - Replace Production Order data from SAP	 
	 * @see com.syclo.sap.mm.Steplet#doSteplet()
	 */
	public ProductionOrder[] _returnData;	
	public boolean doSteplet() throws AgentryException {
		try {
			com.syclo.sap.mm.User u = (com.syclo.sap.mm.User)_user;
			if (u.getIsProductionOrder()) {
				if (!u.getIsProductionOrderFetch()) {
					ProductionOrderStepHandler handler = new ProductionOrderStepHandler(u);
					_returnData = handler.ProductionOrderRead();
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