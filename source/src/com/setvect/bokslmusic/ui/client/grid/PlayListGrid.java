package com.setvect.bokslmusic.ui.client.grid;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.google.gwt.user.client.Element;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;

public class PlayListGrid extends LayoutContainer {
	private Grid<MusicArticleModel> grid;

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		getAriaSupport().setPresentation(true);

		GridCellRenderer<MusicArticleModel> timeRenderer = new GridCellRenderer<MusicArticleModel>() {
			public String render(MusicArticleModel model, String property, ColumnData config, int rowIndex,
					int colIndex, ListStore<MusicArticleModel> store, Grid<MusicArticleModel> grid) {
				int sec = model.get(property);
				return ClientUtil.getMinuteSec(sec);
			}
		};

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		RowNumberer r = new RowNumberer();
		configs.add(r);

		ColumnConfig columnName = new ColumnConfig("name", "이름", 100);
		configs.add(columnName);

		ColumnConfig columnRunningTime = new ColumnConfig("runningTime", "시간", 100);
		columnRunningTime.setRenderer(timeRenderer);
		configs.add(columnRunningTime);

		ColumnConfig columnDelete = new ColumnConfig("model", "삭제", 100);
		columnDelete.setRenderer(new ButtonRenderer());
		configs.add(columnDelete);

		ListStore<MusicArticleModel> store = new ListStore<MusicArticleModel>();

		ColumnModel cm = new ColumnModel(configs);

		grid = new Grid<MusicArticleModel>(store, cm);
		grid.addPlugin(r);
		grid.setAutoExpandColumn("name");
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.setHeight(150);
		add(grid);
	}

	/**
	 * 
	 * @param saveList
	 *            재생 목록 추가 데이터
	 */
	public void addPlayItem(List<MusicArticleModel> saveList) {
		ListStore<MusicArticleModel> store = grid.getStore();
		List<MusicArticleModel> beforeList = store.getModels();
		for (MusicArticleModel p : saveList) {
			if (!beforeList.contains(p)) {
				store.add(p);
			}
		}
	}

	class ButtonRenderer implements GridCellRenderer<MusicArticleModel> {
		private boolean init;

		public Object render(final MusicArticleModel model, String property, ColumnData config, final int rowIndex,
				final int colIndex, ListStore<MusicArticleModel> store, Grid<MusicArticleModel> grid) {
			if (!init) {
				init = true;
				grid.addListener(Events.ColumnResize, new Listener<GridEvent<MusicArticleModel>>() {
					public void handleEvent(GridEvent<MusicArticleModel> be) {
						for (int i = 0; i < be.getGrid().getStore().getCount(); i++) {
							if (be.getGrid().getView().getWidget(i, be.getColIndex()) != null
									&& be.getGrid().getView().getWidget(i, be.getColIndex()) instanceof BoxComponent) {
								((BoxComponent) be.getGrid().getView().getWidget(i, be.getColIndex())).setWidth(be
										.getWidth() - 10);
							}
						}
					}
				});
			}

			ColumnConfig col = grid.getColumnModel().getColumn(colIndex);
			Button b = new Button(col.getHeader(), new GridButtonListener(model));

			b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
			return b;
		}

		class GridButtonListener extends SelectionListener<ButtonEvent> {
			private MusicArticleModel model;

			GridButtonListener(MusicArticleModel model) {
				this.model = model;
			}

			@Override
			public void componentSelected(ButtonEvent ce) {
				grid.getStore().remove(model);
			}
		}
	};
}