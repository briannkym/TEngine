package com.briannakayama.domain;


import java.io.Serializable;

/**
 * The component object for a tree of an entity-component like pattern.
 * 
 * Essentially uses a linked list for the strategy pattern.
 * 
 * @author Brian Nakayama
 * @version 0.1
 */
public abstract class Updatable implements Serializable{
	/**
	 * @since 0.1
	 */
	private static final long serialVersionUID = 9042524315335204327L;
	
	transient Updatable prev = this;
	transient Updatable next = this;
	protected int index = 0;
	protected int status = 0;

	/**
	 * Creates an unconnected updatable object.
	 */
	public Updatable() {

	}
	

	/**
	 * The strategy for this component.
	 */
	public abstract void update();

	/**
	 * Remove this component from it's entity.
	 */
	public void removeSelf() {
		prev.next = next;
		next.prev = prev;
		prev = this;
		next = this;
		status = 1;
	}

	/**
	 * Swaps this Updatable with another in the list for use in sorting. 
	 * Not meant for the user.
	 * @param u The updatable to swap with.
	 */
	void swap(Updatable u) {
		Updatable next = this.next;
		Updatable prev = this.prev;
		this.next = u.next;
		this.prev = u.prev;
		this.next.prev = this;
		this.prev.next = this;
		u.next = next;
		u.prev = prev;
		u.next.prev = u;
		u.prev.next = u;
	}

	/**
	 * 
	 * @param u
	 */
	void insertNext(Updatable u) {
		u.next = next;
		u.prev = this;
		next.prev = u;
		next = u;
		u.status = 0;
	}

	/**
	 * 
	 * @param u
	 */
	void insertBefore(Updatable u) {
		u.next = this;
		u.prev = prev;
		prev.next = u;
		prev = u;
		u.status = 0;
	}

	public void setUpdateIndex(int index) {
		this.index = index;
	}

	public String toYAML() {
		return "index: " + index + "\n" + "status: " + status + "\n";
	}

}
