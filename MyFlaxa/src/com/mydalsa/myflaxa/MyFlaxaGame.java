package com.mydalsa.myflaxa;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mydalsa.myflaxa.handlers.StateHandler;

public class MyFlaxaGame extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private StateHandler stateHandler;

	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	
	public static final String TITlE = "MyFlaxa";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;

	public static final float STEP = 1 / 60f;
	
	
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		stateHandler = new StateHandler(this);
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
	}

	@Override
	public void render () {
		stateHandler.update(Gdx.graphics.getDeltaTime());
		stateHandler.render();
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}
	
	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return cam;
	}

	public OrthographicCamera getHudCamera() {
		return hudCam;
	}
	
	
}
