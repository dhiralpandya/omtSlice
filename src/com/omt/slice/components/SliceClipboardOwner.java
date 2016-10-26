package com.omt.slice.components;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;

public class SliceClipboardOwner implements ClipboardOwner {

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		System.out.println("Lost Clipboard Ownership");
	}

}
