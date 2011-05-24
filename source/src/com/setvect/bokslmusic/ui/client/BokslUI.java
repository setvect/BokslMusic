package com.setvect.bokslmusic.ui.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	private SyncPannel syncPannel = new SyncPannel();
	private PlayPannel playPannel = new PlayPannel();
	private ListPannel listPannel = new ListPannel(this);

	public void onModuleLoad() {
		HorizontalPanel warp = new HorizontalPanel();
		warp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		warp.getElement().setId("warp");

		VerticalPanel contentBody = new VerticalPanel();
		warp.add(contentBody);
		contentBody.getElement().setId("contentBody");
		contentBody.add(headerPannel());

		contentBody.add(syncPannel);
		contentBody.add(playPannel);
		contentBody.add(listPannel);

		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(warp);
	}

	private Widget headerPannel() {
		Label titleHeader = new Label("ABC");
		return titleHeader;
	}

	SyncPannel getSyncPannel() {
		return syncPannel;
	}

	PlayPannel getPlayPannel() {
		return playPannel;
	}

	ListPannel getListPannel() {
		return listPannel;
	}
}