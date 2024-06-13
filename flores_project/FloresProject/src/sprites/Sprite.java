package sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	protected boolean alive;
	protected double x, y;
	protected double width;
	protected double height;
	protected double size;
	protected double speed;
	protected double dx, dy;
	protected boolean moving;
	protected Image image;
	protected String type;
	protected boolean isVisible;

	public Sprite(int xPos, int yPos, double width, double height, String type){
		this.alive = true;
        this.type = type;
        this.setSize(width, height);
		this.loadImage(type);
		this.speed = 120/size;
		// applied calculations to make center of Image render at x and y
		this.x = xPos-width/2;
		this.y = yPos-height/2;
	}

	//method to set the object's width and height properties
	protected void setSize(double width, double height){
		this.width = width;
	    this.height = height;
	    this.size = width; // size equals length of a side
	}

	//method to set object's image based on imageURL
	protected void loadImage(String type){
		try{
			this.image = new Image("images/"+type+".png", this.width, this.height, false, false);
		} catch(Exception e){}
	}

	// method to draw object image on given GraphicsContext
	public void render(GraphicsContext gc){
		this.loadImage(type);
		gc.drawImage(this.image, this.x, this.y);
    }

	//method that will check for collision of two sprites
	public boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
	//method that will return the bounds of an image
	private Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	//method to check if this is still inside a plane with given width and height
	public boolean isInBounds(double borderWidth, double borderHeight) {
		return (this.x+this.dx <= borderWidth-this.width &&
				this.x+this.dx >=0 &&
				this.y+this.dy <= borderHeight-this.height &&
				this.y+this.dy >=0);
	}

	// getters
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isMoving() {
		return moving;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getSize() {
		return size;
	}

	public double getSpeed() {
		return speed;
	}

	public Image getImage() {
		return image;
	}

	public String getType() {
		return type;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setMoving(boolean val) {
		this.moving = val;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVisible (boolean val) {
		this.isVisible = val;
	}

	public boolean getVisible () {
		return this.isVisible;
	}

}
