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
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.client.BokslMusicEventListener;
import com.setvect.bokslmusic.ui.client.grid.SyncGrid.SyncGridButtonEvent.BehaviorType;
import com.setvect.bokslmusic.ui.client.service.SyncService;
import com.setvect.bokslmusic.ui.client.service.SyncServiceAsync;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

public class SyncGrid extends LayoutContainer {
	private int gridHeight = 200;
	private ColumnModel cm;
	private final SyncServiceAsync syncService = GWT.create(SyncService.class);
	/** 그리드 데이터 */
	private ListStore<MusicDirectoryModel> store = new ListStore<MusicDirectoryModel>();
	private List<BokslMusicEventListener> buttonEventList = new ArrayList<BokslMusicEventListener>();
	private ButtonRenderer syncButton;
	private FitLayout fitLayout;

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
		syncButton = new ButtonRenderer(BehaviorType.SYNC);
		column.setRenderer(syncButton);
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
		fitLayout = new FitLayout();
		cp.setLayout(fitLayout);
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
	 * 그리드 사이즈 맞춤
	 */
	public void fitLayout() {
		fitLayout.layout();
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

	public void addSyncButtonListener(BokslMusicEventListener syncButtonListenerImpl) {
		buttonEventList.add(syncButtonListenerImpl);
	}

	/**
	 * 동기화 버튼 비활성화
	 */
	public void disableSyncButton() {
		syncButton.disableButton();
	}

	/**
	 * 동기화 버튼 활성화
	 */
	public void enableSyncButton() {
		syncButton.enableButton();
	}

	/**
	 * 동기화 디렉토리 목록 처리 Callback
	 */
	public void reloadSyncList() {
		syncService.getSyncList(new AsyncCallback<List<MusicDirectoryModel>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<MusicDirectoryModel> result) {
				List<MusicDirectoryModel> list = new ArrayList<MusicDirectoryModel>();
				for (MusicDirectoryModel n : result) {
					// TODO 왜 new Data()을 사용했을까?
					MusicDirectoryModel a = new MusicDirectoryModel(n.getBasePath(), new Date());
					list.add(a);
				}
				store.removeAll();
				store.add(list);
			}
		});
	}

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

	class ButtonRenderer implements GridCellRenderer<MusicDirectoryModel> {
		private boolean init;
		private BehaviorType behaviorType;
		private Button button;

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
			button = new Button(col.getHeader(), listener);
			button.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
			button.setToolTip("Click for more information");
			return button;
		}

		public void disableButton() {
			button.disable();
		}

		public void enableButton() {
			button.enable();
		}

		/**
		 * 동기화 버튼 이벤트
		 */
		class GridButtonListener extends SelectionListener<ButtonEvent> {
			private Object eventObj;

			public GridButtonListener(Object eventParam) {
				this.eventObj = eventParam;
			}

			@Override
			public void componentSelected(ButtonEvent ce) {
				for (BokslMusicEventListener event : buttonEventList) {
					event.onClick(eventObj);
				}
			}
		}
	};
}