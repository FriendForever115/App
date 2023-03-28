package com.expensify.chat;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import com.expensify.chat.bootsplash.BootSplash;
import com.expensify.reactnativekeycommand.KeyCommandModule;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "NewExpensify";
  }

  /**
    * Returns the instance of the {@link ReactActivityDelegate}. There the RootView is created and
    * you can specify the renderer you wish to use - the new renderer (Fabric) or the old renderer
    * (Paper).
    */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new MainActivityDelegate(this, getMainComponentName());
  }

  public static class MainActivityDelegate extends ReactActivityDelegate {
    public MainActivityDelegate(ReactActivity activity, String mainComponentName) {
      super(activity, mainComponentName);
    }

    @Override
    protected ReactRootView createRootView() {
      ReactRootView reactRootView = new ReactRootView(getContext());
      // If you opted-in for the New Architecture, we enable the Fabric Renderer.  New delegate and enabling Fabric in ReactRootView is only required for the new architecture builds.
      reactRootView.setIsFabric(BuildConfig.IS_NEW_ARCHITECTURE_ENABLED);
      return reactRootView;
    }

    @Override
    protected boolean isConcurrentRootEnabled() {
      // If you opted-in for the New Architecture, we enable Concurrent Root (i.e. React 18).
      // More on this on https://reactjs.org/blog/2022/03/29/react-v18.html
      return BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(null);
    if (getResources().getBoolean(R.bool.portrait_only)) {
      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    BootSplash.init(R.drawable.bootsplash, MainActivity.this); // <- display the generated bootsplash.xml drawable over our MainActivity
  }

  /**
   * This method is called when a key down event has occurred.
   * Forwards the event to the KeyCommandModule
   */
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    // Disabling hardware ESCAPE support which is handled by Android
    if (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) { 
        return false; 
    }
    KeyCommandModule.getInstance().onKeyDownEvent(keyCode, event);
    return super.onKeyDown(keyCode, event);
  }

  @Override
  public boolean onKeyLongPress(int keyCode, KeyEvent event) {    
    // disabling hardware ESCAPE support which is handled by Android
    if (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) { return false; }
    KeyCommandModule.getInstance().onKeyDownEvent(keyCode, event);
    return super.onKeyLongPress(keyCode, event);
  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {    
    // disabling hardware ESCAPE support which is handled by Android
    if (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) { return false; }
    KeyCommandModule.getInstance().onKeyDownEvent(keyCode, event);
    return super.onKeyUp(keyCode, event);
  }
}
