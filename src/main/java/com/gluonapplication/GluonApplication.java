package com.gluonapplication;

import java.net.URISyntaxException;

import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.visual.Swatch;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GluonApplication extends MobileApplication {

	public GluonApplication() throws URISyntaxException {
		String[] transports = new String[]{WebSocket.NAME};
	    long timeout = 1000;
		IO.Options options = IO.Options.builder()
		        .setTransports(transports)
		        .setTimeout(timeout)
		        .setForceNew(true)
		        .build();

	    Socket socket = IO.socket("http://192.168.1.96:3000", options);
	    socket.connect();
	    socket.disconnect();
	}
	
    @Override
    public void init() {
        addViewFactory(HOME_VIEW, BasicView::new);
    }

    @Override
    public void postInit(Scene scene) {
        Swatch.BLUE.assignTo(scene);

        ((Stage) scene.getWindow()).getIcons().add(new Image(GluonApplication.class.getResourceAsStream("/icon.png")));
    }

    public static void main(String args[]) {
        launch(args);
    }
}