package com.yangjun.baby.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.yangjun.baby.util.ImageDownloader.FlushedInputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.util.Log;
import android.view.Display;

public class ImageUtils {
		public static ImageDownListener listener;
		private static final String LOG_TAG = "TextViewImage";
	 	private static final int HARD_CACHE_CAPACITY = 10;
	    private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds
	    public final static String ALBUM_PATH  
        = Environment.getExternalStorageDirectory() + "/download_test/";  
	    // Hard cache, with a fixed maximum capacity and a life duration
	    private static final HashMap<String, Bitmap> sHardBitmapCache =
	        new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
	        /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			@Override
	        protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
	            if (size() > HARD_CACHE_CAPACITY) {
	                // Entries push-out of hard reference cache are transferred to soft reference cache
	                sSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
	                return true;
	            } else
	                return false;
	        }
	    };

	    // Soft cache for bitmaps kicked out of hard cache
	    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache =
	        new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);
	    /**
	     * Adds this bitmap to the cache.
	     * @param bitmap The newly downloaded bitmap.
	     */
	    public static void addBitmapToCache(String url, Bitmap bitmap) {
	        if (bitmap != null) {
	            synchronized (sHardBitmapCache) {
	                sHardBitmapCache.put(url, bitmap);
	            }
	        }
	    }
	    /**
	     * @param url The URL of the image that will be retrieved from the cache.
	     * @return The cached bitmap or null if it was not found.
	     */
	    public static Bitmap getBitmapFromCache(String url) {
	        // First try the hard reference cache
	        synchronized (sHardBitmapCache) {
	            final Bitmap bitmap = sHardBitmapCache.get(url);
	            if (bitmap != null) {
	                // Bitmap found in hard cache
	                // Move element to first position, so that it is removed last
	                sHardBitmapCache.remove(url);
	                sHardBitmapCache.put(url, bitmap);
	                return bitmap;
	            }
	        }

	        // Then try the soft reference cache
	        SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
	        if (bitmapReference != null) {
	            final Bitmap bitmap = bitmapReference.get();
	            if (bitmap != null) {
	                // Bitmap found in soft cache
	                return bitmap;
	            } else {
	                // Soft reference has been Garbage Collected
	                sSoftBitmapCache.remove(url);
	            }
	        }

	        return null;
	    }
	 
	    /**
	     * Clears the image cache used internally to improve performance. Note that for memory
	     * efficiency reasons, the cache will automatically be cleared after a certain inactivity delay.
	     */
	    public static void clearCache() {
	        sHardBitmapCache.clear();
	        sSoftBitmapCache.clear();
	    }
	    public static void saveFile(Bitmap bm, String fileName) throws IOException {  
	        File dirFile = new File(ALBUM_PATH);  
	        if(!dirFile.exists()){  
	            dirFile.mkdir();  
	        }  
	        File myCaptureFile = new File(ALBUM_PATH + fileName);  
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));  
	        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
	        bos.flush();  
	        bos.close();
	    }  
	    public static Bitmap downloadBitmap(String url) {
	        // AndroidHttpClient is not allowed to be used from the main thread
	    	System.out.println(url);
	        final HttpClient client =new DefaultHttpClient();
	        final HttpGet getRequest = new HttpGet(url);
	        Bitmap bit=null;
	        try {
	            HttpResponse response = client.execute(getRequest);
	            final int statusCode = response.getStatusLine().getStatusCode();
	            if (statusCode != HttpStatus.SC_OK) {
	                Log.w("ImageDownloader", "Error " + statusCode +
	                        " while retrieving bitmap from " + url);
	                return null;
	            }

	            final HttpEntity entity = response.getEntity();
	            if (entity != null) {
	                InputStream inputStream = null;
	                try {
	                    inputStream = entity.getContent();
	                    // return BitmapFactory.decodeStream(inputStream);
	                    // Bug on slow connections, fixed in future release.
	                   bit=BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
	                } finally {
	                    if (inputStream != null) {
	                        inputStream.close();
	                    }
	                    entity.consumeContent();
	                }
	            }
	            Log.i("DEBUG", "SaveStart");
	            saveFile(bit,String.valueOf(url.hashCode()));
	            Log.i("DEBUG", "SaveEND");
	        } catch (IOException e) {
	            getRequest.abort();
	            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
	        } catch (IllegalStateException e) {
	            getRequest.abort();
	            Log.w(LOG_TAG, "Incorrect URL: " + url);
	        } catch (Exception e) {
	            getRequest.abort();
	            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
	        } finally {
	            if ((client instanceof AndroidHttpClient)) {
	                ((AndroidHttpClient) client).close();
	            }
	        }
	        listener.imageDownComplement(url);
	        return bit;
	    }
	    public static Rect getDefaultImageBounds(Context context) {
	        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
	        int width = display.getWidth();
	        int height = (int) (width * 9 / 16);
	                    
	        Rect bounds = new Rect(0, 0, width, height);
	        return bounds;
	    }
}
