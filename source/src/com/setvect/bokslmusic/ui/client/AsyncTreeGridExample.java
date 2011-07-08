package com.setvect.bokslmusic.ui.client;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerServiceAsync;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;

@SuppressWarnings("deprecation")
public class AsyncTreeGridExample extends LayoutContainer {

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		setLayout(new FlowLayout(10));

		final MusicManagerServiceAsync service = GWT.create(MusicManagerService.class);

		// data proxy
		RpcProxy<List<MusicArticleModel>> proxy = new RpcProxy<List<MusicArticleModel>>() {
			@Override
			protected void load(Object loadConfig, AsyncCallback<List<MusicArticleModel>> callback) {
				service.listFolder(model, children)
				service.getFolderChildren((MusicArticleModel) loadConfig, callback);
			}
		};

		// tree loader
		final TreeLoader<MusicArticleModel> loader = new BaseTreeLoader<MusicArticleModel>(proxy) {
			@Override
			public boolean hasChildren(MusicArticleModel parent) {
				return parent instanceof FolderModel;
			}
		};

		// trees store
		final TreeStore<MusicArticleModel> store = new TreeStore<MusicArticleModel>(loader);
		store.setStoreSorter(new StoreSorter<MusicArticleModel>() {

			@Override
			public int compare(Store<MusicArticleModel> store, MusicArticleModel m1, MusicArticleModel m2,
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

		ColumnConfig date = new ColumnConfig("date", "Date", 100);
		date.setDateTimeFormat(DateTimeFormat.getMediumDateTimeFormat());

		ColumnConfig size = new ColumnConfig("size", "Size", 100);

		ColumnModel cm = new ColumnModel(Arrays.asList(name, date, size));

		ContentPanel cp = new ContentPanel();
		cp.setBodyBorder(false);
		cp.setHeading("Async TreeGrid");
		cp.setButtonAlign(HorizontalAlignment.CENTER);
		cp.setLayout(new FitLayout());
		cp.setFrame(true);
		cp.setSize(600, 300);

		TreeGrid<ModelData> tree = new TreeGrid<ModelData>(store, cm);
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
		cp.add(tree);

		ToolTipConfig config = new ToolTipConfig();
		config.setTitle("Example Information");
		config.setShowDelay(1);
		config.setText("In this example state has been enabled for the treegrid. When enabled, the expand state of the treegrid is "
				+ "saved and restored using the StateManager. Try refreshing the browser after expanding some nodes in the "
				+ "treegrid. Notice that this works with asynchronous loading of nodes.");

		ToolButton btn = new ToolButton("x-tool-help");
		btn.setToolTip(config);

		cp.getHeader().addTool(btn);

		add(cp);
	}
}
