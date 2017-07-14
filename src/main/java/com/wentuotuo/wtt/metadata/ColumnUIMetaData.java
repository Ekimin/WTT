//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuotuo.wtt.metadata;

public interface ColumnUIMetaData extends MetaDataObject {
    int ALIGNMENT_CENTER = 0;
    int ALIGNMENT_LEFT = 1;
    int ALIGNMENT_RIGHT = 2;
    int ALIGNMENT_TOP = 3;
    int ALIGNMENT_BOTTOM = 4;
    int VALUE_CHARACTER_UNLIMITED = 0;
    int VALUE_CHARACTER_LIST = 1;
    int VALUE_CHARACTER_CODETABLE = 2;
    int VALUE_CHARACTER_RANGE = 3;

    int getHAlignment();

    int getVAlignment();

    int getValueCharacter();

    String getValueCodetable();

    String getValueList();

    String getValueRange();

    String getInputMask();

    boolean matchMask(String var1);

    String getDisplayFormat();

    String getCSS();

    boolean isKey();

    boolean isReadOnly();

    boolean isVisible();

    boolean isRequired();

    boolean isSortable();
}
