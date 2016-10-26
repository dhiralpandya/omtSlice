/**
   Copyright 2013 Dhiral Pandya

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.omt.slice.frames;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.omt.slice.common.SliceConstants;
import com.omt.slice.components.SliceRectangle;
import com.omt.slice.handler.SliceRectangleHandler;

/**
 * This is transparent frame. It is use to draw rectangle.
 * 
 * @author omt
 * @author Dhiral Pandya (dhiralpandya@gmail.com)
 * @since 10 May 2013
 * 
 */
public class TransparentFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = -8584725664766657461L;
	private SliceRectangle sliceRectangle;
	private Rectangle2D rectangle2d;

	public TransparentFrame(SliceRectangleHandler rectangleHandler) {
		this.sliceRectangle = new SliceRectangle(this, rectangleHandler);
		setFrameProperties();
	}

	/**
	 * This method will set all frame properties.
	 */
	private void setFrameProperties() {
		setDefaultLookAndFeelDecorated(true);
		rectangle2d = getScreenBounds();
		setSize((int) rectangle2d.getWidth(), (int) rectangle2d.getHeight());
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setVisible(true);
		setLocationRelativeTo(null);
		this.setOpacity(0.50f);
		addMouseListener(sliceRectangle);
		setFullScreenBufferedImage(rectangle2d);
		startTimer();
	}

	/**
	 * Repaint it is use to handle rectangle drawing
	 */
	private void startTimer() {
		Timer timer = new Timer(SliceConstants.TIMER_MILISECONDS, this);
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		sliceRectangle.drawRectangle((Graphics2D) g);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();

	}

	/**
	 * This method will calculate number of monitors connected with computer and
	 * calculate Width and Height
	 */
	private Rectangle2D getScreenBounds() {

		Rectangle2D rectangle2d = new Rectangle2D.Double();

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		for (GraphicsDevice graphicsDevice : graphicsEnvironment
				.getScreenDevices()) {

			for (GraphicsConfiguration graphicsConfiguration : graphicsDevice
					.getConfigurations()) {
				Rectangle2D.union(rectangle2d,
						graphicsConfiguration.getBounds(), rectangle2d);
			}

		}
		return rectangle2d;
	}

	private void setFullScreenBufferedImage(Rectangle2D rectangle2d) {

		try {
			Robot robot = new Robot();
			sliceRectangle.setFullScreenBufferedImage(robot
					.createScreenCapture(rectangle2d.getBounds()));
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method will update full screen buffer image. It will call from
	 * SliceSystemTray when user click on Small notification icon.
	 */
	public void updateFullScreenBuggeredImage() {
		setFullScreenBufferedImage(rectangle2d);
	}

}
