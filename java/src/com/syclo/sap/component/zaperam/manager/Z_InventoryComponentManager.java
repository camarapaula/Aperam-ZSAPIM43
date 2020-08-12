package com.syclo.sap.component.zaperam.manager;

import com.syclo.sap.ComponentManager;
import com.syclo.sap.SAPObject;

public class Z_InventoryComponentManager extends ComponentManager<SAPObject>
{
	protected String _partnerGUID = null;

	public static final String COMPONENT_MANAGER_CLASS_NAME = Z_InventoryComponentManager.class.getCanonicalName();
	public static String OBJ_TYPE = "IM";

	public Z_InventoryComponentManager() {
		setObjectType(OBJ_TYPE);
	}
	
	public String getPartnerGUID() {
		return _partnerGUID.toUpperCase();
	}

	public String getPartnerGUIDString() throws Exception {
		return getPartnerGUID();
	}

	public void setPartnerGUID(String partnerGUID) {
		this._partnerGUID = partnerGUID;
	}
}
