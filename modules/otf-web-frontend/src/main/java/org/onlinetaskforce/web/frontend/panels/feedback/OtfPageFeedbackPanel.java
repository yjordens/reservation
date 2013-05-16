package org.onlinetaskforce.web.frontend.panels.feedback;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * @author jordens
 * @since 19/03/13
 */
public class OtfPageFeedbackPanel extends FeedbackPanel{
    private Image feedbackImage;
    private PackageResourceReference nokResource;
    private PackageResourceReference warningkResource;
    private PackageResourceReference okResource;
    private Label title;
    private ResourceModel titleModelSuccess;
    private ResourceModel titleModelFailure;
    private ResourceModel titleModelWarning;

    public OtfPageFeedbackPanel(String id) {
        super(id);

        nokResource = new PackageResourceReference(OtfPageFeedbackPanel.class, "../../images/feedback/nok.png");
        warningkResource = new PackageResourceReference(OtfPageFeedbackPanel.class, "../../images/feedback/warning.png");
        okResource = new PackageResourceReference(OtfPageFeedbackPanel.class, "../../images/feedback/ok.png");

        feedbackImage = new Image("feedback-img", okResource);
        feedbackImage.setOutputMarkupId(true);
        add(feedbackImage);

        titleModelSuccess = new ResourceModel("title.success");
        titleModelFailure = new ResourceModel("title.failure");
        titleModelWarning = new ResourceModel("title.warning");
        title = new Label("title", titleModelSuccess);
        add(title);

        setOutputMarkupId(true);
    }

    @Override
    public boolean isVisible() {
        boolean  result;
        result = anyErrorMessage() || anyMessage();

        if (anyErrorMessage()) {
            feedbackImage.setImageResourceReference(nokResource);
            title.setDefaultModel(titleModelFailure);
        } else if (anyMessage(FeedbackMessage.WARNING)) {
            feedbackImage.setImageResourceReference(warningkResource);
            title.setDefaultModel(titleModelWarning);
        } else if (anyMessage()) {
            feedbackImage.setImageResourceReference(okResource);
            title.setDefaultModel(titleModelSuccess);
        }

        return result;
    }

    public Image getFeedbackImage() {
        return feedbackImage;
    }

    public void setFeedbackImage(Image feedbackImage) {
        this.feedbackImage = feedbackImage;
    }

    public Label getTitle() {
        return title;
    }

    public void setTitle(Label title) {
        this.title = title;
    }
}
