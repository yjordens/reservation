package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.WagenBeheerService;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.MultipleBusinessException;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.panels.AnnulatiesConfirmationPanel;

/**
 * @author jordens
 * @since 11/05/13
 */
public class AnnulatiesConfirmationWindow extends ModalWindow {
    @SpringBean
    private WagenBeheerService wagenBeheerService;

    private AnnulatiesConfirmationPanel content;
    private MultipleBusinessException mbe;
    private Wagen wagen;

    public AnnulatiesConfirmationWindow(String id) {
        super(id);
        content = new AnnulatiesConfirmationPanel(CONTENT_ID);
        setInitialHeight(150);
        final AjaxLink okLnk = new AjaxLink("ok-link") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                try {
                    target.appendJavaScript("Wicket.Window.unloadConfirmation=false;");
                    mbe.acceptAllbusinessExceptions();
                    wagenBeheerService.addOrModify(wagen, mbe);
                    ((ModalWindow)((AnnulatiesConfirmationPanel)getParent()).getParent()).close(target);
                    AbstractBasicPage page = (AbstractBasicPage)getPage();
                    page.success(new StringResourceModel("create.wagen.success", this, null).getString());
                    target.add(page);
                } catch (BusinessException be) {
                    ((ModalWindow)((AnnulatiesConfirmationPanel)getParent()).getParent()).close(target);
                    AbstractBasicPage page = (AbstractBasicPage)getPage();
                    page.error(new StringResourceModel("automatisch.annulatie.failure", this, null).getString());
                    target.add(page);
                    target.appendJavaScript("Wicket.Window.unloadConfirmation=false;");
                }
            }
        };
        content.add(okLnk);
        final AjaxLink nokLnk = new AjaxLink("nok-link") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                target.appendJavaScript("Wicket.Window.unloadConfirmation=false;");
                ((ModalWindow)((AnnulatiesConfirmationPanel)getParent()).getParent()).close(target);
            }
        };
        content.add(nokLnk);
        setContent(content);
    }

    @Override
    public ModalWindow showUnloadConfirmation(boolean unloadConfirmation) {
        return super.showUnloadConfirmation(false);
    }

    @Override
    public void close(AjaxRequestTarget target) {
        super.close(target);
        target.appendJavaScript("Wicket.Window.unloadConfirmation=false;");
    }

    public MultipleBusinessException getMbe() {
        return mbe;
    }

    public void setMbe(MultipleBusinessException mbe) {
        this.mbe = mbe;
    }

    public Wagen getWagen() {
        return wagen;
    }

    public void setWagen(Wagen wagen) {
        this.wagen = wagen;
    }
}
