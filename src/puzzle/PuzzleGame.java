package puzzle;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PuzzleGame extends JFrame implements ActionListener{

	JMenuBar mb = new JMenuBar();
	JMenu menu_game = new JMenu("����"); // ���� �޴� �����
	JMenu menu_level = new JMenu("���̵�"); // ���̵� �޴� �����
	JMenuItem menu_game_new	= new JMenuItem("������"); // ���� �޴��� �������̶� �׸��� ����� 
	JMenuItem menu_game_start = new JMenuItem("���ӽ���");
	JMenuItem menu_game_newimage = new JMenuItem("�ٸ� �׸����� �ϱ�");
	JMenuItem menu_game_end = new JMenuItem("��������");
	JMenuItem menu_level1 = new JMenuItem("3 X 3"); // ���̵� �޴��� 3X3�̶� �׸��� �����
	JMenuItem menu_level2 = new JMenuItem("4 X 4");
	
	JFileChooser jfc = new JFileChooser(); // ���� �����ϴ� ��
	Image img;
	GamePan pan;
	String[][] name =new String[3][10];
	int[][] jumsu = new int[3][10];
	
		public PuzzleGame() {
		super("PulzzeGame");
		Container cp = getContentPane();
		
		mb.add(menu_game);
		mb.add(menu_level);
		
		menu_game.add(menu_game_new);
		menu_game.add(menu_game_start);
		menu_game.add(menu_game_newimage);
		menu_game.addSeparator(); // �޴��� ���м��� �߰��ϱ����ؼ� ����ϴ� ����
		// ���弼�۷����� �������� ��... �� �ٸ��̹����� ����������̿� ���� �߰���.
		menu_game.add(menu_game_end);
		
		menu_level.add(menu_level1);
		menu_level.add(menu_level2);
		
		
		setJMenuBar(mb);
		pan = new GamePan(name, jumsu);
		cp.add(pan);
		
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<10; j++)
			{
				name[i][j]="";
				jumsu[i][j] = 0;
			}
		}
		setResizable(false); // �޼ҵ��� �������� ũ�⸦ �����ϵ��� �� ������ �ƴ����� �����ϴ� �޼ҵ�� �̰� ������ũ�� ����Ұ����ϰ� �ϴ°�
		setBounds(300, 200, 540, 590); // ������Ʈ�� ��ġ�� ũ�⸦ �����Ѵ�.
		setVisible(true); // ������Ʈ�� ���̰� �ϴ� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu_game_new.addActionListener(this); // �̺�Ʈ ����ϱ�
		menu_game_start.addActionListener(this);
		menu_game_newimage.addActionListener(this);
		menu_game_end.addActionListener(this);
		
		menu_level1.addActionListener(this);
		menu_level2.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if( o == menu_game_new )
		{
			pan.bFull=true;
			pan.repaint();// ���� ����Ʈ�ǰ� �ִ� ������ ���Ӱ� �ٽ� �׸��� �޼ҵ�
		}else if( o == menu_game_start){
			pan.bFull = false;//false �� ���� �ʰ�  true�� �ϸ� �������� �ȳ�������.
			pan.repaint();
		}else if( o == menu_game_end){
			JOptionPane.showMessageDialog(this, "������ �����մϴ�.","����",0);
			System.exit(0); // ���μ����� ���������� ����ȴ�.
		}else if( o == menu_game_newimage){
			System.out.println("newimage");
			int returnValue=jfc.showOpenDialog(this); // ������ �������ؼ� ����ϴ� ��. 
			// int returnValue=jfc.showSaveDialog(this);  ���� ������ �����ϰ� �ʹٸ� �̷� ���� �����.
			// �׸��� ���� saveFile(file); ���� �޼ҵ带 ���� ����������.
			if(returnValue == JFileChooser.APPROVE_OPTION){
				//  JFileChooser.APPROVE_OPTION �� ���� ��ư�� ������ �� �������� 0�� �Ǵ� ������ �� ���ִ�. 
				//  �� ���� 0�� �ᵵ ������ �߻����� �ʴ´�.
				File file = jfc.getSelectedFile();
				try {
					img = ImageIO.read(file);
					if(img != null){
						pan.setImg(img);
					}else{
						JOptionPane.showMessageDialog(this, "�̹������ϸ� �ҷ����ʼ�","��� ���",2);
						// JOptionPane.showMessageDialog(this, "� ���� ���� ������","�˾�â�� ����",������ �ѹ�?);
						// 1 -> i �������̼� , 2 -> ��� ������ , 3 -> ? ������
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					// ���� �޽����� �߻� �ٿ����� ã�� �ܰ躰�� ���� ����� �ϴ� ����̴�.
				}
			}
			pan.getRand();
			pan.getRect();
			pan.repaint();
		}else if( o == menu_level1){
			pan.chasu = 3; // ���� ���� 3ĭ�� �� ������ 
			pan.getRand();
			pan.getRect();
			pan.repaint();
		}else if( o == menu_level2){
			pan.chasu = 4; // ���̵� ������ �� ����, ���� �� ĭ���� ���������� ���� ��
			pan.getRand();
			pan.getRect();
			pan.repaint();
		}
	}
	public static void main(String[] args) {
		new PuzzleGame();
	}
	
}