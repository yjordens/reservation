package org.onlinetaskforce.web.frontend.window;

import java.io.Serializable;

/**
 * Simple bean containing width and height.
 * <p/>
 *
 * @author muys
 * @since 7/4/11
 */
public class WindowSize implements Serializable {
    private static final long serialVersionUID = 8494418197260239749L;

    private Integer width;
    private Integer height;

    public WindowSize(Integer width, Integer height) {
        this.setWidth(width);
        this.setHeight(height);
    }

    public Integer getHeight() {
        return this.height;
    }

    private void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    private void setWidth(Integer width) {
        this.width = width;
    }
}
