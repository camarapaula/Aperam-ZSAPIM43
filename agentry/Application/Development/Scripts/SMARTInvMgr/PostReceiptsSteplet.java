/*
 * $Filename$
 * $Author$
 *
 * $Log3$
 *
 * Copyright © 1995-2009 Syclo LLC. All Rights Reserved.
 */


package com.syclo.sap.mm.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.mm.Steplet;
import com.syclo.sap.mm.object.Order;
import com.syclo.sap.mm.stephandler.GoodsMovementStepHandler;
import com.syclo.sap.mm.stephandler.PurchaseOrderStepHandler;

public class SendReceiptsSteplet extends Steplet {

	/**
	 * @param session
	 * @throws AgentryException
	 */
	public SendReceiptsSteplet(TransactionSession session)
			throws AgentryException {
		super(session);
	}

	/**
	 * @param session
	 * @throws AgentryException
	 */
	public SendReceiptsSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	/**
	 * @param session
	 * @throws AgentryException
	 */
	public SendReceiptsSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	public Order[] _returnData; //return null

	public boolean doSteplet() throws AgentryException {
		try {
			com.syclo.sap.mm.User u = (com.syclo.sap.mm.User)_user;
			GoodsMovementStepHandler handler = new GoodsMovementStepHandler(u);
			handler.sendReceipts();
			PurchaseOrderStepHandler handler2 = new PurchaseOrderStepHandler((com.syclo.sap.mm.User)_user);
			_returnData = handler2.replacePurchaseOrders();
			return true;		
		}	
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}
}
