package com.setvect.bokslmusic.ui.client.grid;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import net.zschech.gwt.comet.client.CometClient;
import net.zschech.gwt.comet.client.CometListener;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.client.Resources;
import com.setvect.bokslmusic.ui.client.event.AllListGridEventListener;
import com.setvect.bokslmusic.ui.client.service.ChatService;
import com.setvect.bokslmusic.ui.client.service.ChatServiceAsync;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerServiceAsync;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

/**
 * 전체 목록
 * 
 * @version $Id$
 */
public class AllListGrid extends ContentPanel {
	private final MusicManagerServiceAsync service = GWT.create(MusicManagerService.class);
	private TreeGrid<MusicArticleModel> tree;
	private Button addButton;
	private AllListGridEventListener eventListener;

	public AllListGrid() {
		// data proxy
		RpcProxy<List<MusicArticleModel>> proxy = new RpcProxy<List<MusicArticleModel>>() {
			@Override
			protected void load(Object loadConfig, AsyncCallback<List<MusicArticleModel>> callback) {
				MusicDirectoryModel dir = (MusicDirectoryModel) loadConfig;
				service.listMusic(dir, callback);
			}
		};

		// tree loader
		final TreeLoader<MusicArticleModel> loader = new BaseTreeLoader<MusicArticleModel>(proxy) {
			@Override
			public boolean hasChildren(MusicArticleModel parent) {
				return parent instanceof MusicDirectoryModel;
			}
		};

		// trees store
		final TreeStore<MusicArticleModel> store = new TreeStore<MusicArticleModel>(loader);
		store.setStoreSorter(new StoreSorter<MusicArticleModel>() {
			@Override
			public int compare(Store<MusicArticleModel> store, MusicArticleModel m1, MusicArticleModel m2,
					String property) {
				boolean m1Folder = m1 instanceof MusicDirectoryModel;
				boolean m2Folder = m2 instanceof MusicDirectoryModel;

				if (m1Folder && !m2Folder) {
					return -1;
				}
				else if (!m1Folder && m2Folder) {
					return 1;
				}

				return super.compare(store, m1, m2, property);
			}
		});

		ColumnConfig name = new ColumnConfig("name", "Name", 200);
		name.setRenderer(new TreeGridCellRenderer<ModelData>());

		ColumnConfig date = new ColumnConfig("runningTime", "Time", 100);
		// 초 단위 숫자를 => "2:23" 이런 형식으로 변경
		date.setRenderer(ClientUtil.TIME_RENDERER);
		ColumnModel cm = new ColumnModel(Arrays.asList(name, date));

		tree = new TreeGrid<MusicArticleModel>(store, cm) {
			protected void onClick(GridEvent<MusicArticleModel> e) {
				super.onClick(e);
				// 한번 클릭에서도 폴더 확장/축소가 되게
				toggle(e.getModel());
			}
		};

		tree.setStateful(true);
		// stateful components need a defined id
		tree.setId("statefullasynctreegrid");
		store.setKeyProvider(new ModelKeyProvider<MusicArticleModel>() {
			public String getKey(MusicArticleModel model) {
				return model.<String> get("id");
			}
		});
		tree.setBorders(true);
		tree.getStyle().setLeafIcon(IconHelper.createStyle("icon-page"));
		tree.setSize(400, 400);
		tree.setAutoExpandColumn("name");
		tree.getStyle().setLeafIcon(Resources.ICONS.music());

		ContentPanel content = new ContentPanel();
		content.add(tree);

		ToolBar toolBar = new ToolBar();
		addButton = new Button("Add", Resources.ICONS.add());
		addButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {
			public void handleEvent(ButtonEvent be) {
				ChatServiceAsync chatService = GWT.create(ChatService.class);
				chatService.setStatus("하이롱", new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						System.out.println("실패");
					}

					public void onSuccess(Void result) {
						System.out.println("성공");
					}
				});

				// CometSerializer serializer =
				// GWT.create(InstantMessagingCometSerializer.class);
				// CometClient client1 = new CometClient(url, serializer,
				// listener);

				// GridSelectionModel<MusicArticleModel> selectionModel =
				// tree.getSelectionModel();
				// List<MusicArticleModel> items =
				// selectionModel.getSelectedItems();
				// eventListener.addMusicEvent(items);

			}
		});

		toolBar.add(addButton);
		toolBar.add(new SeparatorToolItem());

		content.setHeaderVisible(false);
		content.setTopComponent(toolBar);

		// 데이터의 표시 영역이 레이아웃을 벗어 날때 스크롤 생김
		content.setLayout(new FitLayout());
		setHeading("등록 목록");

		add(content);
	}

	public void addEvent(AllListGridEventListener allListEvent) {
		this.eventListener = allListEvent;

		tree.addListener(Events.OnKeyPress, eventListener);
		tree.addListener(Events.OnMouseDown, eventListener);
	}
}
