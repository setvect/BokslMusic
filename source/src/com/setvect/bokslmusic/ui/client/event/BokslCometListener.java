package com.setvect.bokslmusic.ui.client.event;

import java.io.Serializable;
import java.util.List;

import net.zschech.gwt.comet.client.CometListener;

/**
 * Comet 이벤트를 총괄 해서 받음
 */
public class BokslCometListener implements CometListener {
	public void onConnected(int heartbeat) {
	}

	public void onDisconnected() {
	}

	public void onHeartbeat() {
	}

	public void onRefresh() {
	}

	public void onError(Throwable exception, boolean connected) {
	}

	public void onMessage(List<? extends Serializable> messages) {
		for (Serializable message : messages) {
			System.out.println("receive " + message );
		}
	}
}
