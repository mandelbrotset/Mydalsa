package com.mydalsa.myflaxa.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mydalsa.myflaxa.MyFlaxaGame;
import com.mydalsa.myflaxa.entities.Sprite;

public class GameState extends State {
	public static final float PPM = 100.0f;
	
	private SpriteBatch batch;
	
	private boolean debug = false;
	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera cam;
	private Sprite player;
	private Matrix4 debugMatrix;
	private Body body;

	
	public GameState(MyFlaxaGame game) {
		super(game);
		batch = game.getSpriteBatch();
		cam = game.getCamera();
		
		// set up box2d stuff
		world = new World(new Vector2(0, -9.82f), true);
	//	world.setContactListener(cl = new MyContactListener());
		b2dr = new Box2DDebugRenderer();
	
		// create player
		createPlayer();
		
		// create tiles
//		createTiles();	
		createGround();
		
		
		// set up debug matrix
		debugMatrix = new Matrix4(cam.combined);
		debugMatrix.scl(PPM);
		/*b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);*/
	
	}
	
	private void createGround(){
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(new Vector2(0,0));
		ChainShape shape = new ChainShape();
		Vector2[] vs = {new Vector2(0.0f, 0.0f), new Vector2(1000/ PPM, 0.0f)};
		shape.createChain(vs);
		fixtureDef.shape = shape;
		world.createBody(bodyDef).createFixture(fixtureDef);
		shape.dispose();

	}
	private void createPlayer(){
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtDef = new FixtureDef();
		
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.angularVelocity = 0.9f;
		bodyDef.position.set(new Vector2(180 / PPM , 200 / PPM));
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(13 / PPM, 13 / PPM);
		fixtDef.shape = shape;
		fixtDef.restitution = 0.5f;
		fixtDef.density = 10.0f;
		bodyDef.active = true;
		
		body = world.createBody(bodyDef);
		
		body.createFixture(fixtDef);
		
		player = new Sprite(body);
		shape.dispose();
	}
	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		//System.out.println(body.getPosition().x + ", " + body.getPosition().y);
		world.step(dt, 6, 2);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.position.set(body.getPosition().x * PPM + MyFlaxaGame.V_WIDTH / 4, body.getPosition().y *PPM + MyFlaxaGame.V_HEIGHT /4, 0);
		cam.update();
		//draw entity 
		batch.setProjectionMatrix(cam.combined);
		
		
		// draw box2dworld	
		if(true){
			b2dr.render(world, cam.combined.scl(PPM));
			//System.out.println("DEBUD");
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
