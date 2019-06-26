package com.shoot.common;

public class KLogBean {
	public String msg;
	public String module;
	public int level;
	
	public KLogBean(int level, String module, String msg) {
		this.level = level;
		this.module = module;
		this.msg = msg;
	}
	
	@Override
	public String toString() {
		return "" + level + " " + module + " " + msg;
	}
}
