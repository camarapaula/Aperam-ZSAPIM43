/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/rounds/branches/tips/2.0/java/src/Syclo/sap/rounds/stephandler/CTCodeGroupStepHandler.java $
 * $Id: CTCodeGroupStepHandler.java 2008-03-12 09:04:01Z cayyagari $
 *
 * Copyright © 1995-2008 Syclo LLC. All Rights Reserved.
 */

package com.syclo.sap.mm.steplet;

//Syclo Classes
import com.syclo.sap.mm.User;
import com.syclo.sap.mm.object.GoodsMovementDocument;
import com.syclo.sap.mm.stephandler.ProductionOrderStepHandler;

import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.AgentryException;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.Steplet;

//Inventory Manager Classes

public class GetGoodsMovementDocumentsSteplet extends Steplet {

	/** 
	 * Constructors for Agentry sessions (Transaction, Fetch & Push)
	 * @param session
	 * @throws AgentryException
	 */
	public GetGoodsMovementDocumentsSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}
	public GetGoodsMovementDocumentsSteplet(FetchSession session) throws AgentryException {
		super(session);
	}
	public GetGoodsMovementDocumentsSteplet(PushSession session) throws AgentryException {
		super(session);
	}
	
	
	/* (non-Javadoc)
	 * @see Syclo.sap.Steplet#doSteplet()
	 */
	public GoodsMovementDocument[] _returnData;
	public boolean doSteplet() throws AgentryException {
		try {
			ProductionOrderStepHandler handler = new ProductionOrderStepHandler((User) _user);
			_returnData = handler.getGoodsMovementDocuments();			
			return true;		
		}
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}

}
