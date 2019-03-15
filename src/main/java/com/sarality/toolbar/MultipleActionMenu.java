package com.sarality.toolbar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sarality.action.ClickActions;
import com.sarality.action.LongClickActions;
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
  private final Map<Integer, Integer> itemIconViewMap = new HashMap<>();
  private final SparseArray<ViewAction> clickActionMap = new SparseArray<>();
  private final SparseArray<ViewAction> longClickActionMap = new SparseArray<>();

  public MultipleActionMenu(int menuResourceId) {
    this.menuResourceId = menuResourceId;
  }

  public MultipleActionMenu withItem(int itemId, int itemIconId) {
    itemIconViewMap.put(itemId, itemIconId);
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
  }

  public void initItems(Activity activity, Menu menu) {
    for (Integer itemId : itemIconViewMap.keySet()) {
      MenuItem menuItem = menu.findItem(itemId);
      if (menuItem != null) {
        Integer iconViewId = itemIconViewMap.get(itemId);
        initItem(activity, menuItem, itemId, iconViewId);
      }
    }
  }

  private void initItem(Activity activity, MenuItem menuItem, Integer itemId, Integer iconViewId) {
    View view = menuItem.getActionView();
    Drawable iconDrawable = menuItem.getIcon();

    if (iconDrawable != null && iconViewId != null) {
      ImageView iconView = view.findViewById(iconViewId);
      if (iconView != null) {
        iconView.setImageDrawable(iconDrawable);
      }
    }
    ViewAction clickAction = getClickAction(itemId);
    if (clickAction != null) {
      new ClickActions(activity).register(clickAction).init(view);
    }

    ViewAction longClickAction = getLongClickAction(itemId);
    if (longClickAction != null) {
      new LongClickActions(activity).register(longClickAction).init(view);
    }
  }

  ViewAction getClickAction(int itemId) {
    return clickActionMap.get(itemId);
  }

  ViewAction getLongClickAction(int itemId) {
    return longClickActionMap.get(itemId);
  }
}
