package com.github.stephenenright.typemapper.test.dto.vending;

abstract class BaseDto {

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
