package com.setvect.bokslmusic.ui.client.grid;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.setvect.bokslmusic.ui.client.model.PlayItemModel;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;

public class PlayGrid extends LayoutContainer {
	private int gridHeight = 200;
	private ColumnModel cm;

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		getAriaSupport().setPresentation(true);

		GridCellRenderer<PlayItemModel> timeRenderer = new GridCellRenderer<PlayItemModel>() {
			public String render(PlayItemModel model, String property, ColumnData config, int rowIndex, int colIndex,
					ListStore<PlayItemModel> store, Grid<PlayItemModel> grid) {
				int sec = model.get(property);
				return ClientUtil.getMinuteSec(sec);
			}
		};

		GridCellRenderer<PlayItemModel> buttonRenderer = new GridCellRenderer<PlayItemModel>() {
			private boolean init;

			public Object render(final PlayItemModel model, String property, ColumnData config, final int rowIndex,
					final int colIndex, ListStore<PlayItemModel> store, Grid<PlayItemModel> grid) {
				if (!init) {
					init = true;
					grid.addListener(Events.ColumnResize, new Listener<GridEvent<PlayItemModel>>() {
						public void handleEvent(GridEvent<PlayItemModel> be) {
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

				Button b = new Button((String) model.get(property), new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
					}
				});
				b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
				b.setToolTip("Click for more information");

				return b;
			}
		};

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig("name", "No.", 100);
		column.setRowHeader(true);
		configs.add(column);

		column = new ColumnConfig("name", "이름", 100);
		column.setAlignment(HorizontalAlignment.RIGHT);
		configs.add(column);

		column = new ColumnConfig("runningTime", "시간", 100);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setRenderer(timeRenderer);
		configs.add(column);

		column = new ColumnConfig("name", "삭제", 100);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setRenderer(buttonRenderer);
		configs.add(column);

		ListStore<PlayItemModel> store = new ListStore<PlayItemModel>();
		store.add(getTempData());

		cm = new ColumnModel(configs);

		ContentPanel cp = new ContentPanel();
		// cp.setIcon(Resources.ICONS.table());
		cp.setHeaderVisible(false);
		cp.setButtonAlign(HorizontalAlignment.CENTER);
		cp.setLayout(new FitLayout());
		cp.setHeight(gridHeight);

		final Grid<PlayItemModel> grid = new Grid<PlayItemModel>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("name");
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		cp.add(grid);

		add(cp);
	}

	/**
	 * @return 그리드 높이
	 */
	public int getGridHeight() {
		return gridHeight;
	}

	/**
	 * @param gridHeight
	 *            그리드 높이
	 */
	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	private static List<PlayItemModel> getTempData() {
		List<PlayItemModel> stocks = new ArrayList<PlayItemModel>();

		PlayItemModel dir1Model = new PlayItemModel("song1.mp3", 100);
		stocks.add(dir1Model);

		PlayItemModel dir2Model = new PlayItemModel("song2.mp3", 120);
		stocks.add(dir2Model);
		return stocks;
	}
}