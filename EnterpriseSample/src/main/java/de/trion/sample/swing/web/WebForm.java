package de.trion.sample.swing.web;

import de.trion.sample.controller.ContractController;
import de.trion.sample.controller.HybridNavigationController;
import de.trion.sample.model.Contract;
import de.trion.sample.service.ContractService;
import de.trion.sample.util.ModelConverter;
import java.awt.BorderLayout;
import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javax.swing.JPanel;
import netscape.javascript.JSObject;

public class WebForm extends JPanel
{
    private ContractService contractService;
    private ContractController contractController;
    private HybridNavigationController navigationController;
    
    private JFXPanel fxContainer;
    private WebView webView;
    
    public WebForm(ContractService contractService, ContractController contractController, HybridNavigationController navigationController)
    {
        this.contractService = contractService;
        this.contractController = contractController;
        this.navigationController = navigationController;
        
        initComponents();
    }

    public void navigate(String target)
    {
        Platform.runLater(() ->
        {
            webView.getEngine().executeScript("window.location.hash = '#"+target+"'");
            
        });
    }

    private void initComponents()
    {
        fxContainer = new JFXPanel();
        
        //fxContainer.setPreferredSize(dimension);

        setLayout(new BorderLayout());
        add(fxContainer, BorderLayout.CENTER);

        Platform.runLater(() ->
        {
            webView = new WebView();

            AnchorPane anchorPane = new AnchorPane();
            AnchorPane.setTopAnchor(webView, 0.0);
            AnchorPane.setBottomAnchor(webView, 0.0);
            AnchorPane.setLeftAnchor(webView, 0.0);
            AnchorPane.setRightAnchor(webView, 0.0);

            anchorPane.getChildren().add(webView);

            final Scene scene = new Scene(anchorPane);

            fxContainer.setScene(scene);

            final WebEngine webEngine = webView.getEngine();

            //register api
            JSObject win = (JSObject) webEngine.executeScript("window");
            win.setMember("appFrame", new ApiBridge());

            //adjust styling
            URL resource = getClass().getResource("/app-styles.css");
            String css = resource.toExternalForm();
            webView.getEngine().setUserStyleSheetLocation(css);
            
            //show errors in java stdout
            webEngine.setOnError((WebErrorEvent event) ->
            {
                System.out.println("Error: " + event.getMessage());
            });

            webEngine.setJavaScriptEnabled(true);

            webEngine.load("http://127.0.0.1:8081/sample.html");
        });
    }

    public class ApiBridge
    {
        public void sendMail(JSObject contract)
        {
            Contract contractJava = ModelConverter.contractFrom(contract);
            navigationController.sendMail(contractJava);
        }
        
        public void updateStatus(String status)
        {
            navigationController.statusUpdate(status);
        }
    }
}
