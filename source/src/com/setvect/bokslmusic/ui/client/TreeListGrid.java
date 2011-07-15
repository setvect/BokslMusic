package com.setvect.bokslmusic.ui.client;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreeGridEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerServiceAsync;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

public class TreeListGrid extends LayoutContainer {
	private final MusicManagerServiceAsync service = GWT.create(MusicManagerService.class);

	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
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

		CheckBoxSelectionModel<MusicArticleModel> sm = new CheckBoxSelectionModel<MusicArticleModel>();
		ColumnModel cm = new ColumnModel(Arrays.asList(sm.getColumn(), name, date));

		TreeGrid<MusicArticleModel> tree = new TreeGrid<MusicArticleModel>(store, cm);

		Listener<GridEvent<MusicArticleModel>> listener = new Listener<GridEvent<MusicArticleModel>>() {
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
					System.out.println(m.getName() + ": " + m.getPath());
				}
			}
		};

		tree.addListener(Events.OnKeyPress, listener);
		tree.addListener(Events.OnMouseDown, listener);

		tree.setSelectionModel(sm);
		tree.addPlugin(sm);
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
		tree.setTrackMouseOver(false);
		tree.getStyle().setLeafIcon(Resources.ICONS.music());

		// change in node check state
		tree.addListener(Events.Focus, new Listener<TreeGridEvent<ModelData>>() {
			public void handleEvent(TreeGridEvent<ModelData> be) {
				com.google.gwt.user.client.Window.alert(be.toString());
			}
		});

		add(tree);

		// 데이터의 표시 영역이 레이아웃을 벗어 날때 스크롤 생김
		setLayout(new FitLayout());
	}
}
