/* 
 * The MIT License
 *
 * Copyright 2015 Ahseya.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.horrorho.inflatabledonkey.requests;

import com.github.horrorho.inflatabledonkey.protobuf.ChunkServer;
import java.util.function.Function;
import net.jcip.annotations.Immutable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;

/**
 * ChunkServer.StorageHostChunkList storageHostChunkList HttpUriRequest factory.
 *
 * @author Ahseya
 */
@Immutable
public final class ChunkListRequestFactory implements Function<ChunkServer.HostInfo, HttpUriRequest> {

    public static ChunkListRequestFactory instance() {
        return INSTANCE;
    }

    private static final ChunkListRequestFactory INSTANCE = new ChunkListRequestFactory();

    private ChunkListRequestFactory() {
    }

    @Override
    public HttpUriRequest apply(ChunkServer.HostInfo hostInfo) {
        String uri = hostInfo.getScheme() + "://" + hostInfo.getHostname() + "/" + hostInfo.getUri();
        HttpUriRequest request = RequestBuilder.create(hostInfo.getMethod())
                .setUri(uri)
                .build();
        hostInfo.getHeadersList()
                .stream()
                .map(u -> new BasicHeader(u.getName(), u.getValue()))
                .forEach(request::addHeader);
        return request;
    }
}
