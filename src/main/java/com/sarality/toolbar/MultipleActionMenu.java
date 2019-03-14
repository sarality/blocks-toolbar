package com.sarality.toolbar;

import android.app.Activity;
import android.support.v4.view.MenuItemCompat;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sarality.action.ViewAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Initializes a Menu for the Activity which supports both Click and Long Click Actions
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class MultipleActionMenu {

  private final int menuResourceId;
  @SuppressWarnings("UseSparseArrays")
  private final Map<Integer, Integer> itemLayoutMap = new HashMap<>();
  private final SparseArray<ViewAction> clickActionMap = new SparseArray<>();
  private final SparseArray<ViewAction> longClickActionMap = new SparseArray<>();

  public MultipleActionMenu(int menuResourceId) {
    this.menuResourceId = menuResourceId;
  }

  public MultipleActionMenu withItem(int itemId, int itemLayoutId) {
    itemLayoutMap.put(itemId, itemLayoutId);
    return this;
  }

  public MultipleActionMenu registerClick(int itemId, ViewAction action) {
    clickActionMap.put(itemId, action);
    return this;
  }

  public MultipleActionMenu registerLongClick(int itemId, ViewAction action) {
    longClickActionMap.put(itemId, action);
    return this;
  }

  public void init(Activity activity, Menu menu) {
    MenuInflater layoutInflater = activity.getMenuInflater();
    layoutInflater.inflate(menuResourceId, menu);
    for (Integer itemId : itemLayoutMap.keySet()) {
      int itemLayoutId = itemLayoutMap.get(itemId);
      MenuItem menuItem = menu.findItem(itemId);
      MultipleActionProvider actionProvider = (MultipleActionProvider) MenuItemCompat.getActionProvider(menuItem);
      actionProvider.init(activity, this, menuItem, itemId, itemLayoutId);
    }
  }

  ViewAction getClickAction(int itemId) {
    return clickActionMap.get(itemId);
  }

  ViewAction getLongClickAction(int itemId) {
    return longClickActionMap.get(itemId);
  }
}
