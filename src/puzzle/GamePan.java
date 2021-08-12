package puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePan extends JPanel {

	public Image img = Toolkit.getDefaultToolkit().getImage("./src/images/1.jpg");
	// �̹����� ����ϱ� ���ؼ� ����� �ý��ۿ� ���Ϸ� �����ϴ� �̹����� �о���� ����... ��ŶŬ����
	// new�δ� ��ü�� �������� ���ϹǷ� getDefaultToolkit(); �޼ҵ带 ����Ѵ�.
	MediaTracker mt=new MediaTracker(this);
	// MediaTracker �� �̹����� ������ �ε��� ������ �� �ִ� ��ġ�̴�.
	// this�� �ε��� �̹����� ����� ������Ʈ�� ��Ÿ����.
	int width = 540;
	int height = 540;
	int chasu;
	int brow,bcol,drow,dcol;
	boolean bFull;
	Rectangle[][] panRect = new Rectangle[9][9];
	Rectangle[][] grimRect = new Rectangle[9][9];
	int[][] ia = new int[9][9];
	String[][] name;
	
	public GamePan(String[][] name, int[][] jumsu) {
		bFull = true;
		chasu = 3;
		this.name = name;

		mt.addImage(img, 0);
		try
		{
			mt.waitForAll();	//MediaTracker�� �ִ� ��� �׸��� �ε��Ѵ�.
		} catch(Exception e){}
		getRand();
		getRect();
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if( !(e.getModifiers() == InputEvent.BUTTON1_MASK) ) return;
				drwCheck(e);
			}
		});
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(bFull){
			g.drawImage(img, 0, 0, width, height, this);
			return;
		}
		int x;
		int y;
		
		for(int i=0; i<chasu; i++)
		{
			for(int j=0; j<chasu; j++)
			{
				x = ia[i][j]/chasu;
				y = ia[i][j]%chasu;
				g.drawImage(img, panRect[i][j].x, panRect[i][j].y, 
							j*panRect[i][j].width+panRect[i][j].width, i*panRect[i][j].height+panRect[i][j].height, 
							grimRect[x][y].x, grimRect[x][y].y, 
							y*grimRect[i][j].width+grimRect[x][y].width, x*grimRect[x][y].height+grimRect[x][y].height, 
							this);
			}
		}
		
		g.fillRect(panRect[brow][bcol].x, panRect[brow][bcol].y, panRect[brow][bcol].width, panRect[brow][bcol].height);
		// fillRect �� �簢���� ĥ�ϴ� �����̴�. (�簢���� ���� ����� �𼭸� ��ǥ(x,y),������� ���ο� ����(width,heigt))
		// �̰��� ���� ������.. ȸ�� ������ �������.
	}
	
	public void getRand(){
		int[] imsi = new int[81];
		boolean bDasi;
		int rand = -1;
		Random random = new Random();
		for (int i = 0; i < chasu*chasu; i++) {
			bDasi = true;
			while (bDasi) {
				bDasi = false;
				rand = random.nextInt(chasu*chasu);
				for (int j = 0; j < i; j++) {
					if(rand==imsi[j]){
						bDasi=true;
					}}}
			imsi[i] = rand;
			ia[i/chasu][i%chasu] = rand;
			if( rand == chasu*chasu -1 ){
				brow = i/chasu;
				bcol = i%chasu;
			}}}
	public void getRect(){
		for(int i=0; i<chasu; i++)
		{
			for(int j=0; j<chasu; j++)
			{
				panRect[i][j] = new Rectangle(j*width/chasu, i*height/chasu, width/chasu, height/chasu);
				grimRect[i][j] = new Rectangle(j*img.getWidth(this)/chasu, i*img.getHeight(this)/chasu, img.getWidth(this)/chasu, img.getHeight(this)/chasu);
			}
		}
	}
	public void drwCheck(MouseEvent e){
		for(int i=0; i<chasu; i++){
			for (int j = 0; j < chasu; j++) {
				if(panRect[i][j].contains(e.getPoint()))
				{
					if(i==brow && j==bcol)return;
					if(!((i==brow || j==bcol) && (Math.abs(i-brow) == 1 || Math.abs(j-bcol)==1)))return;
					ia[brow][bcol] = ia[i][j];
					ia[i][j] = chasu*chasu-1;
					
					drow = brow;
					dcol = bcol;
					brow=i;
					bcol=j;
					repaint();
				}
			}
		}
	}
	public static void main(String[] args) {
	}
	public void setImg(Image img)
	{
		this.img = img;
	}
	public Image getImg(){
		return img;
	}
	public Integer getChasu(){
		return chasu;
	}
}