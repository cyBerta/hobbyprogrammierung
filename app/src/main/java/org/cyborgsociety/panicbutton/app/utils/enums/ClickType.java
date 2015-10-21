package org.cyborgsociety.panicbutton.app.utils.enums;

/**
 * Created by richy on 16.10.15.
 */

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


