package de.trion.sample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import netscape.javascript.JSObject;

public class JavaScriptBridge
{
    private static final int JFXPANEL_WIDTH_INT = 800;
    private static final int JFXPANEL_HEIGHT_INT = 600;
    
    private static final String document = 
             "<html>"
            + "<head></head>"
            + "<body>"
            + "<h1>HTML Sample</h1>"
            + "Click me: <button onclick='app.trigger();'>trigger</button>"
            + "</body>"
            + "</html>";

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() ->
        {
            final Dimension dimension = new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT);
            
            final JFrame frame = new JFrame("JavaFX WebView");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            final JFXPanel fxContainer;
            fxContainer = new JFXPanel();
            fxContainer.setPreferredSize(dimension);
            frame.add(fxContainer, BorderLayout.CENTER);
            
            Platform.runLater(() ->
            {
                final WebView webView = new WebView();
                webView.setMinSize(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT);
                
                final Group group = new Group();
                group.getChildren().add(webView);
                
                final Scene scene = new Scene(group);
                
                fxContainer.setScene(scene);
                
                final WebEngine webEngine = webView.getEngine();
                
                //add callback for publishing java api
                webEngine.getLoadWorker().stateProperty().addListener(
                        (ObservableValue<? extends State> ov, State oldState, State newState) ->
                {
                    if (newState == State.SUCCEEDED)
                    {
                        JSObject win = (JSObject) webEngine.executeScript("window");
                        win.setMember("app", new JavaApp());
                    }
                });
                webEngine.setOnError((WebErrorEvent event) ->
                {
                    System.out.println("Error: " + event.getMessage());
                });
 
                
                webEngine.loadContent(document);
            });
            
            frame.setPreferredSize(dimension);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }

    //Java-JavaScript API
    public static class JavaApp
    {
        public void trigger()
        {
            JOptionPane.showMessageDialog(null, "Java Dialog triggered from the web!");
        }

    }
}
