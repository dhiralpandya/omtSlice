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

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import com.omt.slice.common.SliceConstants;
import com.omt.slice.handler.SliceRectangleHandler;
import com.omt.slice.main.Main;

/**
 * This class create application System Tray and add it in OS SystemTray.
 * 
 * @author omt
 * @author Dhiral Pandya (dhiralpandya@gmail.com)
 * 
 */
public class SliceSystemTray {

	private SystemTray systemTray;
	private TrayIcon trayIcon;
	private SliceRectangleHandler sliceRectangleHandler;

	public SliceSystemTray(SliceRectangleHandler sliceRectangleHandler) {
		this.sliceRectangleHandler = sliceRectangleHandler;
		sliceRectangleHandler.setSliceSystemTray(this);
		systemTray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(getTrayImage(SliceConstants.SYSTEM_TRAY_ICON));

		setTrayProperties();
	}

	/**
	 * Set properties for SystemTray
	 */
	private void setTrayProperties() {

		trayIcon.setImageAutoSize(true);
		addHandler();

		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add all handler
	 */
	private void addHandler() {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.TRANSPARENT_FRAME.updateFullScreenBuggeredImage();
					Main.TRANSPARENT_FRAME.setVisible(true);
				} else {
					systemTray.remove(trayIcon);
					System.exit(0);
				}
			}
		};
		trayIcon.addMouseListener(mouseAdapter);
	}

	/**
	 * This will display popup messages
	 * 
	 * @param header
	 * @param message
	 */
	public void displayMessage(String header, String message) {
		trayIcon.displayMessage(header, message, TrayIcon.MessageType.INFO);
	}

	/**
	 * It will return try image
	 * 
	 * @param path
	 * @return
	 */
	private static Image getTrayImage(String path) {

		ImageIcon imageIcon = new ImageIcon(
				SliceSystemTray.class.getResource(path), "omt");
		return imageIcon.getImage();

	}

}
