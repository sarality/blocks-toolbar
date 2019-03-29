package com.sarality.toolbar;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
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
  private Menu menu;

  public MenuEventProcessor(Activity activity, int menuResourceId) {
    this.activity = activity;
    this.menuResourceId = menuResourceId;
  }

  public void init(Menu menu) {
    init(menu, activity.getMenuInflater());
  }

  public void init(Menu menu, MenuInflater inflater) {
    inflater.inflate(menuResourceId, menu);
    this.menu = menu;
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

  public void startAnimation(int itemId) {
    if (itemActionMap.containsKey(itemId)
        && menu != null) {
      MenuItem item = menu.findItem(itemId);
      if (item == null) {
        return;
      }

      AnimationDrawable animationDrawable = (AnimationDrawable) item.getIcon();
      if (animationDrawable != null) {
        animationDrawable.start();
      }
    }
  }

  public void clearAnimation(int itemId) {
    if (itemActionMap.containsKey(itemId)
        && menu != null) {
      MenuItem item = menu.findItem(itemId);
      if (item == null) {
        return;
      }

      AnimationDrawable animationDrawable = (AnimationDrawable) item.getIcon();
      if (animationDrawable != null) {
        animationDrawable.stop();
      }


    }
  }

}
