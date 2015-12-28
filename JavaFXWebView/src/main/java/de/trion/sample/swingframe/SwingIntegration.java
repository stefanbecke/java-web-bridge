package de.trion.sample.swingframe;

import de.trion.sample.util.SampleWebserver;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import netscape.javascript.JSObject;

/**
 * Sample of the web-swing integration
 */
public class SwingIntegration
{
    private static final int JFXPANEL_WIDTH_INT = 800;
    private static final int JFXPANEL_HEIGHT_INT = 600;
    
    public static void main(String[] args) throws Exception
    {
        SampleWebserver.serve();
        
        SwingIntegration app = new SwingIntegration();
        app.gui();
    }

    private void gui()
    {
        SwingUtilities.invokeLater(() ->
        {
            final Dimension dimension = new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT);
            
            final JFrame frame = new JFrame("Swing/Web Integration Demo");
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
                        (ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) ->
                {
                    if (newState == Worker.State.SUCCEEDED)
                    {
                        JSObject win = (JSObject) webEngine.executeScript("window");
                        win.setMember("appFrame", new JavaApp());
                    }
                });
                webEngine.setOnError((WebErrorEvent event) ->
                {
                    System.out.println("Error: " + event.getMessage());
                });
 
                webEngine.setJavaScriptEnabled(true);
                
                webEngine.load("http://127.0.0.1:8081/sample.html");
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
        public void sendMail(JSObject contract)
        {
            SendMailForm sendMailForm = new SendMailForm();
            sendMailForm.nameLabel.setText((String) contract.getMember("name"));
            sendMailForm.companyLabel.setText((String) contract.getMember("company"));
            sendMailForm.setVisible(true);
        }
        
        public String getInvoices()
        {
            final List<Invoice> invoices = new ArrayList<>();
            
            invoices.add(new Invoice("acme-1", "2015-08-01", 1, true));
            invoices.add(new Invoice("wonder-1", "2015-09-01", 2, true));
            invoices.add(new Invoice("sun-1", "2012-01-01", 3, true));
            
            String json = "["+
                    invoices.stream()
                          .map(Invoice::toJSON)
                          .collect(Collectors.joining(", "))
                    +"]";

            return json;
        }
    }
    

    public static class Invoice
    {
        public String number;
        public String date;
        public Integer companyId;
        public boolean paid;

        public Invoice()
        {
        }
        
        public Invoice(String number, String date, Integer companyId, boolean paid)
        {
            this.number = number;
            this.date = date;
            this.companyId = companyId;
            this.paid = paid;
        }

        public String getNumber()
        {
            return number;
        }

        public void setNumber(String number)
        {
            this.number = number;
        }

        public String getDate()
        {
            return date;
        }

        public void setDate(String date)
        {
            this.date = date;
        }

        public Integer getCompanyId()
        {
            return companyId;
        }

        public void setCompanyId(Integer companyId)
        {
            this.companyId = companyId;
        }

        public boolean isPaid()
        {
            return paid;
        }

        public void setPaid(boolean paid)
        {
            this.paid = paid;
        }
        
        public String toJSON()
        {
            return "{ \"number\": \"" + number + "\","
                    + "\"date\": \"" + date + "\","
                    + "\"companyId\": \"" + companyId + "\" ,"
                    + "\"paid\": " + paid +"}";
        }
        
        
    }
}
