package com.syclo.sap.mm.table;

//Agentry Classes
import java.util.GregorianCalendar;

import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.Vendor;
import com.syclo.sap.mm.stephandler.CTVendorStepHandler;

public class CTVendor extends com.syclo.sap.ComplexTable 
{
	public CTVendor(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
		Vendor obj = new Vendor();
		String bapiName = "/SYCLO/MM_CTVENDOR_GET";
		_stepHandler = new CTVendorStepHandler(this, _shortClassName, bapiName, obj, _clientLastDataUpdateTime, _rebuilding);
	}

	public void build() throws AgentryException
	{
		super.build();
		if (_tableCheck) {
			try {
				_stepHandler.build();
				setNewDataUpdateTime(_newLastUpdate);
			}
			catch (Exception e) {
				_user.rethrowException(e, true);
			}
		}
	}

	public boolean willRebuildTable() throws AgentryException
	{
		// override this if you want custom reload logic for this table
		boolean retVal = super.willRebuildTable();
		return retVal;
	}
}