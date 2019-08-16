package com.mobplug.games.framework.entities;

public class Point3D extends Point2D {
	private static final long serialVersionUID = 1L;
	
	private float z;
	
	public Point3D(float x, float y, float z) {
		super(x, y);
		this.z = z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public float getZ() {
		return this.z;
	}
}
