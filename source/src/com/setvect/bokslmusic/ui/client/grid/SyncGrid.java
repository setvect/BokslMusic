/* 
 * Ext GWT 2.2.1 - Ext for GWT 
 * Copyright(c) 2007-2010, Ext JS, LLC. 
 * licensing@extjs.com 
 *  
 * http://extjs.com/license 
 */
package com.setvect.bokslmusic.ui.client.grid;

import java.util.ArrayList;
import java.util.Date;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.setvect.bokslmusic.ui.client.SyncButtonListener;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid.SyncGridButtonEvent.BehaviorType;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

public class SyncGrid extends LayoutContainer {
	private int gridHeight = 200;
	private ColumnModel cm;
	public ListStore<MusicDirectoryModel> store = new ListStore<MusicDirectoryModel>();
	private List<SyncButtonListener> buttonEventList = new ArrayList<SyncButtonListener>();

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		getAriaSupport().setPresentation(true);

		GridCellRenderer<MusicDirectoryModel> gridNumber = new GridCellRenderer<MusicDirectoryModel>() {
			public String render(MusicDirectoryModel model, String property, ColumnData config, int rowIndex,
					int colIndex, ListStore<MusicDirectoryModel> store, Grid<MusicDirectoryModel> grid) {
				Date syncDate = model.get(property);
				DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-dd");
				return format.format(syncDate);
			}
		};

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig("basePath", "BasePath", 100);
		column.setRowHeader(true);
		configs.add(column);

		column = new ColumnConfig("syncDate", "Last Updated", 100);
		column.setAlignment(HorizontalAlignment.RIGHT);
		column.setRenderer(gridNumber);
		configs.add(column);

		column = new ColumnConfig("basePath", "동기화", 100);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setRenderer(new ButtonRenderer(BehaviorType.SYNC));
		configs.add(column);

		column = new ColumnConfig("basePath", "삭제", 100);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setRenderer(new ButtonRenderer(BehaviorType.DELETE));
		configs.add(column);
		cm = new ColumnModel(configs);

		ContentPanel cp = new ContentPanel();
		// cp.setIcon(Resources.ICONS.table());
		cp.setHeaderVisible(false);
		cp.setButtonAlign(HorizontalAlignment.CENTER);
		cp.setLayout(new FitLayout());
		cp.setHeight(gridHeight);

		final Grid<MusicDirectoryModel> grid = new Grid<MusicDirectoryModel>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("basePath");
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

	class ButtonRenderer implements GridCellRenderer<MusicDirectoryModel> {
		private boolean init;
		private BehaviorType behaviorType;

		public ButtonRenderer(BehaviorType behavior) {
			behaviorType = behavior;
		}

		public Object render(final MusicDirectoryModel model, String property, ColumnData config, final int rowIndex,
				final int colIndex, ListStore<MusicDirectoryModel> store, Grid<MusicDirectoryModel> grid) {
			if (!init) {
				init = true;
				grid.addListener(Events.ColumnResize, new Listener<GridEvent<MusicDirectoryModel>>() {
					public void handleEvent(GridEvent<MusicDirectoryModel> be) {
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
			String path = model.get(property);
			SyncGridButtonEvent eventParam = new SyncGridButtonEvent(behaviorType, path, rowIndex);
			SelectionListener<ButtonEvent> listener = new GridButtonListener(eventParam);
			Button b = new Button(col.getHeader(), listener);
			b.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
			b.setToolTip("Click for more information");
			return b;
		}

		/**
		 * 동기화 버튼 이벤트
		 */
		class GridButtonListener extends SelectionListener<ButtonEvent> {
			private SyncGridButtonEvent eventObj;

			public GridButtonListener(SyncGridButtonEvent eventParam) {
				this.eventObj = eventParam;
			}

			@Override
			public void componentSelected(ButtonEvent ce) {
				for (SyncButtonListener event : buttonEventList) {
					event.onClick(eventObj);
				}
			}
		}
	};

	public static class SyncGridButtonEvent {
		public enum BehaviorType {
			DELETE, SYNC
		}

		private String path;
		private BehaviorType behaviorType;
		private int rowIndex;

		public SyncGridButtonEvent(BehaviorType behaviorType, String path, int rowIndex) {
			this.path = path;
			this.behaviorType = behaviorType;
			this.rowIndex = rowIndex;
		}

		public String getPath() {
			return path;
		}

		public BehaviorType getBehaviorType() {
			return behaviorType;
		}

		public int getRowIndex() {
			return rowIndex;
		}
	}

	public void addSyncButtonListener(SyncButtonListener syncButtonListenerImpl) {
		buttonEventList.add(syncButtonListenerImpl);
	}
}