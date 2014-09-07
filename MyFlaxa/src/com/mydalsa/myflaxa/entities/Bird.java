package com.mydalsa.myflaxa.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mydalsa.myflaxa.MyFlaxaGame;
import com.mydalsa.myflaxa.handlers.Content;
import com.mydalsa.myflaxa.util.Constants;

public class Bird extends Sprite {

	private boolean goingLeft;
	private boolean goingRight;
	private boolean goingDown;
	private boolean goingUp;
	private boolean isLocked;

	public Bird(Vector2 startPosition, World world, boolean initiallyLocked, int id) {
		super(id);
		isLocked = initiallyLocked;
		createBody(startPosition, world);
		
		if(!initiallyLocked){
			body.setGravityScale(Constants.BIRD_GRAVITY_SCALE);
			body.setLinearVelocity(Constants.START_VELOCITY, body.getLinearVelocity().y);
		}
		
		goingDown = false;
		goingUp = false;
		goingRight = true;
		goingLeft = false;

		Content res = MyFlaxaGame.getRes();
		Texture tex = res.getTexture("flax");
		TextureRegion[] sprites = TextureRegion.split(tex, 100, 100)[0];

		setAnimation(sprites, 1 / 48f);
	}

	private void createBody(Vector2 startPos, World world) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtDef = new FixtureDef();

		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(startPos);
		bodyDef.active = true;

		// Create the birds body as a circle

		Shape shape = new CircleShape();
		shape.setRadius(Constants.BIRD_WIDTH / 2);

		fixtDef.shape = shape;
		fixtDef.restitution = 1.0f;
		fixtDef.density = Constants.BIRD_WEIGHT / (Constants.BIRD_HEIGHT * Constants.BIRD_WEIGHT);

		this.body = world.createBody(bodyDef);

		this.body.setGravityScale(0f);
		this.body.createFixture(fixtDef);

		// Creating a left and a right beak as sensors.
		fixtDef.isSensor = true;

		shape = new PolygonShape();
		((PolygonShape) shape).setAsBox(Constants.BIRD_WIDTH / 5, Constants.BIRD_HEIGHT / 12, new Vector2(Constants.BIRD_WIDTH / 2, 0), 0);
		fixtDef.shape = shape;
		this.body.createFixture(fixtDef).setUserData("beakRight");

		((PolygonShape) shape).setAsBox(Constants.BIRD_WIDTH / 5, Constants.BIRD_HEIGHT / 12, new Vector2(-Constants.BIRD_WIDTH / 2, 0), 0);
		fixtDef.shape = shape;
		this.body.createFixture(fixtDef).setUserData("beakLeft");

		shape.dispose();

	}

	@Override
	public void update(float dt) {
		super.update(dt);
	
		//Check if going left or right
		if (goingRight && body.getLinearVelocity().x < -0.01) {
			goingLeft = true;
			goingRight = false;
		}
		if (goingLeft && body.getLinearVelocity().x > 0.01) {
			goingLeft = false;
			goingRight = true;
		}

		//Check if going down and add an angular velocity so that the bird points down
		if (body.getLinearVelocity().y < -0.01 && !goingDown) {
			goingDown = true;
			goingUp = false;
			if (goingRight)
				body.setAngularVelocity(-4f);
			if (goingLeft)
				body.setAngularVelocity(4f);

		} else if (body.getLinearVelocity().y > 0.01 && !goingUp) { //Check if going up and add an angular velocity so the bird points up
			goingUp = true;
			goingDown = false;
			if (goingRight)
				body.setAngularVelocity(4f);
			if (goingLeft)
				body.setAngularVelocity(-4f);
		}
		//Check so that bird only point in 45degrees angle at some direction.
		if (goingRight && goingUp && body.getAngle() > Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (Math.PI / 4f));

		}
		if (goingRight && goingDown && body.getAngle() < -Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (-Math.PI / 4f));
		}

		if (goingLeft && goingUp && body.getAngle() < -Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (-Math.PI / 4f));

		}
		if (goingLeft && goingDown && body.getAngle() > Math.PI / 4) {
			body.setAngularVelocity(0);
			body.setTransform(body.getPosition().x, body.getPosition().y,
					(float) (Math.PI / 4f));
		}
		
		// Adds max speed in x and y
		if(body.getLinearVelocity().x > Constants.START_VELOCITY)
			body.setLinearVelocity(Constants.START_VELOCITY, body.getLinearVelocity().y);
		if(body.getLinearVelocity().x < -Constants.START_VELOCITY)
			body.setLinearVelocity(-Constants.START_VELOCITY, body.getLinearVelocity().y);
		if(body.getLinearVelocity().y > Constants.JUMP_VELOCITY*2)
			body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY*2);
		
	}

	public void render(SpriteBatch sb) {

		sb.begin();
		TextureRegion tr = animation.getFrame();
		if (goingLeft) {
			if (!tr.isFlipX())
				animation.getFrame().flip(true, false);
		} else {
			if (tr.isFlipX())
				animation.getFrame().flip(true, false);
		}
		sb.draw(animation.getFrame(), body.getPosition().x * Constants.PPM - width / 2, body.getPosition().y * Constants.PPM - height / 2, width / 2, height / 2, width, height, 1.0f, 1.0f, (float) (body.getAngle() * 180 / Math.PI));
		sb.end();

	}
	
	public void switchDirection(){
		if(goingRight)
			body.setLinearVelocity(-Constants.START_VELOCITY, body.getLinearVelocity().y);
		if(goingLeft)
			body.setLinearVelocity(Constants.START_VELOCITY, body.getLinearVelocity().y);
	}
	
	public void flyUp(){
		if (isLocked) {
			isLocked = false;
			body.setGravityScale(Constants.BIRD_GRAVITY_SCALE);
			body.setLinearVelocity(Constants.START_VELOCITY, body.getLinearVelocity().y);
		}
		
		body.setLinearVelocity(body.getLinearVelocity().x, Constants.JUMP_VELOCITY);
	
	}

}
