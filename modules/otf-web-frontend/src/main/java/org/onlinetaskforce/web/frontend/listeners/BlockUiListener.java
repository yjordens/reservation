package org.onlinetaskforce.web.frontend.listeners;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.attributes.IAjaxCallListener;

/**
 * @author jordens
 * @since 12/03/13
 */
public class BlockUiListener implements IAjaxCallListener{
    @Override
    public CharSequence getBeforeHandler(Component component) {
        return "$.blockUI({ message: '<span id=\"busy-indicator\" /><h2> Even geduld...</h2>', css: { height: '850px;' } })";
    }

    @Override
    public CharSequence getPrecondition(Component component) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CharSequence getBeforeSendHandler(Component component) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    public CharSequence getAfterHandler(Component component) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CharSequence getSuccessHandler(Component component) {
        return "$.unblockUI();";
    }

    @Override
    public CharSequence getFailureHandler(Component component) {
        return "$.unblockUI();";
    }

    @Override
    public CharSequence getCompleteHandler(Component component) {
         return "$.unblockUI();";
    }
}
