package com.stephenenright.typemapper.test.models.vending;

abstract class BaseModel {

	protected String id;

	protected boolean deleted = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
