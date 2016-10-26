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

package com.omt.slice.handler;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.omt.slice.common.ClipboardImage;
import com.omt.slice.common.SliceConstants;
import com.omt.slice.components.SliceClipboardOwner;
import com.omt.slice.components.SliceFileChooser;
import com.omt.slice.components.SliceSystemTray;

/**
 * This handler is use in Slice Rectangle.
 * 
 * @author omt
 * @author Dhiral Pandya (dhiralpandya@gmail.com)
 * @since 10 May 2013
 */
public class SliceRectangleHandler {

	private SliceFileChooser sliceFileChooser = new SliceFileChooser();
	private SliceClipboardOwner clipboardOwner = new SliceClipboardOwner();
	private SliceSystemTray sliceSystemTray;

	/**
	 * This method will call after mouse key release and rectangle draw on
	 * frame.
	 */
	public void onRectangleDrawComplete(JFrame jframe, Rectangle screenRect) {
		jframe.setVisible(false);
		if (validateRectangle(screenRect)) {

			BufferedImage bufferedImage = getBufferedImage(screenRect);
			addToClickBoard(bufferedImage);

			int btnClick = sliceFileChooser.showOpenDialog(jframe);
			if (btnClick == SliceFileChooser.APPROVE_OPTION) {
				File f = sliceFileChooser.getSelectedFile();
				System.out.println("Selected File :" + f.getParent() + " P :"
						+ f);
				f = validateFile(f);
				slice(f, bufferedImage);
				if (sliceSystemTray != null) {
					sliceSystemTray.displayMessage(
							SliceConstants.MSG_SAVE_SUCCESSFULLY,
							"File :" + f.getName());
				}
				// System.exit(0);
			}
		} else {
			// System.exit(0);
		}
	}

	/**
	 * This function validate file.
	 * 
	 * @param file
	 * @return
	 */
	private File validateFile(File file) {
		String filePath = file.getAbsolutePath();
		if (filePath.indexOf(".png") == -1) {
			filePath += ".png";
		}
		System.out.println("File Path :" + filePath);
		file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	private BufferedImage getBufferedImage(Rectangle screenRect) {
		BufferedImage bufferedImage = null;
		try {
			Robot robot = new Robot();
			bufferedImage = robot.createScreenCapture(screenRect);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bufferedImage;
	}

	private void slice(File f, BufferedImage bufferedImage) {
		try {

			ImageIO.write(bufferedImage, "png", f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean validateRectangle(Rectangle rectangle) {
		boolean isValid = false;
		if (rectangle.getWidth() > 0 && rectangle.getHeight() > 0) {
			isValid = true;
		}
		return isValid;
	}

	public void setSliceSystemTray(SliceSystemTray sliceSystemTray) {
		this.sliceSystemTray = sliceSystemTray;
	}

	private void addToClickBoard(BufferedImage bufferedImage) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard
				.setContents(new ClipboardImage(bufferedImage), clipboardOwner);
	}

}
