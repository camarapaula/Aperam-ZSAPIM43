package com.syclo.sap.mm.table;

//Java Classes
import java.util.GregorianCalendar;

//Agentry Classes
import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.StorageLocation;
import com.syclo.sap.mm.stephandler.CTStorageLocationStepHandler;

public class CTStorageLocation extends com.syclo.sap.ComplexTable 
{
	public CTStorageLocation(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
		_user.log("CTStorageLocation::CTStorageLocation()");
		StorageLocation obj = new StorageLocation();
		String bapiName = "/SYCLO/MM_CTSLOC_GET";
		_stepHandler = new CTStorageLocationStepHandler(this, _shortClassName, bapiName, obj, _clientLastDataUpdateTime, _rebuilding);
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