/*
 * $HeadURL: http://habanero/repos/ProductDev/sap/inventorymanager/branches/tips/3.0/java/src/com/syclo/sap/mm/stephandler/CTMaterialUOM.java $
 * $Id: CTMaterialUOM.java 2009-03-04 16:51:01Z cayyagari $
 *
 * Copyright © 1995-2008 Syclo LLC. All Rights Reserved.
 */


package com.syclo.sap.mm.table;

//java classes
import java.util.GregorianCalendar;

//Agentry Classes
import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.MaterialUOM;
import com.syclo.sap.mm.stephandler.CTMaterialUOMStepHandler;

public class CTMaterialUOM extends com.syclo.sap.ComplexTable 
{
	public CTMaterialUOM(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);	
		MaterialUOM obj = new MaterialUOM();
		String bapiName = "/SYCLO/MM_CTMATUOM_GET";
		_stepHandler = new CTMaterialUOMStepHandler(this, _shortClassName, bapiName, obj, _clientLastDataUpdateTime, _rebuilding);
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
		if (_tableCheck) {
			try {
				boolean retVal = super.willRebuildTable();
				return retVal;
			}
			catch (Exception e) {
				_user.rethrowException(e, true);
			}
		}
		return false;
	}
}