package com.setvect.bokslmusic.ui.client;

import java.util.List;

import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.setvect.bokslmusic.ui.client.event.AllListGridEventListener;
import com.setvect.bokslmusic.ui.client.grid.AllListGrid;
import com.setvect.bokslmusic.ui.client.grid.PlayListGrid;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

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
			final AllListGrid allListGrid = new AllListGrid();
			this.add(allListGrid);

			final PlayListGrid playList = new PlayListGrid();
			this.add(playList);

			SyncGrid syncGrid = new SyncGrid();
			this.add(syncGrid);
			this.setSize(350, 425);

			allListGrid.addEvent(new AllListGridEventListener<GridEvent<MusicArticleModel>>() {
				public void addMusicEvent(List<MusicArticleModel> items) {
					for (MusicArticleModel a : items) {
						playList.addItem(a);
					}
				}

				public void handleEvent(GridEvent<MusicArticleModel> be) {
					MusicArticleModel model = be.getModel();
					if (!(model instanceof MusicDirectoryModel)) {	
						return;
					}

					MusicDirectoryModel directoryModel = (MusicDirectoryModel) model;
					Grid<MusicArticleModel> grid = be.getGrid();
					ListStore<MusicArticleModel> s = grid.getStore();
					List<MusicArticleModel> data = s.getRange(0, s.getCount());
					for (MusicArticleModel m : data) {
					}
				}
			});
		}
	}
}