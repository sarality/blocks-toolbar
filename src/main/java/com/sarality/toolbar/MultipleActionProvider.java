package com.sarality.toolbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.sarality.action.ClickActions;
import com.sarality.action.LongClickActions;
import com.sarality.action.ViewAction;

/**
 * Action Provider that registers the Click and Long Click Actions on a Menu Item.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class MultipleActionProvider extends ActionProvider {
  private final Context context;

  private Activity activity;
  private MultipleActionMenu menu;
  private MenuItem menuItem;
  private int itemId;
  private int itemLayoutId;
  private Integer iconViewId;

  public MultipleActionProvider(Context context) {
    super(context);
    this.context = context;
  }

  @Override
  public View onCreateActionView() {
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(itemLayoutId, null);
    initItem(view);
    return view;
  }

  public void init(Activity activity, MultipleActionMenu menu, MenuItem menuItem, int itemId, int itemLayoutId,
      Integer iconViewId) {
    this.activity = activity;
    this.menu = menu;
    this.menuItem = menuItem;
    this.itemId = itemId;
    this.itemLayoutId = itemLayoutId;
    this.iconViewId = iconViewId;
  }

  private void initItem(View view) {
    Drawable iconDrawable = menuItem.getIcon();
    if (iconDrawable != null && iconViewId != null) {
      ImageView iconView = view.findViewById(iconViewId);
      if (iconView != null) {
        iconView.setImageDrawable(iconDrawable);
      }
    }
    ViewAction clickAction = menu.getClickAction(itemId);
    if (clickAction != null) {
      new ClickActions(activity).register(clickAction).init(view);
    }

    ViewAction longClickAction = menu.getLongClickAction(itemId);
    if (longClickAction != null) {
      new LongClickActions(activity).register(longClickAction).init(view);
    }
  }
}
