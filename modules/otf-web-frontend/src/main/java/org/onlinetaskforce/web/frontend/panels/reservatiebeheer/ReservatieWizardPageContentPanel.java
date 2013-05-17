package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.Component;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.extensions.wizard.IWizardModel;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.dynamic.DynamicWizardModel;
import org.apache.wicket.extensions.wizard.dynamic.DynamicWizardStep;
import org.apache.wicket.extensions.wizard.dynamic.IDynamicWizardStep;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.StringValidator;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.business.services.ReservatieBeheerService;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.enumerations.TijdEnum;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.utils.DateTimeUtil;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.onlinetaskforce.web.frontend.components.datepicker.DatePickerField;
import org.onlinetaskforce.web.frontend.dropdown.EnumDropDownChoice;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.ReservatieWizardPage;
import org.onlinetaskforce.web.frontend.validators.ReservatiePeriodeValidator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Reusable basic DetailReservatiePageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class ReservatieWizardPageContentPanel extends Wizard {
    private Reservatie reservatie;

    private OtfWizardButtonBar buttonBar;

    private IDynamicWizardStep step0KiesPeriode;
    private IDynamicWizardStep step1ZoekOptie;
    private IDynamicWizardStep step2Doel;
    private IDynamicWizardStep step3KiesWagen;
    private IDynamicWizardStep step4Confirmation;

    private RadioGroup radioGroup;
    private List<Wagen> wagenkeuzes;

    @SpringBean
    ReservatieBeheerService reservatieBeheerService;
    @SpringBean
    GebruikerService gebruikerService;

    public ReservatieWizardPageContentPanel(String s, IModel<Reservatie> model) {
        super(s);
        reservatie = model.getObject();
        reservatie.setCreatieGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
        reservatie.setCreatieTijdstip(new Date());
        this.step0KiesPeriode = new Step0(null);
        this.step1ZoekOptie = new Step1(this.step0KiesPeriode);
        this.step2Doel = new Step2(step1ZoekOptie);
        this.step3KiesWagen = new Step3(this.step2Doel);
        this.step4Confirmation = new Step4(step3KiesWagen);
        final IWizardModel wizardModel = new DynamicWizardModel(this.step0KiesPeriode);
	    this.init(wizardModel);
        setDefaultModel(new CompoundPropertyModel<ReservatieWizardPageContentPanel>(this));
    }

    public final class Step0 extends DynamicWizardStep {

        public Step0(IDynamicWizardStep previousStep) {
            super(previousStep);
            setTitleModel(new ResourceModel("step0.title"));
            setSummaryModel(new ResourceModel("step0.summary"));

            DatePickerField begindatum = new DatePickerField("reservatie.BeginDatum");
            begindatum.setRequired(true);
            add(begindatum);
            DatePickerField einddatum = new DatePickerField("reservatie.EindDatum");
            einddatum.setRequired(true);
            add(einddatum);
            DropDownChoice<TijdEnum> starttijd = new EnumDropDownChoice<TijdEnum>("reservatie.starttijd", TijdEnum.class);
            starttijd.setRequired(true);
            add(starttijd);
            DropDownChoice<TijdEnum> eindtijd = new EnumDropDownChoice<TijdEnum>("reservatie.eindtijd", TijdEnum.class);
            eindtijd.setRequired(true);
            add(eindtijd);
            FormComponent<?>[] components = new FormComponent<?>[4];
            components[0] = begindatum;
            components[1] = einddatum;
            components[2] = starttijd;
            components[3] = eindtijd;
            add(new ReservatiePeriodeValidator(components));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            Component previous = buttonBar.get("previous");
            previous.setVisible(false);
            Component finish = buttonBar.get("finish");
            finish.setVisible(false);
        }

        @Override
        public boolean isLastStep() {
            return false;
        }

        @Override
        public IDynamicWizardStep next() {
            GregorianCalendar beginCal = new GregorianCalendar();
            beginCal.setTime(reservatie.getBeginDatum());
            beginCal.set(Calendar.HOUR, DateTimeUtil.getUur(reservatie.getStarttijd()));
            beginCal.set(Calendar.MINUTE, DateTimeUtil.getMinuten(reservatie.getStarttijd()));
            reservatie.setBeginDatum(beginCal.getTime());
            GregorianCalendar eindCal = new GregorianCalendar();
            eindCal.setTime(reservatie.getEindDatum());
            eindCal.set(Calendar.HOUR, DateTimeUtil.getUur(reservatie.getEindtijd()));
            eindCal.set(Calendar.MINUTE, DateTimeUtil.getMinuten(reservatie.getEindtijd()));
            reservatie.setEindDatum(eindCal.getTime());

            return step1ZoekOptie;
        }
    }

    public final class Step1 extends DynamicWizardStep {

        public Step1(IDynamicWizardStep previousStep) {
            super(previousStep);
            setTitleModel(new ResourceModel("step1.title"));
            setSummaryModel(new ResourceModel("step1.summary"));
            reservatie.setZoekMethodeAutomatisch(Boolean.TRUE);
            final RadioGroup<Boolean> zoekMethode = new RadioGroup<Boolean>("reservatie.zoekMethodeAutomatisch");
            zoekMethode.add(new Radio<Boolean>("automatisch", new Model<Boolean>(true)));
            zoekMethode.add(new Radio<Boolean>("manueel", new Model<Boolean>(false)));
            add(zoekMethode);
            add(new AbstractFormValidator() {
                @Override
                public FormComponent<?>[] getDependentFormComponents() {
                    return null;
                }

                @Override
                public void validate(Form<?> form) {
                    if (zoekMethode.getConvertedInput() == Boolean.FALSE) {
                        form.error(new StringResourceModel("manueel.not.support", form.getPage(), null).getString());
                    }
                }
            });
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            Component previous = buttonBar.get("previous");
            previous.setVisible(true);
            Component finish = buttonBar.get("finish");
            finish.setVisible(false);
        }

        @Override
        public boolean isLastStep() {
            return false;
        }

        @Override
        public IDynamicWizardStep next() {
            return step2Doel;
        }
    }

    public final class Step2 extends DynamicWizardStep {

        public Step2(IDynamicWizardStep previousStep) {
            super(previousStep);
            setTitleModel(new ResourceModel("step2.title"));
            setSummaryModel(new ResourceModel("step2.summary"));
            TextArea<String> doel = new TextArea<String>("reservatie.doel");
            doel.add(StringValidator.maximumLength(500));
            doel.setRequired(true);
            add(doel);
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            Component previous = buttonBar.get("previous");
            previous.setVisible(true);
            Component finish = buttonBar.get("finish");
            finish.setVisible(false);
        }

        @Override
        public boolean isLastStep() {
            return false;
        }

        @Override
        public IDynamicWizardStep next() {
            return step3KiesWagen;
        }
    }

    public final class Step3 extends DynamicWizardStep {

        public Step3(IDynamicWizardStep previousStep) {
            super(previousStep);
            setTitleModel(new ResourceModel("step3.title"));
            setSummaryModel(new ResourceModel("step3.summary"));
            radioGroup = new RadioGroup("radioGroup", Model.of(new String()));
            radioGroup.add(new ZoekAutomatischListPanel("reservatie-list", radioGroup, new LazyListDataProvider<Wagen>() {
                @Override
                protected List<Wagen> getData() {
                    wagenkeuzes = reservatieBeheerService.zoekAutomatisch(reservatie);
                    if (wagenkeuzes == null || wagenkeuzes.size() == 0) {
                        warn(new StringResourceModel("ander.criteria", getPage(), null).getString());
                    }
                    return wagenkeuzes;
                }
            }));
            radioGroup.setRequired(true);
            add(radioGroup);
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            Component previous = buttonBar.get("previous");
            previous.setVisible(true);
            Component finish = buttonBar.get("finish");
            finish.setVisible(false);
        }

        @Override
        public boolean isLastStep() {
            return false;
        }

        @Override
        public IDynamicWizardStep next() {
            reservatie.setWagen(findWagen((String)radioGroup.getConvertedInput()));

            return step4Confirmation;
        }

        private Wagen findWagen(String convertedInput) {
            Wagen wagen = null;
            if (wagenkeuzes != null && wagenkeuzes.size() > 0) {
                Iterator<Wagen> wagenIterator = wagenkeuzes.iterator();
                while (wagenIterator.hasNext()) {
                    Wagen next = wagenIterator.next();
                    if (convertedInput.equals(next.getId())) {
                        wagen = next;
                        break;
                    }
                }
            }
            return wagen;
        }
    }

    public final class Step4 extends DynamicWizardStep {

        public Step4(IDynamicWizardStep previousStep) {
            super(previousStep);
            setTitleModel(new ResourceModel("step4.title"));
            setSummaryModel(new ResourceModel("step4.summary"));
            setDefaultModel(new Model<Reservatie>(reservatie));
            add(new Label("reservatie.BeginDatum") {
                @Override
                public <C> IConverter<C> getConverter(Class<C> type) {
                    return (IConverter<C>)new PatternDateConverter("dd/MM/yyyy HH:mm", false);
                }
            });
            add(new Label("reservatie.EindDatum") {
                @Override
                public <C> IConverter<C> getConverter(Class<C> type) {
                    return (IConverter<C>)new PatternDateConverter("dd/MM/yyyy HH:mm", false);
                }
            });
            add(new Label("reservatie.wagen.merk"));
            add(new Label("reservatie.wagen.merktype"));
            add(new Label("reservatie.wagen.brandstof"));
            add(new Label("reservatie.wagen.nummerplaat"));
        }

        @Override
        protected void onConfigure() {
            super.onConfigure();
            Component previous = buttonBar.get("previous");
            previous.setVisible(true);
            Component finish = buttonBar.get("finish");
            finish.setVisible(true);
        }

        @Override
        public boolean isLastStep() {
            return true;
        }

        @Override
        public IDynamicWizardStep next() {
            return null;
        }
    }

    @Override
    public void onCancel() {
        super.onCancel();
        setResponsePage(new ReservatieWizardPage());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        try {
            reservatie = reservatieBeheerService.createReservatie(reservatie);
            setResponsePage(new ReservatieWizardPage(new StringResourceModel("create.reservatie.success", getPage(), null).getString()));
        } catch (BusinessException e) {
            setResponsePage(new ReservatieWizardPage(new StringResourceModel("create.reservatie.bevestiging.email.failure", getPage(), null).getString()));
        }
    }

    public Reservatie getReservatie() {
        return reservatie;
    }

    public void setReservatie(Reservatie reservatie) {
        this.reservatie = reservatie;
    }

    @Override
    protected Component newButtonBar(String id) {
        buttonBar = new OtfWizardButtonBar(id, this);
        return buttonBar;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }
}

