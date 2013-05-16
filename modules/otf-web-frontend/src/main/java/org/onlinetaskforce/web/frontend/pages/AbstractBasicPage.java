package org.onlinetaskforce.web.frontend.pages;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.resource.JQueryPluginResourceReference;
import org.onlinetaskforce.web.frontend.application.OtfApplication;
import org.onlinetaskforce.web.frontend.components.menu.Menu;
import org.onlinetaskforce.web.frontend.panels.feedback.OtfPageFeedbackPanel;

/**
 * @author jordens
 * @since 17/03/13
 */
public class AbstractBasicPage extends WebPage{
    private static final CssResourceReference OTF_PAGE_CSS = new CssResourceReference(HomeLoginPage.class, "../css/style.css");

    private static final JavaScriptResourceReference OTF_PAGE_JS_JQUERY_BLOCKUI = new JQueryPluginResourceReference(HomeLoginPage.class, "../js/jquery.blockUI.js");
    private static final JavaScriptResourceReference OTF_PAGE_JS_MODERNIZR = new JavaScriptResourceReference(HomeLoginPage.class, "../js/modernizr-1.5.min.js");
    private static final JavaScriptResourceReference OTF_PAGE_JS_SOOPERFISH = new JavaScriptResourceReference(HomeLoginPage.class, "../js/jquery.sooperfish.js");
    private static final JavaScriptResourceReference OTF_PAGE_JS_EASING_SOOPER = new JavaScriptResourceReference(HomeLoginPage.class, "../js/jquery.easing-sooper.js");

    protected Menu menu;
    OtfPageFeedbackPanel feedbackPanel;


    public AbstractBasicPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(createPageTitleLabel());
        menu = new Menu("menu_container");
        add(menu);

        feedbackPanel = new OtfPageFeedbackPanel("feedback");
        add(feedbackPanel);
    }

    private Label createPageTitleLabel() {
        return new Label("pageTitle", new Model<String>() { // NOSONAR
            @Override
            public String getObject() {
                return getPageTitle();
            }
        });
    }

    protected String getPageTitle() {
        return new StringResourceModel("title.pagetitle", this, null, new Object[]{((OtfApplication) getApplication()).getApplicationVersion()}).getString();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
      response.render(CssHeaderItem.forReference(OTF_PAGE_CSS));
      response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_JQUERY_BLOCKUI));
      response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_MODERNIZR));
      response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_EASING_SOOPER));
      response.render(JavaScriptReferenceHeaderItem.forReference(OTF_PAGE_JS_SOOPERFISH));
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public OtfPageFeedbackPanel getFeedbackPanel() {
        return feedbackPanel;
    }

    public void setFeedbackPanel(OtfPageFeedbackPanel feedbackPanel) {
        this.feedbackPanel = feedbackPanel;
    }
}
