package com.pairliu.learning.vertx;

import java.io.File;
import java.io.FileOutputStream;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.file.AsyncFile;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.http.HttpServerResponse;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.core.streams.WriteStream;
import org.vertx.java.deploy.Verticle;

public class PumpFileDecorator implements WriteStream {
    FileOutputStream stream; 
    private final HttpClientResponse _cResponse;
    private final HttpServerResponse _response;
    Verticle _verticle;
    AsyncFile _file;
    WriteStream _fileWriteStream;
    int position = 0;
    boolean finished = false;
    
    public PumpFileDecorator(final HttpClientResponse cResponse, final HttpServerResponse response, final Verticle verticle) {
        _verticle = verticle;
        _cResponse = cResponse;
        _response = response;
    }

    public void writeBuffer(final Buffer data) {
        try {
            stream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int curr = position;
        position += data.length(); //At the last write, the position should be the size of content

        _response.writeBuffer(data);
        
//        _file.write(data, curr, new AsyncResultHandler<Void>() {
//            public void handle(AsyncResult<Void> event) {
//                
//            }
//            
//        });
        _fileWriteStream.writeBuffer(data);
        
    }

    public void setWriteQueueMaxSize(int maxSize) {
        _response.setWriteQueueMaxSize(maxSize);
        _fileWriteStream.setWriteQueueMaxSize(maxSize);
    }

    public boolean writeQueueFull() {
        return _response.writeQueueFull() || _fileWriteStream.writeQueueFull();
    }

    public void drainHandler(Handler<Void> handler) {
        _response.drainHandler(handler);
        _fileWriteStream.drainHandler(handler);
    }

    public void exceptionHandler(Handler<Exception> handler) {
        _response.exceptionHandler(handler);
        _fileWriteStream.exceptionHandler(handler);
    }
    
    public void closeStream() {
        try {
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void start() {
        _cResponse.pause();
        try {
            stream = new FileOutputStream(new File("d:/subfolder/google.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        _verticle.getVertx().fileSystem().open("d:/subfolder/google_async.html", new AsyncResultHandler<AsyncFile>() {
            public void handle(AsyncResult<AsyncFile> event) {
                _file = event.result;
                _fileWriteStream = _file.getWriteStream();
                _cResponse.endHandler(new Handler<Void>() {
                    public void handle(Void event) {
                        System.out.println("clientResponse's size is: " + position);
//                        finished = true;
                        end();
                    }
                });

                Pump.createPump(_cResponse, PumpFileDecorator.this).start();
                _cResponse.resume();
                
            }
        });
    }
    
    public void end() {
        //When the response is ended? Does this mean this call is the reason why some content hasn't been written?
        //No, not the reason. The reason is _cResponse will end early, and at that time, there is 
        //some content needed to be written out. 
        //So, cannot rely on _cResponse to end.
        closeStream();
        _response.end();
        _file.close();
    }

}
