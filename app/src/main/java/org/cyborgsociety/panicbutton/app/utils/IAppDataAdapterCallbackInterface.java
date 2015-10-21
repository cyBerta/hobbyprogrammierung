package org.cyborgsociety.panicbutton.app.utils;

import org.cyborgsociety.panicbutton.app.utils.enums.ClickType;

/**
 * Created by richy on 16.10.15.
 */
public interface IAppDataAdapterCallbackInterface {

    void onCustomItemClicked(String appId, ClickType type, boolean checked);

}
