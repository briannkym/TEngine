package com.briannakayama.draw2d;

import com.briannakayama.domain.Updatable;

public class UAnimator<T> extends Updatable {
	UImage<T> img;
	T[] images;
	int[] sequence;
	int curIndex = 0;
	
	public UAnimator(T[] images){
		this.images = images;
		this.sequence = new int[1];
	}
	
	@SuppressWarnings("unchecked")
	public UAnimator(T[] images, int[] sequence){
		T[] image_seq = (T[])new Object[sequence.length];
		for (int i = 0; i < sequence.length; i++){
			image_seq[i] = images[sequence[i]];
		}
		this.images = image_seq;
	}
	
	public UAnimator(UImage<T> img){
		this.img = (UImage<T>) img;
	}
	
	public void setImage(UImage<T> img){
		this.img = (UImage<T>) img;
	}
	
	@Override
	public void update() {
		switch(images.length - curIndex){
		case 0:
			curIndex = 0;
			break;
		}
		img.image = images[curIndex];
		curIndex ++;
	}	
	
}
