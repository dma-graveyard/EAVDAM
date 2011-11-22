package dk.frv.eavdam.utils;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class LinkLabel extends JLabel {

	public static final long serialVersionUID = 1L;

	private String text;

	public LinkLabel(String text) {
		super(text);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		enableEvents(MouseEvent.MOUSE_EVENT_MASK);
	}

	public void setText(String text) {
		super.setText("<html><font color=\"#0000CF\"><u>"+text+"</u></font></html>");
		this.text = text;
	}

	public String getNormalText() {
		return text;
	}

	protected void processMouseEvent(MouseEvent evt) {
		super.processMouseEvent(evt);
		if (evt.getID() == MouseEvent.MOUSE_CLICKED) {
			fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getNormalText()));
		}
	}

	public void addActionListener(ActionListener listener) {
		listenerList.add(ActionListener.class, listener);
	}

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