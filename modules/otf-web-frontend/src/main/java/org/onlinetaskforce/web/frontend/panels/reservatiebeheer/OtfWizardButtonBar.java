package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.extensions.wizard.IWizard;
import org.apache.wicket.extensions.wizard.WizardButtonBar;

/**
 * @author jordens
 * @since 3/05/13
 */
public class OtfWizardButtonBar extends WizardButtonBar {
    /**
     * Construct.
     *
     * @param id     The component id
     * @param wizard The containing wizard
     */
    public OtfWizardButtonBar(String id, IWizard wizard) {
        super(id, wizard);
    }
}
