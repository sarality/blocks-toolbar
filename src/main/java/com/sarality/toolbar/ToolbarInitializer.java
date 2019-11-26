package com.sarality.toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Initializes a toolbar for the Activity
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ToolbarInitializer {

  private final AppCompatActivity activity;
  private final Toolbar toolbar;

  public ToolbarInitializer(AppCompatActivity activity, int toolbarViewId) {
    this.activity = activity;
    this.toolbar = (Toolbar) activity.findViewById(toolbarViewId);
  }

  public void init() {
    activity.setSupportActionBar(toolbar);
  }
}
