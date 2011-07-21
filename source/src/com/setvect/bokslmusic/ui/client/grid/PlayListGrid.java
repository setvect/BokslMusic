package com.setvect.bokslmusic.ui.client.grid;

import java.util.Arrays;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.setvect.bokslmusic.ui.client.Resources;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerServiceAsync;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;

public class PlayListGrid extends ContentPanel {
	private ColumnModel cm;
	private final MusicManagerServiceAsync service = GWT.create(MusicManagerService.class);
	/** 그리드 데이터 */
	private ListStore<MusicArticleModel> store = new ListStore<MusicArticleModel>();

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		getAriaSupport().setPresentation(true);
		RowNumberer numberer = new RowNumberer();

		ColumnConfig colTitle = new ColumnConfig("name", "Name", 200);
		colTitle.setRowHeader(true);
		colTitle.setSortable(false);

		ColumnConfig colDate = new ColumnConfig("runningTime", "Time", 100);
		// 초 단위 숫자를 => "2:23" 이런 형식으로 변경
		colDate.setRenderer(ClientUtil.TIME_RENDERER);

		cm = new ColumnModel(Arrays.asList(numberer, colTitle, colDate));

		final Grid<MusicArticleModel> grid = new Grid<MusicArticleModel>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("basePath");
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.addPlugin(numberer);

		ContentPanel content = new ContentPanel();
		content.add(grid);

		ToolBar toolBar = new ToolBar();
		Button addButton = new Button("Add", Resources.ICONS.add());
		addButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent be) {
				System.out.println("SSSSSSSSSSS");
			}
		});

		toolBar.add(addButton);

		// toolBar.add(addButton);
		toolBar.add(new SeparatorToolItem());

		content.setHeaderVisible(false);
		content.setTopComponent(toolBar);

		// 데이터의 표시 영역이 레이아웃을 벗어 날때 스크롤 생김
		setLayout(new FitLayout());
		setHeading("재생 목록");
		add(content);
	}

	/**
	 * @param item
	 *            항목 추가
	 */
	public void addItem(MusicArticleModel item) {
		store.add(item);
	}
}