package org.cyborgsociety.panicbutton.app.utils;

/**
 * Created by richy on 12.10.15.
 */



public interface IFragmentInteractionCallbackListener {
    public enum ClickType{
        TYPE_DATA(1),
        TYPE_CACHE(2);
        private int value;
        private ClickType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
    }

    public void onFragmentItemClicked(String appId, ClickType type, boolean checked);
}
