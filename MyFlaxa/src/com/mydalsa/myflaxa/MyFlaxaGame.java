package com.mydalsa.myflaxa;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mydalsa.myflaxa.handlers.Content;
import com.mydalsa.myflaxa.handlers.StateHandler;

public class MyFlaxaGame extends ApplicationAdapter {
	
	private SpriteBatch batch;
	private StateHandler stateHandler;

	private OrthographicCamera cam;
	private OrthographicCamera hudCam;
	private static Content res;
	
	public static final String TITlE = "MyFlaxa";
	public static final int V_WIDTH = 1000;
	public static final int V_HEIGHT = 800;
	public static final int SCALE = 1;

	public static final float STEP = 1 / 60f;
	
	
	
	@Override
	public void create () {
		res = new Content();
		res.loadTexture("res/flax_50.png", "flax");
		Gdx.graphics.setDisplayMode(V_WIDTH, V_HEIGHT, false);
		batch = new SpriteBatch();
		Gdx.graphics.setTitle(TITlE);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		stateHandler = new StateHandler(this);

	}

	@Override
	public void render () {
		stateHandler.update(Gdx.graphics.getDeltaTime());
		stateHandler.render();
	}
	
	@Override
	public void pause() {
		super.pause();
	}
	
	@Override
	public void resize(int width, int height) {
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
	
	public static Content getRes() {
		return res;
	}
	
}
