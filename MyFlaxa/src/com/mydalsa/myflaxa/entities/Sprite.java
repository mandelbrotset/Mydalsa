package com.mydalsa.myflaxa.entities;

import javax.swing.text.Position;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mydalsa.myflaxa.handlers.Animation;


public class Sprite {
	private TextureRegion texture;
	private Body body;
	protected float width;
	protected float height;
	protected Animation animation;
	
	public Sprite(Body body){
		this.body = body;
		animation = new Animation();
	}
	
	public void update(float dt) {
		animation.update(dt);
	}

	public void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}

	public void loadTexture(TextureRegion txt){
		texture = txt;
		width = texture.getRegionWidth();
		height = texture.getRegionHeight();
	}
	public void render(SpriteBatch sb, float PPM, boolean isGoingLeft) {
		sb.begin();
		TextureRegion tr = animation.getFrame();
		if(isGoingLeft){
			if(!tr.isFlipX())
				animation.getFrame().flip(true, false);
		}else{
			if(tr.isFlipX())
				animation.getFrame().flip(true, false);
		}
		sb.draw(animation.getFrame(), body.getPosition().x * PPM - width/2, body.getPosition().y * PPM - height/2, width/2, height/2, width, height, 1.0f, 1.0f, (float)(body.getAngle()*180/Math.PI));
		sb.end();
	}
}
