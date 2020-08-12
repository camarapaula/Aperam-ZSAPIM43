package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.Steplet;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_InboundDeliveryDocumentsFetchStepHandler;

/*
 * This class is invoked from the Agentry steplet that fetches data from backend. It creates the
 * session and Invokes the Stephandler method.
 * */

public class Z_InboundDeliveryDocumentsFetchSteplet extends Steplet{
	public Z_InboundDeliveryDocumentsFetchSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_InboundDeliveryDocumentsFetchSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_InboundDeliveryDocumentsFetchSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	/*
	 * This Execute method will be called from Agentry step and passes the control to 
	 * Z_InboundDeliveryDocumentsFetchStepHandler class.This method is overridden from the Steplet class.
	 * */
	
	@Override
	public boolean execute() throws AgentryException {
		try {
			Z_InboundDeliveryDocumentsFetchStepHandler handler = (Z_InboundDeliveryDocumentsFetchStepHandler) StepHandlerFactory
					.create(StepHandlerFactory.getClassName("Z_InboundDeliveryDocumentsFetchStepHandler"),
							new Class[] { User.class }, new Object[] { this._user });

			this._returnData = handler.getDocs();
			return true;
		} catch (Throwable e) {
			throwExceptionToClient(e);
		}
		return false;
	}

}



