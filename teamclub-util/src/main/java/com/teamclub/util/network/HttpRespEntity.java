package com.teamclub.util.network;

import com.teamclub.util.libs.F;

/**
 * Created by zhangmeng on 17-2-10.
 */
public class HttpRespEntity {
    private byte[] content;

    public HttpRespEntity(byte[] content) {
        this.content = content;
    }


    public F.Option<String> toStr(){
        try {
            return F.Option.Some(new String(content, "UTF-8"));
        } catch(Exception e) {
            return F.Option.None();
        }

    }

    public byte[] getRaw(){
        return content;
    }
}
