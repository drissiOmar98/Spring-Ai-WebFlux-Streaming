package dev.omar.frontend;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * 🌐 MainView – The main Vaadin UI view for interacting with the AI backend.
 * <p>
 * 🛠 Features:
 * - Provides a text area for user input (`Question`)
 * - Displays the AI response in another read-only text area (`Answer`)
 * - Sends messages to the backend NDJSON streaming endpoint (`/chat`) via {@link WebClient}
 * - Streams AI tokens reactively and updates the UI in real-time
 * - Handles errors gracefully and shows notifications
 * - Uses Vaadin push (@Push in AppShell) for real-time streaming
 * <p>
 * 🎨 Styling:
 * - Uses `cyberpunk.css` for neon/cyberpunk look
 * - Class names prefixed with "cp-" for easy customization
 * <p>
 * 🔑 Notes:
 * - `backend.url` is injected from application.properties
 * - Supports canceling previous streams when a new request is sent
 * - Automatically disposes active streams on view detach to prevent memory leaks
 */
@Route("")
@CssImport("./styles/cyberpunk.css")
public class MainView extends VerticalLayout {

    /**
     * 🌐 Backend URL injected from application properties
     */
    @Value("${backend.url}")
    private String backendUrl;

    /**
     * 🔗 WebClient for calling backend endpoints
     */
    private final WebClient webClient;

    /**
     * 📝 Input TextArea for user question
     */
    private final TextArea input = new TextArea("Question");

    /**
     * 📝 Read-only TextArea for AI answer
     */
    private final TextArea answer = new TextArea("Answer");

    /**
     * 🔘 Button to send user input to backend
     */
    private final Button ask = new Button("Ask");

    /**
     * ♻ Disposable for managing active reactive stream
     */
    private Disposable activeStream;

    /**
     * Constructor – initializes the view with backend URL and WebClient builder
     *
     * @param backendUrl backend base URL
     * @param builder    WebClient.Builder for reactive calls
     */
    public MainView(@Value("${backend.url}") String backendUrl, WebClient.Builder builder) {
        this.backendUrl = backendUrl;
        this.webClient = builder.baseUrl(backendUrl).build();
        createViewElements();
    }




}
