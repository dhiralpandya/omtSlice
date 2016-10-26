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

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This is custom file chooser to save .png file.
 * 
 * @author omt
 * @author Dhiral Pandya (dhiralpandya@shipco.com)
 * @since 10 May 2013
 */
public class SliceFileChooser extends JFileChooser {

	public SliceFileChooser() {
		setFileChooserProperties();
	}

	/**
	 * Set properties for JFileChooser
	 */
	private void setFileChooserProperties() {
		setApproveButtonText("Save");
		setSelectedFile(new File("omt.png"));
		setCurrentDirectory(new java.io.File("."));
		setDialogTitle("Save Image");
		// setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		setAcceptAllFileFilterUsed(false);
		setFilter();
	}

	private void setFilter() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".png image", ".png");
		setFileFilter(filter);
	}

}
