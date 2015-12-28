package de.trion.sample;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class JavaFXWebView
{

    private static final int JFXPANEL_WIDTH_INT = 800;
    private static final int JFXPANEL_HEIGHT_INT = 600;

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
            
            // create JavaFX scene
            Platform.runLater(() ->
            {
                final WebView webView = new WebView();
                webView.setMinSize(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT);
                
                final Group group = new Group();
                group.getChildren().add(webView);
                
                final Scene scene = new Scene(group);
                
                fxContainer.setScene(scene);
                
                final WebEngine webEngine = webView.getEngine();
                webEngine.load("https://www.google.com/");
            });
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }

}
