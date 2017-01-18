package com.briannakayama.draw2d;

import java.util.HashMap;
import java.util.Map;

import com.briannakayama.domain.Initializable;

public abstract class ImageFactory<T extends Initializable> {
	protected Map<String, T> images = new HashMap<String, T>();
	protected Map<String, T[]> animations =  new HashMap<String, T[]>();

	public abstract boolean loadDirectory(String location);
	
	public UImage<T> getImgInstance(String s){
		return new UImage<T>(images.get(s));
	}
	
	public UImage<T> getImgInstance(){
		return new UImage<T>();
	}
	
	public UAnimator<T> getAnmInstance(String s){
		if(animations.containsKey(s)){	
			return new UAnimator<T>(animations.get(s));
		}
		return null;
	}
	
	public UAnimator<T> getAnmInstance(String s, int... sequence){
		if(animations.containsKey(s)){	
			return new UAnimator<T>(animations.get(s), sequence);
		}
		return null;
	}
}
