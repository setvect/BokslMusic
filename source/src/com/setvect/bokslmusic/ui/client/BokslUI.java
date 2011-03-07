package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	public void onModuleLoad() {
		Viewport warp = new Viewport();
		warp.setLayout(new BorderLayout());
		warp.setBorders(true);
		LayoutContainer hearder = header();
		warp.add(hearder, new BorderLayoutData(LayoutRegion.NORTH, 30));
		warp.add(body(), new BorderLayoutData(LayoutRegion.CENTER));
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(warp);
	}

	private LayoutContainer body() {
		LayoutContainer body = new LayoutContainer();

		body.setLayout(new BorderLayout());
		ContentPanel northPanel = new ContentPanel();
		northPanel.add(new Text("11111111111"));
		BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 100, 50, 200);
		northData.setCollapsible(true);
		northData.setMargins(new Margins(5, 5, 5, 5));
		body.add(northPanel, northData);

		ContentPanel centerPanel = new ContentPanel();
		centerPanel.add(new Text("22222222222"));
		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER, 100, 50, 200);
		centerData.setCollapsible(true);
		centerData.setMargins(new Margins(5, 5, 5, 5));
		body.add(centerPanel, centerData);

		return body;
	}

	private LayoutContainer header() {
		LayoutContainer hearder = new LayoutContainer();
		Label titleHeader = new Label("복슬 Music");
		hearder.add(titleHeader);
		return hearder;
	}
}
