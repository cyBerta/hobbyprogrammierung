package org.cyborgsociety.panicbutton.app.utils;

import org.cyborgsociety.panicbutton.app.utils.enums.ClickType;

/**
 * Created by richy on 12.10.15.
 */



public interface IAppItemPropertyInterface {

    void onAppItemPropertyChanged(String appId, ClickType type, boolean checked);
}
