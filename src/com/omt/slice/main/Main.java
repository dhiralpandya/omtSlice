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

package com.omt.slice.main;

import java.awt.SystemTray;

import javax.swing.SwingUtilities;

import com.omt.slice.components.SliceSystemTray;
import com.omt.slice.frames.TransparentFrame;
import com.omt.slice.handler.SliceRectangleHandler;

/**
 * This is main class. that contains main status methods. It's a entry point of
 * Slice App
 * 
 * @author omt
 * @author Dhiral Pandya (dhiralpandya@gmail.com)
 * @since 10 May 2013
 * @version 1.2.4 (It is Application Current Version that are in Sourceforge)
 */
public class Main {

	public static TransparentFrame TRANSPARENT_FRAME;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				SliceRectangleHandler sliceRectangleHandler = new SliceRectangleHandler();
				if (SystemTray.isSupported()) {
					SliceSystemTray sliceSystemTray = new SliceSystemTray(
							sliceRectangleHandler);
				}
				TRANSPARENT_FRAME = new TransparentFrame(sliceRectangleHandler);

			}
		});
	}

}
