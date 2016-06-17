import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;




public class KeyboardServer extends JFrame{
	private static int port;
	ServerThread serverthread; //��ʼ���߳�
	final JTextField messagebox;
	final JTextField field;
	final JButton stopbutton;
	final JButton startbutton;
	final JButton aboutbutton;
	static int menux  =0; //menux�ź��� 0��ʾδ���� 1��ʾ���� 2��ʾ��ͣ
	String message =null;
	String[] messages =null;
	String type =null;
	String info =null;
	public KeyboardServer(){
		 super();
        setTitle("osu! Keyboard Droid Server");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image image;
		try {
			image = ImageIO.read(this.getClass().getResource("/img/logo.png"));
			this.setIconImage(image);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		this.setVisible(true);
		
        Toolkit toolkit = getToolkit(); // ���Toolkit����
        Dimension dimension = toolkit.getScreenSize(); // ���Dimension����
        int screenHeight = dimension.height; // �����Ļ�ĸ߶�
        int screenWidth = dimension.width; // �����Ļ�Ŀ��
        int frm_Height = this.getHeight(); // ��ô���ĸ߶�
        int frm_width = this.getWidth(); // ��ô���Ŀ��
        setLocation((screenWidth - frm_width) / 2,
                (screenHeight - frm_Height) / 2); // ʹ�ô��������ʾ
        
        getContentPane().setLayout(null);
        final JLabel label = new JLabel();
        try {
			label.setText("����IP��"+InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        label.setBounds(10, 20, 300, 25);
        Font font = new Font("SimSun", Font.PLAIN, 16);
        label.setFont(font);
        getContentPane().add(label);
        
        final JLabel label2 =new JLabel();
    	label2.setText("������˿ںţ�");
    	label2.setBounds(10, 50, 100, 25);
    	getContentPane().add(label2);
    	
    	field = new JTextField();
    	field.setBounds(110,50,90,25);
    	getContentPane().add(field);
        
    	startbutton = new JButton();
    	startbutton.setText("����");
    	startbutton.setBounds(10,90, 80, 25);
    	getContentPane().add(startbutton);
    	
    	stopbutton = new JButton();
    	stopbutton.setText("ֹͣ");
    	stopbutton.setEnabled(false);
    	stopbutton.setBounds(120,90, 80, 25);
    	getContentPane().add(stopbutton);
    	
        final JLabel label3 =new JLabel();
    	label3.setText("����osu! Keyboard Droid�ֻ������뱾��IP�Ͷ˿ں�");
    	label3.setBounds(10, 120, 320, 20);
    	getContentPane().add(label3);
    	
    	messagebox = new JTextField();
    	messagebox.enable(false);
    	getContentPane().add(messagebox);
    	
    	aboutbutton = new JButton();
    	aboutbutton.setText("����");
    	aboutbutton.setBounds(220,10, 80, 25);
    	getContentPane().add(aboutbutton);
    	
    	
    	
    	startbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	String str  = field.getText().trim();
            	int num;
            	if(str.equals("")){
            		JOptionPane.showMessageDialog(null,"�˿ڲ���Ϊ��");
            		return;
            	}
            	try{
            		num = Integer.parseInt(str);
            	}catch(Exception e){
            		JOptionPane.showMessageDialog(null,"�˿ں�Ӧ��Ϊ����");
            		return;
            	}
            	if(num<0||num>65535){
            		JOptionPane.showMessageDialog(null,"�˿ں�Ӧ�ô���0С��65535");
            		return;
            	}
            	port=num;
            	stopbutton.setEnabled(true);
            	startbutton.setEnabled(false);
            	start();
            }
        });
    	
		
    	stopbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
 //           	startbutton.setEnabled(false);
            	stopbutton.setEnabled(false);
            	startbutton.setEnabled(true);
            	stop();
            }
    	 });
		
    	
    	aboutbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	JOptionPane.showMessageDialog(null,"Version 1.0.0\nCopyright 2014-2016 Death Horizon_Studio\nPowered by LiarOnce\nGithub��Ŀ��ַ��https://github.com/LiarOnce/osu-Keyboard-Droid-Server\n�ڴ˸�л����ԭ���ߣ�\nǰ�й����� ³����\nԴ��Ŀ��ַ��http://git.oschina.net/lujianing/android-remote-control-computer");
            }
    	});
    	
    	
    	setVisible(true);
	}
	
	public static void main(String[] args) {
		
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
            	new KeyboardServer();
            }
        });
	
	}
	
	public void start(){
		if(menux==0){   //menux�ź��� 0��ʾδ���� 1��ʾ���� 2��ʾ��ͣ
			serverthread  =new ServerThread();
			serverthread.start();
			menux=1;
			field.setEditable(false);
		}
		if(menux==2){
			serverthread.resume();
			menux=1;
		}
	}
	
	public void stop(){
		if(menux==1){
			serverthread.suspend();
			menux=2;
		}
		
	}
	
	public class ServerThread extends Thread{
    			
    	public void run(){
    		try {
    			//����һ��DatagramSocket���󣬲�ָ�������Ķ˿ں�
    			DatagramSocket socket;
    			try{
    				socket = new DatagramSocket(port);
    			}catch(Exception e){
    				messagebox.setText("�˿ڱ�ʹ��,������˿�");
    				startbutton.setEnabled(true);
    				stopbutton.setEnabled(false);
    				menux=0;
    				
    				
    				field.setEditable(true);
    				return;
    			}
				byte data [] = new byte[1024];
				//����һ���յ�DatagramPacket����
				DatagramPacket packet = new DatagramPacket(data,data.length);
				//ʹ��receive�������տͻ��������͵�����
				System.out.println("�����˿ڼ���"+socket.getLocalPort());
				while(true){
					socket.receive(packet);
					message = new String(packet.getData(),packet.getOffset(),packet.getLength());
					System.out.println("message--->" + message);
					messagebox.setText(message);
					messages = message.split(":");
					if(messages.length>=2){
						type= messages[0];
						info= messages[1];
						if(type.equals("keyboard"))
							KeyBoard(info);
					}
				
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	public void KeyBoard(String info)throws AWTException{
    		String args[]=info.split(",");
    		String type=null;
    		String cont=null;
    		String keystate =null;
    		java.awt.Robot robot = new Robot();
    		if(args.length==2){
    			type = args[0];
    			cont = args[1];
    		}
    		if(args.length==3){
    			type = args[0];
    			cont = args[1];
    			keystate = args[2];
    		}
    		
    		  if(type.equals("key")){    
    			if(cont.equals("Z")){     //osu! Standard�Լ�Taikoģʽ
    				if(keystate.equals("down"))
    					robot.keyPress(KeyEvent.VK_Z);
    				if(keystate.equals("up"))
    					robot.keyRelease(KeyEvent.VK_Z);
    				}
    			}
    			if(cont.equals("X")){    //ͬ��
    				if(keystate.equals("down"))
    					robot.keyPress(KeyEvent.VK_X);
    				if(keystate.equals("up"))
    					robot.keyRelease(KeyEvent.VK_X);
    				}
    			if(cont.equals("C")){    //Taikoģʽ
    				if(keystate.equals("down"))
    					robot.keyPress(KeyEvent.VK_C);
    				if(keystate.equals("up"))
    					robot.keyRelease(KeyEvent.VK_C);
    				}
    			
    			if(cont.equals("V")){    //ͬ��
    				if(keystate.equals("down"))
    					robot.keyPress(KeyEvent.VK_V);
    				if(keystate.equals("up"))
    					robot.keyRelease(KeyEvent.VK_V);
    				}
    			
    			if(cont.equals("Shift")){  //��ˮ��ģʽ����û���ã�
    				if(keystate.equals("down"))
    					robot.keyPress(KeyEvent.VK_SHIFT);
    				if(keystate.equals("up"))
    					robot.keyRelease(KeyEvent.VK_SHIFT);
    		    }
    	   }
     }
}


