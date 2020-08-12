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
import com.syclo.sap.mm.User;
import com.syclo.sap.mm.object.Material;
import com.syclo.sap.mm.stephandler.PhysicalInventoryStepHandler;

public class PostCountsSteplet extends Steplet {

	/**
	 * @param session
	 * @throws AgentryException
	 */
	public PostCountsSteplet(TransactionSession session)
			throws AgentryException {
		super(session);
	}

	/**
	 * @param session
	 * @throws AgentryException
	 */
	public PostCountsSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	/**
	 * @param session
	 * @throws AgentryException
	 */
	public PostCountsSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	/** 
	 * Get Physical Inventory Item data
	 * @see com.syclo.sap.mm.Steplet#doSteplet()
	 */
	public Material[] _returnData;  //return null
	public boolean doSteplet() throws AgentryException {
		try {
			PhysicalInventoryStepHandler handler = new PhysicalInventoryStepHandler((User) _user);
			handler.count();
			return true;		
		}	
		catch (Throwable e)
		{
			throwExceptionToClient(e);
			return false;
		}
	}
}
