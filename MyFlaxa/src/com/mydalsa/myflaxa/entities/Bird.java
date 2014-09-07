package com.mydalsa.myflaxa.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.mydalsa.myflaxa.MyFlaxaGame;
import com.mydalsa.myflaxa.handlers.Content;

public class Bird extends Sprite {

	public Bird(Body body) {
		super(body);
		Content res = MyFlaxaGame.getRes();
		Texture tex = res.getTexture("flax");
		TextureRegion[] sprites = TextureRegion.split(tex, 100, 100)[0];

		setAnimation(sprites, 1 / 48f);
	}

}
