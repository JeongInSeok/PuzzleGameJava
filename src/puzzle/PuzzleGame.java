package puzzle;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PuzzleGame extends JFrame implements ActionListener{

	JMenuBar mb = new JMenuBar();
	JMenu menu_game = new JMenu("게임"); // 게임 메뉴 만들기
	JMenu menu_level = new JMenu("난이도"); // 난이도 메뉴 만들기
	JMenuItem menu_game_new	= new JMenuItem("새게임"); // 게임 메뉴에 새게임이란 항목을 만들기 
	JMenuItem menu_game_start = new JMenuItem("게임시작");
	JMenuItem menu_game_newimage = new JMenuItem("다른 그림으로 하기");
	JMenuItem menu_game_end = new JMenuItem("게임종료");
	JMenuItem menu_level1 = new JMenuItem("3 X 3"); // 난이도 메뉴에 3X3이란 항목을 만들기
	JMenuItem menu_level2 = new JMenuItem("4 X 4");
	
	JFileChooser jfc = new JFileChooser(); // 파일 선택하는 것
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
		menu_game.addSeparator(); // 메뉴에 구분선을 추가하기위해서 사용하는 문장
		// 에드세퍼레이터 구분짓는 선... 즉 다른이미지와 게임종료사이에 선이 추가됨.
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
		setResizable(false); // 메소드의 프레임의 크기를 변경하도록 할 것인지 아닌지를 결정하는 메소드로 이건 프레임크기 변경불가능하게 하는거
		setBounds(300, 200, 540, 590); // 컴포넌트의 위치와 크기를 지정한다.
		setVisible(true); // 컴포넌트가 보이게 하는 문장
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menu_game_new.addActionListener(this); // 이벤트 등록하기
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
			pan.repaint();// 현재 페인트되고 있는 영역을 새롭게 다시 그리는 메소드
		}else if( o == menu_game_start){
			pan.bFull = false;//false 로 하지 않고  true로 하면 조각조각 안나누어짐.
			pan.repaint();
		}else if( o == menu_game_end){
			JOptionPane.showMessageDialog(this, "게임을 종료합니다.","종료",0);
			System.exit(0); // 프로세스가 정상적으로 종료된다.
		}else if( o == menu_game_newimage){
			System.out.println("newimage");
			int returnValue=jfc.showOpenDialog(this); // 파일을 열기위해서 사용하는 것. 
			// int returnValue=jfc.showSaveDialog(this);  만약 파일을 저장하고 싶다면 이런 것을 써야함.
			// 그리고 나서 saveFile(file); 같은 메소드를 따로 만들어줘야함.
			if(returnValue == JFileChooser.APPROVE_OPTION){
				//  JFileChooser.APPROVE_OPTION 은 열기 버튼을 눌렀을 때 실제값이 0이 되는 것으로 볼 수있다. 
				//  즉 위에 0을 써도 에러는 발생하지 않는다.
				File file = jfc.getSelectedFile();
				try {
					img = ImageIO.read(file);
					if(img != null){
						pan.setImg(img);
					}else{
						JOptionPane.showMessageDialog(this, "이미지파일만 불러오십쇼","경고 경고",2);
						// JOptionPane.showMessageDialog(this, "어떤 글을 넣을 것인지","팝업창의 제목",아이콘 넘버?);
						// 1 -> i 인포메이션 , 2 -> 경고 아이콘 , 3 -> ? 아이콘
					}
				} catch (Exception e2) {
					e2.printStackTrace();
					// 에러 메시지의 발생 근원지를 찾아 단계별로 에러 출력을 하는 기능이다.
				}
			}
			pan.getRand();
			pan.getRect();
			pan.repaint();
		}else if( o == menu_level1){
			pan.chasu = 3; // 가로 세로 3칸씩 씩 나누기 
			pan.getRand();
			pan.getRect();
			pan.repaint();
		}else if( o == menu_level2){
			pan.chasu = 4; // 난이도 조절할 때 가로, 세로 몇 칸으로 나누는지에 대한 것
			pan.getRand();
			pan.getRect();
			pan.repaint();
		}
	}
	public static void main(String[] args) {
		new PuzzleGame();
	}
	
}