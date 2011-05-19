package com.setvect.bokslmusic.ui.client.grid;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.client.service.AlbumService;
import com.setvect.bokslmusic.ui.client.service.AlbumServiceAsync;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.AlbumArticleModel;
import com.setvect.bokslmusic.ui.shared.model.FolderModel;

public class AlbumTreeGrid extends LayoutContainer {

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		final AlbumServiceAsync service = GWT.create(AlbumService.class);

		// data proxy
		RpcProxy<List<AlbumArticleModel>> proxy = new RpcProxy<List<AlbumArticleModel>>() {
			@Override
			protected void load(Object loadConfig, AsyncCallback<List<AlbumArticleModel>> callback) {
				service.getFolderChildren((AlbumArticleModel) loadConfig, callback);
			}
		};

		// tree loader
		final TreeLoader<AlbumArticleModel> loader = new BaseTreeLoader<AlbumArticleModel>(proxy) {
			@Override
			public boolean hasChildren(AlbumArticleModel parent) {
				return parent instanceof FolderModel;
			}
		};

		// trees store
		final TreeStore<AlbumArticleModel> store = new TreeStore<AlbumArticleModel>(loader);

		store.setStoreSorter(new StoreSorter<AlbumArticleModel>() {
			@Override
			public int compare(Store<AlbumArticleModel> store, AlbumArticleModel m1, AlbumArticleModel m2,
					String property) {
				boolean m1Folder = m1 instanceof FolderModel;
				boolean m2Folder = m2 instanceof FolderModel;

				if (m1Folder && !m2Folder) {
					return -1;
				}
				else if (!m1Folder && m2Folder) {
					return 1;
				}

				return super.compare(store, m1, m2, property);
			}
		});

		ColumnConfig name = new ColumnConfig("name", "Name", 100);
		name.setRenderer(new TreeGridCellRenderer<ModelData>());

		ColumnConfig runningTime = new ColumnConfig("runningTime", "재생시간", 100);
		runningTime.setRenderer(ClientUtil.TIME_RENDERER);

		ColumnModel cm = new ColumnModel(Arrays.asList(name, runningTime));

		TreeGrid<ModelData> tree = new TreeGrid<ModelData>(store, cm);
		tree.setStateful(true);
		tree.setId("albumTree");
		store.setKeyProvider(new ModelKeyProvider<AlbumArticleModel>() {
			public String getKey(AlbumArticleModel model) {
				return model.<String> get("name");
			}
		});
		tree.setBorders(true);
		tree.getStyle().setLeafIcon(IconHelper.createStyle("icon-page"));
		tree.setSize(400, 400);
		tree.setAutoExpandColumn("name");
		tree.setTrackMouseOver(false);
		add(tree);
	}
}
