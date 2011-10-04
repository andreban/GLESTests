package com.mobplug.android.glestests.glutils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TGATextureLoader {
	   
    private static int DEFAULTALPHA = 0x00000000;
    private LittleEndianDataInputStream dfin;
    // TGA-specific variables
    private int zl,
            orientation = 0,
            imgType = 0,
            IDlength = 0,
            cMapFirst = 0,
            xOrigin,
            yOrigin,
            description,
            depth = 0,
            pixels = 0,
            cMapType = 0,
            cMapLength = 0,
            cMapEntrySize = 0,
            framePtr = 0,
            cachePtr = 0,
            red = 0,
            green = 0,
            blue = 0,
            col = 0,
            cacheStep = 0,
            stepFrameCol = 0,
            stepFrameRow = 0,
            pixelCt = 0,
            pixel = 0,
            alpha = DEFAULTALPHA,
            fileLength = 0,
            RLEtype,
            RLElength,
            RLEcounter,
            RLEpause = 0,
            xRes,
            yRes,
            value;
    private boolean loaded = false,
            RLEencoded = false;
    private int[] frameBuffer;
    private int[] cache = null;
    private int[] palette = null;
    
    public TGATextureLoader(InputStream in) {
        dfin = new LittleEndianDataInputStream(new BufferedInputStream(in));
    }

    // load the TGA header in order to prepare the framebuffer
    private void readHeader() throws IOException {
        IDlength = dfin.readUnsignedByte(); // read one single byte
        cMapType = dfin.readUnsignedByte();
        imgType = dfin.readUnsignedByte();
        cMapFirst = dfin.readUnsignedShort(); // read 16 bits
        cMapLength = dfin.readUnsignedShort();
        cMapEntrySize = dfin.readUnsignedByte();
        xOrigin = dfin.readUnsignedShort();
        yOrigin = dfin.readUnsignedShort();
        xRes = dfin.readUnsignedShort();  // read width of image in pixels
        yRes = dfin.readUnsignedShort();  // read height of image in pixels
        depth = dfin.readUnsignedByte();  // read image depth in bits per pixel
        description = dfin.readUnsignedByte(); // read description bits

        frameBuffer = new int[xRes * yRes];
    }

    // load the TGA file
    private void loadPicture() throws IOException {
        // calculate orientation (top-down, left-right, etc.)
        orientation = (description & 0xF0) >> 4;

        // prepare variables for the RLE decoding process
        RLEpause = 0;
        RLEcounter = 0;
        RLElength = 1;
        
        if (imgType == 9 | imgType == 10 | imgType == 11) {
            RLEencoded = true; // it's a compressed image
        }
        
        dfin.skip(IDlength); // skip ID field

        if (cMapType == 1) { // there's a color map
            if (cMapEntrySize == 16) {
                throw new IOException("16 bit color maps not supported");
            }
            
            palette = new int[cMapLength * (cMapEntrySize / 8)];
            value = 0;
            
            for (zl = 0; zl < cMapLength; zl++) {
                if (cMapEntrySize == 24) {
                    palette[value++] = dfin.readUnsignedByte();
                    palette[value++] = dfin.readUnsignedByte();
                    palette[value++] = dfin.readUnsignedByte();
                }
            }
        }

        // reading image data
        pixels = xRes * yRes; // total number of pixels

        if (!RLEencoded) { // picture is not RLE-compressed?
            // read raw image data into cache array
            cache = new int[pixels * (depth / 8)];
            for (int i = 0; i < cache.length; i++) {
                cache[i] = dfin.readUnsignedByte();
            }
        }

        // convert the image data from the cache array into the framebuffer
        stepFrameCol = 1; // default - left to right, bottom to top
        stepFrameRow = -(2 * xRes);
        framePtr = (yRes - 1) * xRes;
        pixelCt = 0; // counter for pixels

        if (orientation == 0x10) { // right to left, bottom to top
            stepFrameCol = -1;
            stepFrameRow = 0;
            framePtr = pixels - 1;
        } else if (orientation == 0x20) { // left to right, top to bottom
            stepFrameCol = 1;
            stepFrameRow = 0;
            framePtr = 0;
        } else if (orientation == 0x30) { // right to left, top to bottom
            stepFrameCol = -1;
            stepFrameRow = 2 * xRes;
            framePtr = xRes - 1;
        }
        
        if (imgType == 1 | imgType == 9) {
            decodeCMAP(); // decode a colormapped image
        } else if (imgType == 2 | imgType == 10) {
            decodeTC(); // decode a true color image
        } else if (imgType == 3 | imgType == 11) {
            decodeGray(); // decode a grayscale image
        } else {
            throw new IOException("TGA image type " + imgType + " not supported");
        }
        
        dfin.close();
        cache = null;
    }

    // decode a colormapped image
    private void decodeCMAP() throws IOException {
        if (depth == 16) {
            //nothing yet...
        } else if (depth == 8) {
            while (pixelCt < pixels) {
                if (RLEencoded) {
                    if (RLEpause == 0) {
                        decodeRLE();
                    }
                    
                    pixel = dfin.readUnsignedByte() * 3;
                } else {
                    pixel = cache[cachePtr++] * 3;
                }
                
                red = palette[pixel + 2];
                green = palette[pixel + 1];
                blue = palette[pixel];
                
                value = getRGBAValue(red, green, blue);
                
                for (RLEcounter = 0; RLEcounter < RLElength; RLEcounter++) {
                    frameBuffer[framePtr] = value;
                    framePtr += stepFrameCol;
                    col++;
                    
                    if (col == xRes) {
                        framePtr += stepFrameRow;
                        col = 0;
                    }
                    
                    pixelCt++;
                }
                
                if (RLEpause > 0) {
                    RLEpause--;
                }
            }
        }
    }

    // decode a gray scale image
    private void decodeGray() throws IOException {
        while (pixelCt < pixels) {
            if (RLEencoded) {
                if (RLEpause == 0) {
                    decodeRLE();
                }
                
                pixel = dfin.readUnsignedByte();
            } else {
                pixel = cache[cachePtr++];
            }
            
            value = getRGBAValue(pixel, pixel, pixel);
            
            for (RLEcounter = 0; RLEcounter < RLElength; RLEcounter++) {
                frameBuffer[framePtr] = value;
                framePtr += stepFrameCol;
                col++;
                
                if (col == xRes) {
                    framePtr += stepFrameRow;
                    col = 0;
                }
                
                pixelCt++;
            }
            
            if (RLEpause > 0) {
                RLEpause--;
            }
        }
    }

    // 24-bit and 32-bit TrueColor images
    private final void decodeTC() throws IOException {
        if (depth == 24) {
            while (pixelCt < pixels) {
                if (RLEencoded) {
                    if (RLEpause == 0) {
                        decodeRLE();
                    }
                    
                    blue = dfin.readUnsignedByte();
                    green = dfin.readUnsignedByte();
                    red = dfin.readUnsignedByte();
                } else {
                    blue = cache[cachePtr++];
                    green = cache[cachePtr++];
                    red = cache[cachePtr++];
                }
                
                value = getRGBAValue(red, green, blue);
                
                for (RLEcounter = 0; RLEcounter < RLElength; RLEcounter++) {
                    frameBuffer[framePtr] = value;
                    framePtr += stepFrameCol;
                    col++;
                    
                    if (col == xRes) {
                        framePtr += stepFrameRow;
                        col = 0;
                    }
                    
                    pixelCt++;
                }
                
                if (RLEpause > 0) {
                    RLEpause--;
                }
            }
        } else if (depth == 32) {
            while (pixelCt < pixels) {
                if (RLEencoded) {
                    if (RLEpause == 0) {
                        decodeRLE();
                    }
                    
                    blue = dfin.readUnsignedByte();
                    green = dfin.readUnsignedByte();
                    red = dfin.readUnsignedByte();
                    alpha = dfin.readUnsignedByte();
                } else {
                    blue = cache[cachePtr++];
                    green = cache[cachePtr++];
                    red = cache[cachePtr++];
                    alpha = cache[cachePtr++];
                }
                
                value = getRGBAValue(red, green, blue, alpha);
                
                for (RLEcounter = 0; RLEcounter < RLElength; RLEcounter++) {
                    frameBuffer[framePtr] = value;
                    framePtr += stepFrameCol;
                    col++;
                    if (col == xRes) {
                        framePtr += stepFrameRow;
                        col = 0;
                    }
                    pixelCt++;
                }
                
                if (RLEpause > 0) {
                    RLEpause--;
                }
            }
        }
    }

    // decode a RLE compression byte
    private final void decodeRLE() throws IOException {
        int v = dfin.readUnsignedByte();
        RLEtype = (v & 0x80);
        RLElength = (v & 0x7F) + 1;
        
        if (RLEtype == 0) {
            RLEpause = RLElength;
            RLElength = 1;
        }
    }

    //create RGBA value 
    private int getRGBAValue(int red, int green, int blue, int alpha) {
        return (red << 24) + (green << 16) + (blue << 8) + alpha;
    }

    //create RGBA value with default alpha
    private int getRGBAValue(int red, int green, int blue) {
        return (red << 24) + (green << 16) + (blue << 8) + DEFAULTALPHA;
    }

    public static byte getByte(int i, int pos) {
        return (byte) ((i >> (24 - pos * 8)) & 0x000000FF);
    }    
   
    public static void loadTGA(TGATexture tex, InputStream in) throws IOException {
        TGATextureLoader tgaIO = new TGATextureLoader(in);        
        tgaIO.readHeader();
        tex.setWidth(tgaIO.xRes);
        tex.setHeight(tgaIO.yRes);
        tgaIO.loadPicture();
        
        
        byte[] data = new byte[tgaIO.frameBuffer.length * 4];
        int dataPtr = 0;
        for (int i = 0; i < tgaIO.frameBuffer.length; i++) {
            for (int j = 0; j < 4; j++) {
                data[dataPtr++] = getByte(tgaIO.frameBuffer[i], j);
            }
        }
        tex.setData(data);
    }  
    
}
