/*
* Copyright 2011 Danish Maritime Safety Administration. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice,
* this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright notice,
* this list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY Danish Maritime Safety Administration ``AS IS''
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

* The views and conclusions contained in the software and documentation are those
* of the authors and should not be interpreted as representing official policies,
* either expressed or implied, of Danish Maritime Safety Administration.
*
*/
package dk.frv.eavdam.utils;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 * Class for a JLabel representing link.
 */
public class LinkLabel extends JLabel {

	public static final long serialVersionUID = 1L;

	private String text;

	/**
	 * Constructor for giving the text for the label.
	 *
	 * @param text  Text for the label
	 */
	public LinkLabel(String text) {
		super(text);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enableEvents(MouseEvent.MOUSE_EVENT_MASK);
	}

	/**
	 * Sets the text for the label.
	 *
	 * @param text  Text for the label
	 */
	public void setText(String text) {
		super.setText("<html><font color=\"#0000CF\"><u>"+text+"</u></font></html>");
		this.text = text;
	}

	/**
	 * Sets the text for the label and makes the text red.
	 *
	 * @param text  Text for the label
	 */
	public void setRedText(String text) {
		super.setText("<html><font color=\"red\"><u>"+text+"</u></font></html>");
		this.text = text;
	}
	
	/**
	 * Gets label's text.
	 *
	 * @return  Label's text
	 */
	public String getNormalText() {
		return text;
	}

	protected void processMouseEvent(MouseEvent evt) {
		super.processMouseEvent(evt);
		if (evt.getID() == MouseEvent.MOUSE_CLICKED) {
			fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getNormalText()));
		}
	}

	/**
	 * Adds action listener.
	 *
	 * @param listener  Action listener to add
	 */
	public void addActionListener(ActionListener listener) {
		listenerList.add(ActionListener.class, listener);
	}

	/**
	 * Removes action listener.
	 *
	 * @param listener  Action listener to remove
	 */
	public void removeActionListener(ActionListener listener) {
		listenerList.remove(ActionListener.class, listener);
	}

	protected void fireActionPerformed(ActionEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i] == ActionListener.class) {
				ActionListener listener = (ActionListener)listeners[i+1];
				listener.actionPerformed(evt);
			}
		}
	}

}