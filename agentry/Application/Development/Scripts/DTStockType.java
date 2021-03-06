package com.syclo.sap.mm.table;

//syclo classes
import com.syclo.agentry.DataTableObject;
import com.syclo.agentry.DataTableSession;
import com.syclo.agentry.AgentryException;
import java.util.GregorianCalendar;

//inventory manager classes
import com.syclo.sap.mm.User;
import com.syclo.sap.mm.stephandler.DTStockTypeStepHandler;

public class DTStockType extends com.syclo.sap.DataTable<DataTableObject> 
{
	public DTStockType(DataTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
	}

	public void initialize() throws AgentryException {
		super.initialize();
		try {		
			if (_tableCheck) {
				DTStockTypeStepHandler handler = new DTStockTypeStepHandler((User) _user, _shortClassName, _clientLastDataUpdateTime, _rebuilding);
				_objList = handler.build();
			}
		}
		catch (Throwable t) {
			rethrowException(t, true);				
		}
	}

	public boolean isOutOfDate() throws AgentryException	{
		try {	
			if (_tableCheck) {
			// override this if you want custom refresh logic for this table
			boolean retVal = super.isOutOfDate();
			return retVal;
			}
		}
		catch (Throwable t) {
			rethrowException(t, true);				
		}
		return false;
	}


}