package com.sarality.toolbar;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.sarality.action.ActionContext;
import com.sarality.action.ViewAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Initializes a Menu for the Activity
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class MenuEventProcessor {

  private final Activity activity;
  private final int menuResourceId;
  private final Map<Integer, ViewAction> itemActionMap = new HashMap<>();

  public MenuEventProcessor(Activity activity, int menuResourceId) {
    this.activity = activity;
    this.menuResourceId = menuResourceId;
  }

  public void init(Menu menu) {
    MenuInflater inflater = activity.getMenuInflater();
    inflater.inflate(menuResourceId, menu);
  }

  public MenuEventProcessor withItem(int itemId, ViewAction action) {
    itemActionMap.put(itemId, action);
    return this;
  }

  public boolean processItem(MenuItem item) {
    Integer itemId = item.getItemId();
    if (itemActionMap.containsKey(itemId)) {
      ViewAction action = itemActionMap.get(itemId);
      View view = activity.getWindow().getDecorView();
      action.perform(new ActionContext(view));
      return true;
    }
    return false;
  }
}
