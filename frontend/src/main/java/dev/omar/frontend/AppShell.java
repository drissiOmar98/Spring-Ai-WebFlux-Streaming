package dev.omar.frontend;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.communication.PushMode;

/**
 * 🌐 AppShell configuration for the Vaadin frontend
 *
 * This class implements {@link AppShellConfigurator} to customize
 * the application shell settings for the Vaadin app.
 *
 * 🛠 Features:
 * - Enables **automatic server push** with {@link PushMode#AUTOMATIC}
 *   to allow real-time updates in the UI without manual refresh
 * - Provides a central place to configure global Vaadin settings if needed
 */
@Push(PushMode.AUTOMATIC)
public class AppShell implements AppShellConfigurator {
}
