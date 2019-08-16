package com.mobplug.games.framework.entities;

import java.io.Serializable;

import com.mobplug.games.framework.FullAnimator;
import com.mobplug.games.framework.interfaces.Animator;

public abstract class Sprite2D<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected Vector2D vector;
	protected Animator<T> animator = new FullAnimator<T>(); 
	protected float width;
	protected float height;
	
	public Sprite2D() {
		vector = new Vector2D();
	}
	public T getImage() {
		return animator.getImage();
	}

	public Point2D getPosition() {
		return vector.getPosition();
	}
	
	public Vector2D getVector() {
		return vector;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public boolean collidesWith(Sprite2D<?> other) {
		float myposx1 = this.getPosition().getX();
		float myposx2 = myposx1 + this.getWidth();
		float otherx1 = other.getPosition().getX();
		float otherx2 = otherx1 + other.getWidth();

		float myposy1 = this.getPosition().getY();
		float myposy2 = myposy1 + this.getHeight();
		float othery1 = other.getPosition().getY();
		float othery2 = othery1 + other.getHeight();
		

		return (myposx1 <= otherx2 && myposx2 >= otherx1 
				&& myposy1 <= othery2 && myposy2 >= othery1); 
			
	}
	
	public abstract void update(long gameTime);
}
