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
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.client.service.SyncService;
import com.setvect.bokslmusic.ui.client.service.SyncServiceAsync;
import com.setvect.bokslmusic.ui.shared.model.SyncDirectoryModel;

public class SyncGrid extends ContentPanel {
	private ColumnModel cm;
	private final SyncServiceAsync syncService = GWT.create(SyncService.class);
	/** 그리드 데이터 */
	private ListStore<SyncDirectoryModel> store = new ListStore<SyncDirectoryModel>();
	private ButtonRenderer syncButton = new ButtonRenderer();

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		getAriaSupport().setPresentation(true);
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		ColumnConfig column = new ColumnConfig("basePath", "디렉토리", 100);
		column.setRowHeader(true);
		column.setSortable(false);
		configs.add(column);

		column = new ColumnConfig("basePath", "동기화", 100);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setSortable(false);
		column.setRenderer(syncButton);
		configs.add(column);

		cm = new ColumnModel(configs);

		final Grid<SyncDirectoryModel> grid = new Grid<SyncDirectoryModel>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("basePath");
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		add(grid);
		setHeading("환경설정");

		reloadSyncList();
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
		syncService.getSyncList(new AsyncCallback<List<SyncDirectoryModel>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<SyncDirectoryModel> result) {
				store.removeAll();
				store.add(result);
			}
		});
	}

	public static class SyncGridButtonEvent {

		private String path;
		private int rowIndex;

		public SyncGridButtonEvent(String path, int rowIndex) {
			this.path = path;
			this.rowIndex = rowIndex;
		}

		public String getPath() {
			return path;
		}

		public int getRowIndex() {
			return rowIndex;
		}
	}

	class ButtonRenderer implements GridCellRenderer<SyncDirectoryModel> {
		private boolean init;
		private Button button;

		public Object render(final SyncDirectoryModel model, String property, ColumnData config, final int rowIndex,
				final int colIndex, ListStore<SyncDirectoryModel> store, Grid<SyncDirectoryModel> grid) {
			if (!init) {
				init = true;
				grid.addListener(Events.ColumnResize, new Listener<GridEvent<SyncDirectoryModel>>() {
					public void handleEvent(GridEvent<SyncDirectoryModel> be) {
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
			SyncGridButtonEvent eventParam = new SyncGridButtonEvent(path, rowIndex);
			SelectionListener<ButtonEvent> listener = new GridButtonListener(eventParam);
			button = new Button(col.getHeader(), listener);
			button.setWidth(grid.getColumnModel().getColumnWidth(colIndex) - 10);
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
			private SyncGridButtonEvent syncEventObject;

			public GridButtonListener(SyncGridButtonEvent eventParam) {
				this.syncEventObject = eventParam;
			}

			@Override
			public void componentSelected(ButtonEvent ce) {
				disableSyncButton();
				syncService.syncDirectory(syncEventObject.getPath(), new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						enableSyncButton();
						Window.alert(caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						enableSyncButton();
					}
				});
			}
		}
	};

}