package com.hjp.zhaocha.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import com.hjp.zhaocha.utils.Util;

public class CommandPanel extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final int FRAME_WIDTH = 280;
	public static final int FRAME_HEIGHT = 80;
	public static final int FRAME_OFFSET_Y = 40;
	
	public static final String COMMAND_TITLE_TEXT = " 大家来找茬 2.0 -- 辅助v1.1";
	public static final String COMMAND_SHOW_TEXT = "显示标记(Alt+A)";
	public static final String COMMAND_HIDE_TEXT = "关闭标记(Alt+A)";
	public static final String COMMAND_EXIT_TEXT = "退出程序(Alt+E)";
	
	public static final char FUNC_KEY = 'A';
	public static final char EXIT_KEY = 'E';
	
	int screenWidth;
	int screenHeight;
	
	Font font = new Font("宋体", Font.PLAIN, 14);
	
	JLabel titleLabel;
	JButton showAssistBtn;
	JButton exitBtn;
	
	public CommandPanel() {
		this.init();
		this.setUndecorated(true);
		this.getContentPane().setBackground(new Color(0xC0FF3E));
		this.setBounds((screenWidth-FRAME_WIDTH)/2, screenHeight-FRAME_HEIGHT-FRAME_OFFSET_Y, FRAME_WIDTH, FRAME_HEIGHT);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void init() {
		titleLabel = new JLabel(COMMAND_TITLE_TEXT);
		titleLabel.setFont(font);
		titleLabel.setBounds(0, 0, FRAME_WIDTH, 30);
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(0x7FFF00));
		DragFrameListener dragFraListnr = new DragFrameListener();
		titleLabel.addMouseListener(dragFraListnr);
		titleLabel.addMouseMotionListener(dragFraListnr);
		
		this.add(titleLabel);
		showAssistBtn = new JButton(COMMAND_SHOW_TEXT);
		showAssistBtn.setFont(font);
		showAssistBtn.setBackground(new Color(0x85ce3d));
		showAssistBtn.setFocusable(false);
		showAssistBtn.setMargin(new Insets(0,0,0,0));
		
		showAssistBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				markSwitch();
			}
		});
		showAssistBtn.setBounds(10, 40, 120, 30);
		this.add(showAssistBtn);
		
		exitBtn = new JButton(COMMAND_EXIT_TEXT);
		exitBtn.setFont(font);
		exitBtn.setBackground(new Color(0x85ce3d));
		exitBtn.setFocusable(false);
		exitBtn.setMargin(new Insets(0,0,0,0));
		exitBtn.setBounds(150, 40, 120, 30);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				applicationExit();
			}
		});
		this.add(exitBtn);
		
		screenWidth = Util.getScreenWidth();
		screenHeight = Util.getScreenHeight();
	}
	
	/**
	 * 退出程序
	 */
	public void applicationExit() {
		System.exit(0);
	}
	
	ImageMarkWindow window;
	
	public void markSwitch() {
		if(window == null) {
			window = new ImageMarkWindow();
			if(!window.isGameFinded) {
				window = null;
			} else {
				showAssistBtn.setText(COMMAND_HIDE_TEXT);
			}
		}else{
			window.dispose();
			window = null;
			showAssistBtn.setText(COMMAND_SHOW_TEXT);
		}
	}
	
	/**
	 * 鼠标事件处理
	 */
	class DragFrameListener implements MouseInputListener {
		
		Point origin = new Point(); 
		
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			 origin.x = e.getX();  
		     origin.y = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {
			CommandPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			CommandPanel.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = CommandPanel.this.getLocation();
			CommandPanel.this.setLocation(
				p.x + e.getX() - origin.x, 
				p.y + e.getY() - origin.y);  
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
		
	}
}
