package net.egaetan.OptServre.desktop;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;

public class DesktopApp {

	private String url;

	public DesktopApp(String url) {
		super();
		this.url = url;
	}

	public void launch() {
		addSystemTray();
		openWebSite();
	}
	
	private void openWebSite() {
		try
		{
		    URI uri = new URI(url);
		    Desktop dt = Desktop.getDesktop();
		    dt.browse(uri);
		}
		catch(Exception ex){}
	}
	
	private void addSystemTray() {
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage("/images/cptch.gif", "tray icon"));
        final SystemTray tray = SystemTray.getSystemTray();
       
        popup.add(new MenuItem("Exit")).addActionListener(e -> {
		    removeTrayIcon(trayIcon, tray);
		    System.exit(0);
		});;
       
        trayIcon.setPopupMenu(popup);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return ;
        }
        
        trayIcon.addActionListener(e -> openWebSite());
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Captcha - Try My Server");
        
        
	}

	private void removeTrayIcon(final TrayIcon trayIcon, final SystemTray tray) {
		tray.remove(trayIcon);
	}
	
	 //Obtain the image URL
    protected Image createImage(String path, String description) {
        URL imageURL = this.getClass().getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
