package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	Viewport viewport = new Viewport();

	public void onModuleLoad() {

		LayoutContainer warp = new LayoutContainer(new RowLayout());
		warp.setBorders(true);

		LayoutContainer hearder = new LayoutContainer();
		Label titleHeader = new Label("복슬 Music");
		hearder.add(titleHeader);
		warp.add(hearder);

		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(warp);
	}
}
