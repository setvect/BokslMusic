package com.setvect.bokslmusic.ui.client;

import java.util.Arrays;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGrid;
import com.extjs.gxt.ui.client.widget.treegrid.TreeGridCellRenderer;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.setvect.bokslmusic.ui.client.service.MusicManagerService;
import com.setvect.bokslmusic.ui.client.service.MusicManagerServiceAsync;
import com.setvect.bokslmusic.ui.client.util.ClientUtil;
import com.setvect.bokslmusic.ui.shared.model.MusicArticleModel;
import com.setvect.bokslmusic.ui.shared.model.MusicDirectoryModel;

/**
 * Entry point classes define <CODE>onModuleLoad()</CODE>.
 */
public class BokslUI implements EntryPoint {
	public void onModuleLoad() {
		MainPanel a = new MainPanel();
		RootPanel rootPanel = RootPanel.get();
		rootPanel.add(a);
	}

	class MainPanel extends LayoutContainer {
		@Override
		protected void onRender(Element parent, int index) {
			super.onRender(parent, index);
			setLayout(new FlowLayout(10));

			this.setLayout(new AccordionLayout());

			ContentPanel allList = makeAllList();

			this.add(allList);

			ContentPanel playList = new ContentPanel();
			playList.setAnimCollapse(false);
			playList.setBodyStyleName("pad-text");
			playList.setHeading("재생 목록");
			playList.addText("TEXT 1");
			this.add(playList);

			ContentPanel playControl = new ContentPanel();
			playControl.setAnimCollapse(false);
			playControl.setBodyStyleName("pad-text");
			playControl.setHeading("재생 창");
			playControl.addText("TEXT 2");
			this.add(playControl);

			this.setSize(350, 425);
		}

		private ContentPanel makeAllList() {

			final MusicManagerServiceAsync service = GWT.create(MusicManagerService.class);

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
			GridCellRenderer<ModelData> timeRenderer = new GridCellRenderer<ModelData>() {
				public String render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex,
						ListStore<ModelData> store, Grid<ModelData> grid) {
					Object value = model.get(property);
					if (value == null) {
						return "";
					}
					int sec = (Integer) value;
					return ClientUtil.getMinuteSec(sec);
				}
			};
			date.setRenderer(timeRenderer);

			ColumnModel cm = new ColumnModel(Arrays.asList(name, date));

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

			ContentPanel allList = new ContentPanel();
			allList.setLayout(new FitLayout());

			allList.add(tree);
			return allList;
		}

		private ModelData newItem(String text, String iconStyle) {
			ModelData m = new BaseModelData();
			m.set("name", text);
			m.set("icon", iconStyle);
			return m;
		}
	}
}