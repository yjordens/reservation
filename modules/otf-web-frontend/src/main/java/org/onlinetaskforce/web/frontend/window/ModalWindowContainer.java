package org.onlinetaskforce.web.frontend.window;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * This  is -just like the name says- a container for a modal window, and a component to trigger that modal window.
 * @author jordens
 * @since 8/03/13
 * @since 10:15 AM
 */
public abstract class ModalWindowContainer<T extends Component, W extends ModalWindow> extends Panel implements ModalWindowAware {
    private static final long serialVersionUID = 8355281879461328993L;

    public static final String TRIGGER_ID = "modalWindowTrigger";
    public static final String MODAL_WINDOW_ID = "modalWindow";

    private W window;
    private T trigger;

    protected ModalWindowContainer(String id, T trigger) {
        super(id);
        this.setTrigger(trigger);
    }

    protected ModalWindowContainer(String id, W window) {
        super(id);
        this.setWindow(window);
    }

    protected ModalWindowContainer(String id, T trigger, W window) {
        this(id, trigger);
        this.setWindow(window);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // IN THEORY, this form should not needed. In reality it is... If this is removed, you will no longer
        // be able to submit forms in modal windows, in chrome....
        Form form = new Form("modalWindowForm");
//        form.setMultiPart(true);
        form.add(getWindow());

        //we use a repeatingview, so the trigger component id doesn't really matter.
        RepeatingView triggerView = new RepeatingView(ModalWindowContainer.TRIGGER_ID);
        if (this.getTrigger() == null) {
            throw new IllegalArgumentException("There 's no trigger defined for this modalwindowcontainer. " +
                    "You should provide one using the constructor, or override the getTrigger() method.");
        }
        triggerView.add(this.getTrigger());
        form.add(triggerView);
        this.add(form);
    }

    /**
     * Utiltity method that returns the form wrapping the trigger and the window.
     *
     * @return the form.
     */
    public final Form getForm() {
        return (Form) this.get("modalWindowForm");
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public final W getWindow() {
        if (this.window == null) {
            this.setWindow(this.createWindow(MODAL_WINDOW_ID));
        }
        return this.window;
    }

    /**
     * Gives you the possibility to override this method, instead of handing it to the constructor. If you override this
     * method, you don't need to worry about the window id, just use the one that's given.
     *
     * @param id the id the window should have
     * @return the modal window
     */
    public W createWindow(String id) {
        return null;
    }

    private void setWindow(W window) {
        if (window == null) {
            throw new IllegalArgumentException("The window you're trying to set is null. Please provide one to the constructor, or override the createWindow(String id) method.");
        }
        if (!StringUtils.equalsIgnoreCase(window.getId(), MODAL_WINDOW_ID)) {
            throw new IllegalArgumentException(String.format("Your modal window ID should be %s (instead of * %s *).", MODAL_WINDOW_ID, window.getId()));
        }
        this.window = window;
    }

    protected T getTrigger() {
        return this.trigger;
    }

    private void setTrigger(T trigger) {
        this.trigger = trigger;
    }
}