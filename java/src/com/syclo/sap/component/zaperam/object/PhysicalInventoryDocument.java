package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.object.Material;

public class PhysicalInventoryDocument extends com.syclo.sap.component.physicalinventory.object.PhysicalInventoryDocument{
	
	public PhysicalInventoryDocument() {
	}

	public PhysicalInventoryDocument(User user) {
		super(user);
		this._imUser = user;
	}
	
	public Material[] Materials;

//	public Material[] getMaterials() {
//		return Materials;
//	}
//
//	public void setMaterials(Material[] materials) {
//		Materials = materials;
//	}

}
