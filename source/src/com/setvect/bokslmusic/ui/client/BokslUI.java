package com.setvect.bokslmusic.ui.client;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.RootPanel;

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

			this.setSize(200, 325);
		}

		private ContentPanel makeAllList() {
			ContentPanel allList = new ContentPanel();
			allList.setAnimCollapse(false);
			allList.setHeading("전체목록");
			allList.setLayout(new FitLayout());

			TreeStore<ModelData> store = new TreeStore<ModelData>();
			TreePanel<ModelData> tree = new TreePanel<ModelData>(store);
			tree.setIconProvider(new ModelIconProvider<ModelData>() {
				public AbstractImagePrototype getIcon(ModelData model) {
					if (model.get("icon") != null) {
						return IconHelper.createStyle((String) model.get("icon"));
					}
					else {
						return null;
					}
				}

			});
			tree.setDisplayProperty("name");

			ModelData m = newItem("Family", null);
			store.add(m, false);

			store.add(m, newItem("Darrell", "user"), false);
			store.add(m, newItem("Maro", "user-girl"), false);
			store.add(m, newItem("Lia", "user-kid"), false);
			store.add(m, newItem("Alec", "user-kid"), false);
			store.add(m, newItem("Andrew", "user-kid"), false);
			tree.setExpanded(m, true);

			m = newItem("Friends", null);
			store.add(m, false);

			store.add(m, newItem("Bob", "user"), false);
			store.add(m, newItem("Mary", "user-girl"), false);
			store.add(m, newItem("Sally", "user-girl"), false);
			store.add(m, newItem("Jack", "user"), false);

			tree.setExpanded(m, true);
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