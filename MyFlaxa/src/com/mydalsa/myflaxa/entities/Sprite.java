package com.mydalsa.myflaxa.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mydalsa.myflaxa.handlers.Animation;


public abstract class Sprite {
	protected TextureRegion texture;
	protected Body body;
	protected float width;
	protected float height;
	protected Animation animation;
	private long id;
	public Sprite(long id){
		animation = new Animation();
		this.id = id;
	}

	public long getId() {
		return id;
	}
	public Body getBody() {
		return body;
	}
	public void update(float dt) {
		animation.update(dt);
	}
	
	public abstract void render(SpriteBatch sb);

	public Vector2 getPosition(){
		if(body == null) return null;
		return body.getPosition();
	}
	
	protected void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}

	protected void loadTexture(TextureRegion txt){
		texture = txt;
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}


}
