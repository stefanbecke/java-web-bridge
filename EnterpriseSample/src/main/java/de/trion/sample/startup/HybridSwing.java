package de.trion.sample.startup;

import javafx.application.Platform;

public class HybridSwing extends LegacySwing
{
    public HybridSwing()
    {
        Platform.setImplicitExit(false);
    }
}
