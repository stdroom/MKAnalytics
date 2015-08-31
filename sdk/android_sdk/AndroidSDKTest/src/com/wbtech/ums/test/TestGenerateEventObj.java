
package com.wbtech.ums.test;

import android.test.AndroidTestCase;

import com.wbtech.ums.dao.Poster;
import com.wbtech.ums.objects.PostObjEvent;
import com.wbtech.ums.objects.PostObjTag;

public class TestGenerateEventObj extends AndroidTestCase {
    public void testGenerateEventObj(){
       PostObjEvent event= Poster.GenerateEventObj(getContext(), null);
       event.setTime("123");
       PostObjEvent event2 = new PostObjEvent("", "", "", "123", "", "1.0", "b40d7fb796674f8a5b6df2e254447d73");
       boolean d=false;
       if(event.equals(event2)){
           d=true;
       }
       assertEquals(d, true);
    }
    
    public void testGenerateEventObj1(){
        PostObjEvent event= Poster.GenerateEventObj(null, null);
        event.setTime("123");
        PostObjEvent event2 = new PostObjEvent("", "", "", "123", "", "", "");
        boolean d=false;
        if(event.equals(event2)){
            d=true;
        }
        assertEquals(d, true);
     }
    
}
