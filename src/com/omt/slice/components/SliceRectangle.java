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

package com.omt.slice.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.omt.slice.handler.SliceRectangleHandler;

/**
 * This class contains all require methods and variable to draw rectangle
 * 
 * @author omt
 * @author Dhiral Pandya (dhiralpandya@gmail.com)
 * @since 10 May 2013
 */
public class SliceRectangle implements MouseListener {

	private int fromX = 0;
	private int fromY = 0;

	private int toX = 0;
	private int toY = 0;

	private boolean isMousePressed = false;

	private BufferedImage fullScreenBufferedImage;

	private SliceRectangleHandler sliceRectangleHandler;
	private JFrame parent;

	public SliceRectangle(JFrame parent,
			SliceRectangleHandler sliceRectangleHandler) {
		this.parent = parent;
		this.sliceRectangleHandler = sliceRectangleHandler;
	}

	/**
	 * This method is use to calculate rectangle.
	 * 
	 * @return
	 */
	public Rectangle getRectangle() {
		Rectangle rectangle = new Rectangle(fromX, fromY, getRectangleWidth(),
				getRectangleHeight());
		return rectangle;

	}

	public void drawRectangle(Graphics2D g) {
		if (isMousePressed) {
			calculateToXY();
			g.setColor(Color.RED);
			g.drawImage(fullScreenBufferedImage, fromX, fromY, toX, toY, fromX,
					fromY, toX, toY, parent);
			g.drawRect(fromX, fromY, getRectangleWidth(), getRectangleHeight());

		}
	}

	private void calculateToXY() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		toX = p.x;
		toY = p.y;
	}

	private void calculateFromXY() {
		Point p = MouseInfo.getPointerInfo().getLocation();
		fromX = p.x;
		fromY = p.y;
	}

	private int getRectangleWidth() {
		return (toX - fromX);
	}

	private int getRectangleHeight() {
		return (toY - fromY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isMousePressed = true;
		calculateFromXY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isMousePressed = false;
		calculateToXY();
		this.sliceRectangleHandler.onRectangleDrawComplete(parent,
				getRectangle());
	}

	/**
	 * This is full screen image that is use to remove transparency for selected
	 * rectangle.
	 * 
	 * @param fullScreenBufferedImage
	 */
	public void setFullScreenBufferedImage(BufferedImage fullScreenBufferedImage) {
		this.fullScreenBufferedImage = fullScreenBufferedImage;
	}

}
