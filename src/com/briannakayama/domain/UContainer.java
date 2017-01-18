package com.briannakayama.domain;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class UContainer extends Updatable implements Externalizable{
	

	private transient final Updatable inner = new UNull();
	private boolean update = true;
	
	public UContainer(){
		
	}
	
	public UContainer(Updatable... updatables){
		for (Updatable u: updatables){
			this.addUpdatable(u);
		}
	}
	
	@Override
	public void update() {
		if (update){
			Updatable current = inner.next;
			while(current != inner) {
				//Update before sorting.
				current.update();
				Updatable sort = current;
				Updatable insert = current.prev; 
				current = current.next;
				if (insert.index > sort.index && insert != inner){
					do{
						insert = insert.prev;
					} while (insert.index > sort.index && insert != inner);
					//insertion swap here:
					sort.swap(insert);								
				}
			}
		}
	}
	
	public void addUpdatable(Updatable u){
		u.removeSelf();
		inner.insertBefore(u);
	}
	
	public boolean hasUpdatable(Updatable u){
		Updatable current = inner;
		do {
			if (current == u){
				return true;
			}
			current = current.next;
		} while(current != inner);
		return false;
	}
	
	public boolean removeUpdatable(Updatable u){
		if(hasUpdatable(u)){
			u.removeSelf();
			return true;
		}
		return false;
	}
	
	public int size(){
		int size = 0;
		Updatable current = inner.next;
		while(current != inner){
			current = current.next;
			size += 1;					
		}
		return size;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		int size =  size();
		out.writeInt(size);
		Updatable current = inner.next;
		while(current != inner){
			out.writeObject(current);
			current = current.next;
			size += 1;					
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		int size = in.readInt();
		for (int i = 0; i < size; i++){
			this.addUpdatable(Updatable.class.cast(in.readObject()));
		}
	}	
}
