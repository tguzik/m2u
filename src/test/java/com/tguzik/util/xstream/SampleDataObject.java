package com.tguzik.util.xstream;

import com.tguzik.objects.BaseObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias( "dataobj" )
class SampleDataObject extends BaseObject {
    private String name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber( int number ) {
        this.number = number;
    }
}
