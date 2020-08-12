package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.Steplet;

/*
 * This class is invoked from the Agentry steplet that creates the sessions. 
 * */

public class Z_ErrorHandlingSteplet extends Steplet{

	public Z_ErrorHandlingSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_ErrorHandlingSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	public Z_ErrorHandlingSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public boolean execute() throws AgentryException {

		return true;
	}
}
