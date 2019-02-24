package com.hjp.zhaocha.window;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JWindow;

import com.hjp.zhaocha.utils.Util;


public class WarmingDialog extends JWindow {
private static final long serialVersionUID = 1L;
	
	public String DIALOG_TITLE_TEXT = " 警告提示";
	public String DIALOG_CONTENT_TEXT = "请确认游戏已经打开，并且游戏窗口没有被遮挡。";
	
	private int screenWidth;
	private int screenHeight;

	public static final int DIALOG_WIDTH = 340;
	public static final int DIALOG_HEIGHT = 160;
	
	Font font = new Font("宋体", Font.PLAIN, 14);
	
	
	public WarmingDialog() {
		init();
		this.setVisible(false);
	}
	
	private static WarmingDialog dialog;
	
	public static void showDlg(String text,String caption) {
		if (dialog == null) {
			dialog = new WarmingDialog();
		}
		dialog.setText(text, caption);
		if(!dialog.isVisible()) {
			dialog.setVisible(true);
		}
	}
	
	JLabel title;
	JLabel content;
	
	public void setText(String text,String caption) {
		this.title.setText(caption);
		this.content.setText(text);
	}
	
	public void init() {
		screenWidth = Util.getScreenWidth();
		screenHeight = Util.getScreenHeight();
		this.setBounds((screenWidth-DIALOG_WIDTH)/2,(screenHeight-DIALOG_HEIGHT)/2,
				DIALOG_WIDTH, DIALOG_HEIGHT);
		this.getContentPane().setBackground(Color.YELLOW);
		this.setAlwaysOnTop(true);
		
		this.setLayout(null);
		title = new JLabel(DIALOG_TITLE_TEXT);
		title.setFont(font);
		title.setOpaque(true);
		title.setBackground(Color.ORANGE);
		title.setBounds(0, 0, DIALOG_WIDTH, 30);
		this.add(title);
		
		content = new JLabel(DIALOG_CONTENT_TEXT, JLabel.CENTER);
		content.setFont(font);
		content.setBounds(0, 30, DIALOG_WIDTH, 80);
		this.add(content);
		
		JButton btn = new JButton("关闭");
		btn.setFont(font);
		btn.setBackground(Color.ORANGE);
		btn.setMargin(new Insets(0,0,0,0));
		btn.setBounds(130, 110, 80, 30);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarmingDialog.this.setVisible(false);
				WarmingDialog.this.dispose();
			}
		});
		this.add(btn);
	}
}
