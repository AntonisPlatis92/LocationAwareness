import java.applet.Applet;
import java.awt.*;
import java.io.IOException;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;
import java.util.Timer;
import java.util.TimerTask;


public class LocationAwareness extends Applet  {
	 
	private static final long serialVersionUID = 1L;
	private MainFrame frame;
	private Box[] floors=new Box[11];
	private Box wall;
	private Box grass;
	private Sphere location;
	private LocationDelta locDelta;
	private int imageHeight = 1200;
	private int imageWidth = 1200;
	private Canvas3D canvas;
	private SimpleUniverse universe;
	private BranchGroup group = new BranchGroup();
	private TransformGroup boxTransformGroup;
    private int iterations=0;
    private TransformGroup tg=new TransformGroup();
    private BranchGroup locationBranchGroup=new BranchGroup();
    private int floor=0;
    private int sector;
    
	public static void main(String[] args) {
		LocationAwareness object = new LocationAwareness();		 
		object.frame = new MainFrame(object, args, object.imageWidth, object.imageHeight);
		object.validate();
	}

	public void init() {
  		startDrawing();
  	}
      
	private void startDrawing() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();	
		canvas = new Canvas3D(config);		
		universe = new SimpleUniverse(canvas);
		
		Background background = new Background(new Color3f(0f,128/255f,1f));
		BoundingSphere backgroundBounds = new BoundingSphere(new Point3d(0,0,0), 1000);
		background.setApplicationBounds(backgroundBounds);
		group.addChild(background);
		add("Center", canvas);
		positionViewer();
		getScene();
		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(new BoundingSphere());
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);
		universe.addBranchGraph(group);
		Timer timer = new Timer();		
		locationBranchGroup.setCapability( BranchGroup.ALLOW_DETACH ); 	
		locationBranchGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		timer.schedule( new TimerTask() {
		    public void run() {
		    	if (iterations!=0){
		    		locationBranchGroup.detach();
		    		locationBranchGroup.removeChild(tg);
		    	}	
			    try {
					locDelta=new LocationDelta(2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    if (locDelta.getFloor()!=floor){
			    	locationBranchGroup.detach();
		    		locationBranchGroup.removeChild(tg);
			    	location = new Sphere (0.02f,0,getAppearance(new Color3f(Color.green))); 	
			    	tg = new TransformGroup();
				    Transform3D transform = new Transform3D();
			    	floor=locDelta.getFloor();
			    Vector3f vector = new Vector3f(.0f, -0.43f+locDelta.getFloor()*0.1f , .0f);
		        transform.setTranslation(vector);
		        tg.setTransform(transform);
				tg.addChild(location);
				locationBranchGroup.addChild(tg);
				universe.addBranchGraph(locationBranchGroup);			
			    }
		    }
		    
		 }, 0, 2*1000);

	}
	public void getScene() {
		for (int j=0; j<13 ;j++) {			
			boxTransformGroup = new TransformGroup();
		    Transform3D transform = new Transform3D();
		    if (j<11){
		        float y;
		     	floors[j] = new Box(.75f, .005f, .25f, Primitive.GENERATE_TEXTURE_COORDS,
				getAppearance(new Color3f(new Color3f((249-5*j)/255f,(206+5*j)/255f,(181+5*j)/255f))));		 	   
		        y=-0.45f + 0.1f*j;
			    Vector3f vector = new Vector3f(.0f, y , 0f);
		        transform.setTranslation(vector);
		        boxTransformGroup.setTransform(transform);
				boxTransformGroup.addChild(floors[j]);
				group.addChild(boxTransformGroup);
		    }
		    else if(j==11){
		    	TextureLoader textureLoader = new TextureLoader("grass2.jpg", null, new Container());
		        ImageComponent2D image = textureLoader.getImage();
		        Texture2D texture = new Texture2D(Texture2D.BASE_LEVEL, Texture.RGBA,
		                                            image.getWidth(), image.getHeight());
		        texture.setImage(0, image);
		        texture.setEnable(true);
		        Appearance ap = new Appearance();
		        ap.setTexture(texture);
		    	grass = new Box(5f, 0.005f, 5f, Primitive.GENERATE_TEXTURE_COORDS,ap);		 	 
		        Vector3f vector = new Vector3f(.0f, -.48f , .0f);
		        transform.setTranslation(vector);
		        boxTransformGroup.setTransform(transform);
				boxTransformGroup.addChild(grass);
				group.addChild(boxTransformGroup);
		    }
		    else if (j==12){
		    	wall = new Box(.75f, 0.5f, .005f, Primitive.GENERATE_TEXTURE_COORDS,
						getAppearance(new Color3f(198/255f,120/255f,103/255f)));		 	 
			    Vector3f vector = new Vector3f(.0f, 0.05f , -.25f);
		        transform.setTranslation(vector);
		        boxTransformGroup.setTransform(transform);
				boxTransformGroup.addChild(wall);
				group.addChild(boxTransformGroup);	    
		    }	
	}	
		
	}


	
	public void positionViewer() {
		ViewingPlatform vp = universe.getViewingPlatform();
		TransformGroup tg1 = vp.getViewPlatformTransform();
		Transform3D t3d = new Transform3D();
		tg1.getTransform(t3d);
		vp.setNominalViewingTransform();

	}

	public static Appearance getAppearance(Color color) {
		return getAppearance(new Color3f(color));
	}
	public static Appearance getAppearance(Color3f color) {
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
		Appearance ap = new Appearance();
		Texture texture = new Texture2D();
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
		Material mat = new Material(color, black, color, white, 70f);
		ap.setTextureAttributes(texAttr);
		ap.setMaterial(mat);
		ap.setTexture(texture);	 
		ColoringAttributes ca = new ColoringAttributes(color,
				ColoringAttributes.NICEST);
		ap.setColoringAttributes(ca);
		return ap;
	}
}