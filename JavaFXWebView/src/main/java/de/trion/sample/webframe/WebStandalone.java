package de.trion.sample.webframe;

import de.trion.sample.util.SampleWebserver;
import java.awt.Desktop;
import java.net.URI;

/**
 * Sample to demonstrate running the web application as standalone
 */
public class WebStandalone
{
    public static void main(String[] args) throws Exception
    {
        SampleWebserver.serve();
        
        if(Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().browse(new URI("http://localhost:8081/sample.html"));
        }
    }

}
