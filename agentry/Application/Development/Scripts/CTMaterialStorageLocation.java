package com.syclo.sap.mm.table;

//java classes
import java.util.GregorianCalendar;

//Agentry Classes
import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.MaterialStorageLocation;
import com.syclo.sap.mm.stephandler.CTMaterialStorageLocationStepHandler;

public class CTMaterialStorageLocation extends com.syclo.sap.ComplexTable 
{
	public CTMaterialStorageLocation(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
		MaterialStorageLocation obj = new MaterialStorageLocation();
		String bapiName = "/SYCLO/MM_CTMATSLOC_GET";
		_stepHandler = new CTMaterialStorageLocationStepHandler(this, _shortClassName, bapiName, obj, _clientLastDataUpdateTime, _rebuilding);
	}

	public void build() throws AgentryException
	{
		super.build();
		if (_tableCheck) {
			try {
				//instantiate the handler that will process the create work order
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