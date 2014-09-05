package com.mydalsa.myflaxa.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;


public class Sprite {
	private TextureRegion texture;
	private Body body;
	protected float width;
	protected float height;
	
	public Sprite(Body body){
		this.body = body;
		//loadTexture(txt);
	}
	
	public void update(float dt) {
	//	animation.update(dt);
	}

	public void loadTexture(TextureRegion txt){
		texture = txt;
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}
	public void render(SpriteBatch sb, int PPM) {
		sb.begin();
		sb.draw(texture, body.getPosition().x * PPM
				- width / 2, body.getPosition().y *  PPM - height
				/ 2);
		sb.end();
	}
}
