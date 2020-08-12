/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/rounds/branches/tips/2.0/java/src/Syclo/sap/rounds/stephandler/CTCodeGroupStepHandler.java $
 * $Id: CTCodeGroupStepHandler.java 2008-03-12 09:04:01Z cayyagari $
 *
 * Copyright © 1995-2008 Syclo LLC. All Rights Reserved.
 */

package com.syclo.sap.mm.steplet;

//Syclo classes
import com.syclo.sap.mm.User;
import com.syclo.sap.mm.object.PhysicalInventoryDocument;
import com.syclo.sap.mm.stephandler.PhysicalInventoryStepHandler;

import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.AgentryException;

import com.syclo.agentry.TransactionSession;
import com.syclo.sap.Steplet;

//Inventory Manager classes


public class GetPhysicalInventoryDocumentsSteplet extends Steplet {

	/** 
	 * Constructors for Agentry sessions (Transaction, Fetch & Push)
	 * @param session
	 * @throws AgentryException
	 */
	public GetPhysicalInventoryDocumentsSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}
	public GetPhysicalInventoryDocumentsSteplet(FetchSession session) throws AgentryException {
		super(session);
	}
	public GetPhysicalInventoryDocumentsSteplet(PushSession session) throws AgentryException {
		super(session);
	}
	
	
	/** 
	 * Get Physical Inventory Documents
	 * @see com.syclo.sap.mm.Steplet#doSteplet()
	 */
	public PhysicalInventoryDocument[] _returnData;
	public boolean doSteplet() throws AgentryException {
		try {
			PhysicalInventoryStepHandler handler = new PhysicalInventoryStepHandler((User) _user);
			_returnData = handler.getPhysicalInventoryDocuments();			
			return true;
		}		
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}

}
