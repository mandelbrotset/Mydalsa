package com.mydalsa.myflaxa.multiplayer;

import java.io.Serializable;
import java.util.ArrayList;

import com.mydalsa.myflaxa.entities.Sprite;

public class MultiplayerSpriteList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3265643594068430643L;
	
	private ArrayList<MultiplayerSpriteList> list;
	
	
	public MultiplayerSpriteList(){
		list = new ArrayList<MultiplayerSpriteList>();
	}
	
	public void addSprite(Sprite sprite){
		
	}
	
	
	
}
