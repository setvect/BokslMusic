package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid;
import com.setvect.bokslmusic.ui.client.grid.TreeListGrid;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	public void onModuleLoad() {
		MainPanel a = new MainPanel();
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(a);
	}

	class MainPanel extends LayoutContainer {
		@Override
		protected void onRender(Element parent, int index) {
			super.onRender(parent, index);
			setLayout(new FlowLayout(10));

			this.setLayout(new AccordionLayout());

			ContentPanel allList = new ContentPanel();
			allList.add(new TreeListGrid());
			allList.setHeading("등록 목록");
			this.add(allList);

			ContentPanel playList = new ContentPanel();
			playList.setAnimCollapse(false);
			playList.setHeading("재생 목록");
			playList.addText("TEXT 1");

			this.add(playList);

			ContentPanel playControl = new ContentPanel();
			playControl.setAnimCollapse(false);
			playControl.setHeading("재생 창");
			playControl.addText("TEXT 2");
			this.add(playControl);

			ContentPanel setting = new ContentPanel();
			setting.setAnimCollapse(false);
			setting.setHeading("환경설정");
			SyncGrid syncGrid = new SyncGrid();
			setting.add(syncGrid);
			this.add(setting);
			this.setSize(350, 425);
		}
	}
}