package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * @author jordens
 * @since 9/05/13
 */
public abstract class BaseModalWindow extends ModalWindow implements ContentPanelAware {
    private WindowSize size;

    public BaseModalWindow(String id) {
        super(id);
    }

    public BaseModalWindow(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        //For some strange reason (wicket1.5), the close button in the upper right corner doesn't really call the close method.
        //This has the effect that the nested forms still remain visible (hence validated) even when the window is closed.
        CloseButtonCallback callback = new CloseButtonCallback() {
            @Override
            public boolean onCloseButtonClicked(AjaxRequestTarget target) {
                return true;
            }
        };
        this.setCloseButtonCallback(callback);

        super.setContent(getInitialContentPanel());

        this.add(new NoWicketUnloadConfirmationBehavior());
    }

    protected Panel getInitialContentPanel() {
        return new EmptyPanel(this.getContentId());
    }

    @Override
    public ModalWindow setContent(Component component) {
        throw new WicketRuntimeException("BaseModalWindow does not support the setContent method. Override getContentPanel(). This method will be called on show().");
    }

    @Override
    public void show(AjaxRequestTarget target) {
        //switch de contentpanel with the provided one.
        this.update(target, new ContentPanelProvider() {
            @Override
            public Component create(String id) {
                return getContentPanel(id);
            }
        });

        super.show(target);
    }

    /**
     * Updates the contentpanel with the provided one.
     *
     * @param target   target used to update the contentpanel after the update
     * @param provider the provider that creates a new contentpanel
     * @return the replaced component
     */
    @Override
    public Component update(AjaxRequestTarget target, ContentPanelProvider provider) {
        Component newContentPanel = provider.create(getContentId());
        return this.getContent().replaceWith(newContentPanel);
    }

    protected abstract Panel getContentPanel(String id);

    /**
     * This will set the window size to the desired size, or "maximize" the window when no size is set.
     * <p/>
     *
     * @param settings the javascript settings
     * @return the settings
     */
    @Override
    protected AppendingStringBuffer postProcessSettings(AppendingStringBuffer settings) {
        if (this.getSize() == null) {
            settings.append("settings.width=Wicket.Window.getViewportWidth()-" + getLeftRightMarginWidth() + ";\n");
            settings.append("settings.height=Wicket.Window.getViewportHeight()-" + getTopBottomMarginWidth() + ";\n");
        } else {
            if (this.getSize().getWidth() != null) {
                settings.append("settings.width=" + this.getSize().getWidth() + ";\n");
            } else {
                settings.append("settings.width=Wicket.Window.getViewportWidth()-" + getLeftRightMarginWidth() + "\n");
            }
            if (this.getSize().getHeight() != null) {
                settings.append("settings.height=" + this.getSize().getHeight() + ";\n");
            } else {
                settings.append("settings.height=Wicket.Window.getViewportHeight()-" + getTopBottomMarginWidth() + ";\n");
            }
        }

        return settings;
    }

    /**
     * getTopBottomMarginWidth() is the space between the top and bottom border of the screen.
     */
    protected int getTopBottomMarginWidth() {
        return 200;
    }

    /**
     * Returns the space between the left and right border of the screen.
     *
     * @return see description.
     */
    protected int getLeftRightMarginWidth() {
        return 50;
    }

    private WindowSize getSize() {
        if (this.size == null) {
            this.setSize(getDefaultWindowSize());
        }
        return this.size;
    }

    /**
     * The default window size, if your app requires one.
     */
    protected WindowSize getDefaultWindowSize() {
        return null;
    }

    public void setSize(WindowSize size) {
        this.size = size;
    }

    public void setSize(Integer width, Integer height) {
        this.size = new WindowSize(width, height);

        if (height != null) {
            this.setInitialHeight(height);
        }
        if (width != null) {
            this.setInitialWidth(width);
        }
    }
}
