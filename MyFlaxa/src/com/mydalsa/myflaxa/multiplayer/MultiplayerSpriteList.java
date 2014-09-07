package com.mydalsa.myflaxa.multiplayer;

import java.io.Serializable;
import java.util.HashSet;

import com.badlogic.gdx.math.Vector2;
import com.mydalsa.myflaxa.entities.Sprite;

public class MultiplayerSpriteList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3265643594068430643L;
	
	private HashSet<MultiplayerSprite> set;
	
	
	public MultiplayerSpriteList(){
		set = new HashSet<MultiplayerSprite>();
	}
	
	public void addSprite(Sprite sprite){
		long id = sprite.getId();
		Vector2 position = sprite.getPosition();
		Vector2 velocity = sprite.getBody().getLinearVelocity();
		float angularVel = sprite.getBody().getAngularVelocity();
		float angle = sprite.getBody().getAngle();
		
		MultiplayerSprite ms = new MultiplayerSprite(id);
		ms.setProperties(position, velocity, angularVel, angle);
		
		set.add(ms);
	}
	
	
	
}
