package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class GxApp implements EntryPoint {
	Viewport viewport = new Viewport();
	private HtmlContainer northPanel;
	private ContentPanel centerPanel;
	private ContentPanel westPanel;
	private ContentPanel eastPanel;
	private ContentPanel southPanel;

	public void onModuleLoad() {
		viewport.setLayout(new BorderLayout());
		createNorth();
		createCenter();
		setTabitem();
		createWest();
		createEast();
		createSouth();
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(viewport);
	}

	private void setTabitem() {
		TabPanel tabPanel = new TabPanel();
		tabPanel.setCloseContextMenu(true);
		tabPanel.setBorderStyle(false);
		tabPanel.setBodyBorder(false);
		tabPanel.setTabScroll(true);
		tabPanel.setAnimScroll(true);
		centerPanel.add(tabPanel);

	}

	private void createSouth() {
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.SOUTH, 220, 150, 320);
		data.setMargins(new Margins(5, 5, 5, 5));
		data.setCollapsible(true);
		southPanel = new ContentPanel();
		ToolBar toolBar = new ToolBar();
		southPanel.setTopComponent(toolBar);
		viewport.add(southPanel, data);
	}

	private void createNorth() {
		StringBuffer sb = new StringBuffer();
		sb.append("<B>Ext GWT Demo</B>");
		northPanel = new HtmlContainer(sb.toString());
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
		data.setMargins(new Margins());
		viewport.add(northPanel, data);
	}

	private void createCenter() {
		centerPanel = new ContentPanel();
		centerPanel.setBorders(false);
		centerPanel.setHeaderVisible(false);
		centerPanel.setLayout(new FitLayout());
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.CENTER);
		data.setMargins(new Margins(5, 5, 5, 0));
		viewport.add(centerPanel, data);
	}

	private void createWest() {
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST, 220, 150, 320);
		data.setMargins(new Margins(5, 5, 5, 5));
		data.setCollapsible(true);
		westPanel = new ContentPanel();
		ToolBar toolBar = new ToolBar();
		westPanel.setTopComponent(toolBar);
		viewport.add(westPanel, data);
	}

	private void createEast() {
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.EAST, 220, 150, 320);
		data.setMargins(new Margins(5, 5, 5, 5));
		data.setCollapsible(true);
		eastPanel = new ContentPanel();
		ToolBar toolBar = new ToolBar();
		eastPanel.setTopComponent(toolBar);
		viewport.add(eastPanel, data);
	}
}
