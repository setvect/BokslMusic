package com.extjs.gxt.ui.client.data;

import com.extjs.gxt.ui.client.data.BeanModelFactory;
import java.util.Map;
import com.extjs.gxt.ui.client.core.FastMap;

public class BeanModelLookupImpl extends com.extjs.gxt.ui.client.data.BeanModelLookup {
  private Map<String, BeanModelFactory> m;
  public BeanModelFactory getFactory(Class b) {
    String n = b.getName();
    if (m == null) {
      m = new FastMap<BeanModelFactory>();
    }
    if (m.get(n) == null) {
    }
    return m.get(n);
  }
  
}
